import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameGUI extends JFrame {
	private ArrayList<ArrayList<JButton>> feedBack = new ArrayList<ArrayList<JButton>>();
	private ArrayList<ArrayList<JButton>> guess = new ArrayList<ArrayList<JButton>>();
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
			ArrayList<JButton> guessRow = new ArrayList<JButton>();
			
			guessPanel.add(feedBackPanel);
			for (int n = 0; n < 4; n++){
				JButton temp = new JButton("f");
				JButton temp2 = new JButton("g");
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
	}
}
