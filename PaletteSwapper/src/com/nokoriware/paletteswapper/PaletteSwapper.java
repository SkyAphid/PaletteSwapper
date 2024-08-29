package com.nokoriware.paletteswapper;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PaletteSwapper {
	

	public static void buildPalette(File paletteFile, File[] imageFiles) throws Exception {
		System.out.println("Looking for definitions in " + paletteFile.getName());

		String[] lines = Files.readAllLines(paletteFile.toPath());
		
		String paletteName = paletteFile.getName().substring(0, paletteFile.getName().indexOf("."));
		
		runWormsPaletteSwap(paletteName, lines, imageFiles);
	}
	
	/*
	 * 
	 * 
	 * Palette Generation
	 * 
	 * 
	 */
	
	/*
	 * Worms Mode
	 */
	
	private static final String CHUNK_KEYWORD = "-";
	private static final int EXPECTED_LINE_LENGTH = 7;
	
	private static void runWormsPaletteSwap(String paletteName, List<String> lines, File[] imageFiles) throws Exception {
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
			if (lines[j].length() == EXPECTED_LINE_LENGTH) {
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
	 * 
	 * Color Tools
	 * 
	 * 
	 */
	
	/**
	 * With the given palette, scan and replace colors in all of the image files.
	 * 
	 * Indexed and Unindexed images are treated differently to accomodate both.
	 */
	private static BufferedImage[] replaceColors(Palette palette, File[] imageFiles) throws IOException {
		BufferedImage[] images = new BufferedImage[imageFiles.length];
		
		for (int i = 0; i < imageFiles.length; i++) {
			images[i] = ImageIO.read(imageFiles[i]);
		}
		
		for (int i = 0; i < images.length; i++) {
			
			BufferedImage image = images[i];
			
			if (image.getColorModel() instanceof IndexColorModel) {
				image = convertToARGB(image);
			}
				
			images[i] = replaceColorsUnindexed(palette, image);
		}
		
		return images;
	}
	
	private static BufferedImage replaceColorsUnindexed(Palette palette, BufferedImage image) throws IOException {
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

		return image;
	}
	
	public static BufferedImage convertToARGB(BufferedImage i) {
	    BufferedImage result = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    
	    Graphics2D g2d = result.createGraphics();
	    g2d.setComposite(AlphaComposite.Clear);
	    g2d.fillRect(0, 0, result.getWidth(), result.getHeight());
	    g2d.setComposite(AlphaComposite.Src);
	    g2d.drawImage(i, 0, 0, null);
	    
	    return result;
	}

	/*
	 * 
	 * 
	 * I/O
	 * 
	 * 
	 */
	
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
