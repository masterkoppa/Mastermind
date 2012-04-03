import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import mastermind.core.Code;
import mastermind.core.PlayList;
import mastermind.core.controller.*;
import mastermind.gui.*;

public class Mastermind {
	
	private JFrame mainWindow;
	
	private PlayList playListModel;
	private IGameController mainController;
	private MastermindMain mainView;
	private CodeMakerPanel codemakerView;
	private Code secret;
	
	public Mastermind(){
		
		playListModel = new PlayList();
		
		initGUI();
		
		mainController = new GameController(playListModel, secret);
		mainView = new MastermindMain(mainController, playListModel, secret);
		mainView.startGUI();
		
	}
	
	private void initGUI(){
		codemakerView = new CodeMakerPanel();
		
		mainWindow = new JFrame();
		
		mainWindow.add(codemakerView);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	//TODO Make this a method calls that creates all the GUI objects here
            	//     inside the GUI thread instead of the main thread.
            	mainWindow.setTitle("Mastermind");
            	mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	mainWindow.setSize(800, 600);
        		mainWindow.setVisible(true);
            }
        });
		
		while(codemakerView.getSecret().isEmpty()){
			try {
				Thread.sleep(100);
				if(!mainWindow.isVisible()){
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		secret = codemakerView.getSecret();
		
		System.out.println("Found you!");
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Mastermind();
	}

}
