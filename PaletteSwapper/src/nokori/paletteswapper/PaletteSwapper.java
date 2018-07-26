package nokori.paletteswapper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PaletteSwapper {
	public static void main(String args[]) {
		
		try {
			File[] paletteFiles = getPaletteFiles();
			File[] imageFiles = getImageFiles();
			
			for (int i = 0; i < paletteFiles.length; i++) {
				String paletteName = paletteFiles[i].getName().substring(0, paletteFiles[i].getName().indexOf("."));
				HashMap<String, String> palette = new HashMap<String, String>();
				
				buildPalette(paletteFiles[i], palette);
				replaceColors(paletteName, palette, imageFiles);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Caught " + e.getClass().getName() + "\nMessage:\n" + e.getMessage(), "Caught " + e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
		}
		
		System.exit(0);
	}
	
	private static final void replaceColors(String paletteName, HashMap<String, String> palette, File[] imageFiles) throws IOException {
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
			
			String imageName = imageFiles[i].getName();
			
			String absolutePath = imageFiles[i].getAbsolutePath();
			String directory = absolutePath.substring(0, absolutePath.indexOf(imageName));
			
			String paletteSwappedName = imageName.substring(0, imageName.lastIndexOf(".")) + paletteName + ".png";
			
			File f = new File(directory + paletteSwappedName);
			ImageIO.write(image, "png", new FileOutputStream(f));
			
			System.out.println("Saved " + f.getPath());
		}
	}
	
	private static final String POINTER_KEYWORD = "->";
	private static final int EXPECTED_LINE_LENGTH = 16;
	
	private static void buildPalette(File paletteFile, HashMap<String, String> palette) throws IOException {
		System.out.println("Looking for definitions in " + paletteFile.getName());

		String s = new String(Files.readAllBytes(paletteFile.toPath()));
		String[] lines = s.split(System.getProperty("line.separator"));

		for (int j = 0; j < lines.length; j++) {
			if (lines[j].length() == EXPECTED_LINE_LENGTH) {
				int pointerIndex = lines[j].indexOf(POINTER_KEYWORD);

				if (pointerIndex != -1) {
					String findColor = lines[j].substring(0, pointerIndex).toUpperCase(Locale.ENGLISH);
					String replaceColor = lines[j].substring(pointerIndex + POINTER_KEYWORD.length(), lines[j].length())
							.toUpperCase(Locale.ENGLISH);

					palette.put(findColor, replaceColor);

					System.out.println("Found definition: " + findColor + " will be replaced with " + replaceColor);
				}
			}
		}
	}
	
	private static File[] getPaletteFiles() throws IOException {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Images", "txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Select Palette Swap Color Definitions");
		chooser.setMultiSelectionEnabled(true);
		chooser.setRequestFocusEnabled(true);

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFiles();
		}
		
		return null;
	}
	
	private static File[] getImageFiles() throws IOException {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Select Images to Palette Swap");
		chooser.setMultiSelectionEnabled(true);
		chooser.setRequestFocusEnabled(true);
		
		int returnVal = chooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFiles();
		}
		
		return null;
	}
}
