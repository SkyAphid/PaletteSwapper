package nokori.paletteswapper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Locale;

import javax.imageio.ImageIO;

public class PaletteSwapper {
	
	private static final String BRASCH_SYNTAX = "#BraschSyntax";
	private static final String WORMS_SYNTAX = "#WormsSyntax";
	
	public static void buildPalette(File paletteFile, File[] imageFiles) throws Exception {
		System.out.println("Looking for definitions in " + paletteFile.getName());

		String s = new String(Files.readAllBytes(paletteFile.toPath()));
		String[] lines = s.split(System.getProperty("line.separator"));
		
		String paletteName = paletteFile.getName().substring(0, paletteFile.getName().indexOf("."));
		
		switch(lines[0]) {
		case WORMS_SYNTAX:
			runWormsPaletteSwap(paletteName, lines, imageFiles);
			break;
		case BRASCH_SYNTAX:
		default:
			runBraschPaletteSwap(paletteName, lines, imageFiles);
			break;
		}
	}
	
	/*
	 * Brasch Syntax
	 */
	
	private static final String POINTER_KEYWORD = "->";
	private static final int EXPECTED_BRASCH_LINE_LENGTH = 16;
	
	private static void runBraschPaletteSwap(String paletteName, String[] lines, File[] imageFiles) throws IOException {
		System.out.println("Using Brasch syntax to build palette");
		
		Palette palette = new Palette();

		//Build palette
		for (int j = 0; j < lines.length; j++) {
			if (lines[j].length() == EXPECTED_BRASCH_LINE_LENGTH) {
				int pointerIndex = lines[j].indexOf(POINTER_KEYWORD);

				if (pointerIndex != -1) {
					String findColor = lines[j].substring(0, pointerIndex).toUpperCase(Locale.ENGLISH);
					String replaceColor = lines[j].substring(pointerIndex + POINTER_KEYWORD.length(), lines[j].length()).toUpperCase(Locale.ENGLISH);

					palette.put(findColor, replaceColor);

					System.out.println("Found definition: " + findColor + " will be replaced with " + replaceColor);
				}
			}
		}
		
		//Replace colors
		BufferedImage[] images = replaceColors(palette, imageFiles);
		
		//Save files
		saveFiles(paletteName, imageFiles, images);
	}
	
	/*
	 * Worms Mode
	 */
	
	private static final String CHUNK_KEYWORD = "-";
	private static final int EXPECTED_WORMS_LINE_LENGTH = 7;
	
	private static void runWormsPaletteSwap(String paletteName, String[] lines, File[] imageFiles) throws Exception {
		System.out.println("Using Brasch syntax to build palette");
		
		ArrayList<Palette> palettes = new ArrayList<Palette>();
		ArrayList<String> baseColors = new ArrayList<String>();

		//Build palette
		for (int j = 0; j < lines.length; j++) {
			
			//Chunk divider
			if (lines[j].equals(CHUNK_KEYWORD)) {
				palettes.add(new Palette());
				System.out.println("Chunk divider found: defining new palette (Chunk " + palettes.size() + ")");
			}
			
			//Color
			if (lines[j].length() == EXPECTED_WORMS_LINE_LENGTH) {
				if (!palettes.isEmpty()) {
					Palette palette = palettes.get(palettes.size()-1);
					
					String findColor = baseColors.get(palette.size());
					
					palette.put(findColor, lines[j]);

					System.out.println("Found definition: " + findColor + " will be replaced with " + lines[j]);
				} else {
					//Build base color list if a palette hasn't been defined yet
					baseColors.add(lines[j]);
					
					System.out.println("Added base color: " + lines[j]);
				}
			}
		}
		
		//Replace colors
		for (int i = 0; i < palettes.size(); i++) {
			BufferedImage[] images = replaceColors(palettes.get(i), imageFiles);
			
			//Save files
			saveFiles(paletteName + "_Chunk" + (i+1), imageFiles, images);
		}
	}

	/*
	 * 
	 * General Tools
	 * 
	 */
	

	
	private static final BufferedImage[] replaceColors(Palette palette, File[] imageFiles) throws IOException {
		BufferedImage[] images = new BufferedImage[imageFiles.length];
		
		for (int i = 0; i < imageFiles.length; i++) {
			images[i] = ImageIO.read(imageFiles[i]);
		}
		
		for (int i = 0; i < images.length; i++) {
			BufferedImage image = images[i];

			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {

					Color color = new Color(image.getRGB(x, y));
					String hexCode = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());  
					
					if (palette.containsKey(hexCode)) {
						Color replace = Color.decode(palette.get(hexCode));
						image.setRGB(x, y, replace.getRGB());
					}
				}
			}
		}
		
		return images;
	}
	
	private static final void saveFiles(String paletteName, File[] imageFiles, BufferedImage[] images) throws IOException {
		for (int i = 0; i < images.length; i++) {
			String imageName = imageFiles[i].getName();
			
			//This ensures a fully correct parent path - getParent() doesn't seem to work for every directory (such as the desktop)
			String absolutePath = imageFiles[i].getAbsolutePath();
			String directory = absolutePath.substring(0, absolutePath.indexOf(imageName));
			
			//Create a name for the copied palette
			String paletteSwappedName = imageName.substring(0, imageName.lastIndexOf(".")) + paletteName + ".png";
			
			//Export
			File f = new File(directory + paletteSwappedName);
			ImageIO.write(images[i], "png", new FileOutputStream(f));
			
			System.out.println("Saved " + f.getPath());
		}
	}
}
