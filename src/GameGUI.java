import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import mastermind.gui.RoundButton;


public class GameGUI extends JFrame {
	private ArrayList<ArrayList<JButton>> feedBack = new ArrayList<ArrayList<JButton>>();
	private ArrayList<ArrayList<RoundButton>> guess = new ArrayList<ArrayList<RoundButton>>();
	private JButton submit = new JButton("Submit");
	private JButton undo = new JButton("Undo");
	private JCheckBox computer = new JCheckBox("Computer Code Breaker");
	private JCheckBox logging = new JCheckBox("logging");
	
	
	public GameGUI(){
		super();
		JPanel guessPanel = new JPanel();
		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new GridLayout(4, 1));
		GridLayout guessLayout = new GridLayout(10, 5);
		guessLayout.setVgap(5);
		guessPanel.setLayout(guessLayout);
		checkPanel.add(computer);
		checkPanel.add(logging);
		checkPanel.add(submit);
		checkPanel.add(undo);
		
		for (int i = 0; i < 10; i++){
			JPanel feedBackPanel = new JPanel();
			feedBackPanel.setLayout(new GridLayout(2, 2));
			ArrayList<JButton> feedBackRow = new ArrayList<JButton>();
			ArrayList<RoundButton> guessRow = new ArrayList<RoundButton>();
			
			guessPanel.add(feedBackPanel);
			for (int n = 0; n < 4; n++){
				JButton temp = new JButton("f");
				RoundButton temp2 = new RoundButton("g");
				temp.setEnabled(false);
				temp.setVisible(false);
				temp2.setVisible(false);
				guessRow.add(temp2);
				guessPanel.add(temp2);
				feedBackRow.add(temp);
				feedBackPanel.add(temp);
			}
			feedBack.add(feedBackRow);
			guess.add(guessRow);
		}
		rowVisible(0);
		setLayout(new FlowLayout());
		add(guessPanel);
		add(checkPanel);
		
		
		
	}
	
	private void rowVisible(int r){
		for (int i = 0; i < 4; i++){
			guess.get(r).get(i).setVisible(true);
			feedBack.get(r).get(i).setVisible(true);
		}
	}
	
	
	private void submitVisible(){
		//uses xor to toggle
		submit.setVisible(true ^ submit.isVisible());
	}
	
	public static void main(String[] args){
		GameGUI gui = new GameGUI();
		gui.setVisible(true);
		gui.setLookAndFeel();
	}
	
	private void setLookAndFeel() {
		try {
			if (UIManager.getSystemLookAndFeelClassName().contains("Aqua")
					|| UIManager.getSystemLookAndFeelClassName().contains(
							"Windows")) {
				// System.out.println("Going to system theme!");
				throw new Exception("Prefer native look and feel");
			}
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				// Prefer system looks over Nimbus to keep consistency
				// Although it's tempting to get XP out of here since
				// it still looks bad...
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			// Ignore and continue
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
				// System.out.println(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				// e.printStackTrace();
				// Ignore again... wow what's installed in here o_0?
			}
		}
	}
}
