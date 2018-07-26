package nokori.paletteswapper;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is the program for PaletteSwapper. The actual tools for Palette Swapping is in PaletteSwapper.java
 * 
 * @author Brayden
 *
 */
public class PaletteSwapperCore {
	public static void main(String args[]) {
		
		try {
			File[] paletteFiles = getPaletteFiles();
			File[] imageFiles = getImageFiles();
			
			for (int i = 0; i < paletteFiles.length; i++) {
				PaletteSwapper.buildPalette(paletteFiles[i], imageFiles);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Caught " + e.getClass().getName() + "\n\nMessage:\n" + e.getMessage() + "\n\nAborting palette swap.", "Caught " + e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		JOptionPane.showMessageDialog(null, "Palette swap complete. Exiting program.", "Success", JOptionPane.INFORMATION_MESSAGE);
		
		System.exit(0);
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
