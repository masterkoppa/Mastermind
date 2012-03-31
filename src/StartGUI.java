import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class StartGUI extends JFrame {
	private ArrayList<JButton> secretCode = new ArrayList<JButton>();
	
	public StartGUI(){
		super();
		JPanel codePanel = new JPanel();
		codePanel.setLayout(new GridLayout(2, 1));
		
	}
}
