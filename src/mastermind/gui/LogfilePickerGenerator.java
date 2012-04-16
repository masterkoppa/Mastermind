package mastermind.gui;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import mastermind.core.commands.*;

import org.apache.commons.io.FileExistsException;

public class LogfilePickerGenerator {
	
	/**
	 * Generates the file picker with the designated filters that allow for the user
	 * to pick a file suitable for logging.
	 * @param mainWindow - The panel where this is originating from
	 *                     to use as a parent of the popup window.
	 * @return
	 */
	public static boolean generateAndShow(JPanel mainWindow){
		// The file name where the user want's the file stored
		String fileName = "";

		// Open a File Chooser

		int result = JFileChooser.ERROR_OPTION;
		
		JFileChooser file = new JFileChooser();

		file.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".txt") || f.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public String getDescription() {
				return "Text File (.txt)";
			}

		});

		result = file.showSaveDialog(mainWindow);

		// If the user cancels the file picker
		if (result == JFileChooser.CANCEL_OPTION) {
			return false;
		} else {
			File f = file.getSelectedFile();

			fileName = f.getPath();
			
			//return true;
		}

		// Start configuring the logging
		IFileCommand logger = new ConfigureLogForFileCommand(fileName, false);
		
		
		try {
			// Try to save the log in place
			logger.execute();
		} catch (FileExistsException e1) {
			// The file already exists aske the user what it wants
			// to do
			int response = JOptionPane
					.showConfirmDialog(mainWindow,
							"File Already Exists, Please Pick Another file");

			// Yes Overriwrite?
			if (response == JOptionPane.YES_OPTION) {
				logger = new ConfigureLogForFileCommand(fileName,
						true);
				try {
					logger.execute();
				} catch (FileExistsException e2) {
					System.err
							.println("I say I want to override, why wont you let me IO");
					
					e2.printStackTrace();
					
					return false;
				} catch (IOException e2) {
					JOptionPane
							.showMessageDialog(mainWindow,
									"Unknown IO Exception, please try again");
					e2.printStackTrace();
					return false;
				}
			} else {
				// Cancel or No
				return false;
			}
		} catch (IOException e1) {
			// IO Exception, let the user try again
			JOptionPane.showMessageDialog(mainWindow,
					"Unknown IO Exception, please try again");
			e1.printStackTrace(); // Print error message for
									// debugging purposes from the
									// user
			return false;
		}
		
		return true;
	}

}
