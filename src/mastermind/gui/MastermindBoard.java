package mastermind.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.FeedbackPeg;
import mastermind.core.Guess;
import mastermind.core.PlayList;
import mastermind.interfaces.Observer;

public class MastermindBoard extends JPanel implements Observer{
	
	private PlayList data;
	private ArrayList<Row> rows;
	private final ColorPeg[] availableColors;
	
	public MastermindBoard(PlayList model){
		data = model;
		rows = new ArrayList<Row>();
		availableColors = new ColorPeg[]{ColorPeg.BLACK , ColorPeg.BLUE, ColorPeg.GREEN, ColorPeg.RED, ColorPeg.WHITE, ColorPeg.YELLOW};
		notifyChange();
		this.register();
	}

	@Override
	public void register() {
		data.register(this);
	}

	@Override
	public void notifyChange() {
		this.removeAll(); //Clear the JPanel
		
		System.out.println("UPDATING!!");
		
		rows.clear();
		
		for(int i = 0; i < PlayList.NUM_OF_ROWS; i++){
			Guess g = data.getMove(i);
			//TODO Fix the law of demeter here, major breakage of the law here
			//     plus its not pretty or readable.
			if(g == null){
				g = new Guess();
			}
			Object f = g.getFeedback();
			if(f == null){
				rows.add(new Row(null, g.getCode().getPegs()));
			}else{
				rows.add(new Row(g.getFeedback().getRawFeedback(), 
						g.getCode().getPegs()));
			}
		}
		
		this.setLayout(new GridLayout(0,1));
		
		for(Row r : rows){
			this.add(r);
		}
		
		
	}
	
	public ColorPeg[] getLastGuess(){
		for(int i = 0; i < PlayList.NUM_OF_ROWS; i++){
			if(!rows.get(i).hasFeedback()){
				return rows.get(i).getCode();
			}
		}
		//If there are now guesses then we have a problem
		throw new IllegalStateException("The program has already gotten feedback for all it's rows, the game should be over.");
	}
	
	private class Row extends JPanel{

		private static final long serialVersionUID = 1L;
		private FeedbackPeg[] feedback;
		private boolean hasFeedback;
		private ColorPeg[] code;
		
		public Row(FeedbackPeg[] feedback, ColorPeg[] code){
			//Check if this data even exists, most of the time
			//it wont exist so we make it empty
			if(feedback != null){
				this.feedback = feedback;
				this.hasFeedback = true;
			}
			else{
				this.feedback = new FeedbackPeg[Code.NUM_OF_PEGS];
				this.hasFeedback = false;
			}
			
			if(code != null)
				this.code = code;
			else
				this.code = new ColorPeg[Code.NUM_OF_PEGS];
			
			build();
		}
		
		private void build(){
			this.setLayout(new BorderLayout());
			
			//Add the feedback panel to this row
			JPanel feedbackPanel = new JPanel(new GridLayout(0,2));
			
			for(int i = 0; i < Code.NUM_OF_PEGS; i ++){
				JButton peg = new JButton();
				peg.setFocusable(false); //Can't do just setEnabeled for some looks and feels
				
				
				if(feedback[i] == null){
					//Don't do anything
					//peg.setBackground(Color.RED); //To show something there...
					peg.setToolTipText("Empty");
				} else{
					peg.setBackground(feedback[i].getColor());
					peg.setToolTipText(feedback[i].getName());//Add tooltip to the button, just because?
				}
				
				
				feedbackPanel.add(peg);
			}
			
			this.add(feedbackPanel, BorderLayout.WEST);
			
			//Set up the code for this row
			
			JPanel codePanel = new JPanel(new GridLayout(0, Code.NUM_OF_PEGS));
			
			for(int i = 0; i < Code.NUM_OF_PEGS; i++){
				JButton peg = new JButton();
				peg.setName(Integer.toString(i)); //This is how we know where we are
				
				if(code[i] == null){
					//Don't do anything
					//peg.setBackground(Color.RED); //To show something there...
					peg.setToolTipText("Empty");
				} else{
					peg.setBackground(code[i].getColor());
				}
				
				//Action Listener to go through all the colors
				peg.addActionListener(new ActionListener(){
					private int index = -1;

					@Override
					public void actionPerformed(ActionEvent e) {
						JButton self = (JButton)e.getSource();
						if(index == -1){
							index = 0;
						} else{
							index = (index + 1)%availableColors.length;
						}
						
						self.setBackground(availableColors[index].getColor());
						
						//Some hackish fix :D
						
						String number = self.getName();
						int index = Integer.parseInt(number);
						code[index] = availableColors[this.index];
					}
					
				});
				
				
				codePanel.add(peg);
			}
			
			this.add(codePanel, BorderLayout.CENTER);
		}
		
		/**
		 * Returns whether this class had feedback when built
		 * @return
		 */
		public boolean hasFeedback(){
			return this.hasFeedback;
		}
		
		public ColorPeg[] getCode(){
			return this.code;
		}
		
	}

}
