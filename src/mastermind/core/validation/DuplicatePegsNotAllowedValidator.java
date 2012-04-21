package mastermind.core.validation;

import mastermind.core.Code;
import mastermind.core.ColorPeg;

public class DuplicatePegsNotAllowedValidator implements ICodeValidator{

	@Override
	public Boolean validate(Code c) 
	{
		ColorPeg[] pegs = c.getPegs();

		for(int count = 0; count<pegs.length; count++)
		{
			if(this.hasDuplicate(pegs, pegs[count], count))
				return false;
		}
			
		return true;
	}
	
	/**
	 * Uses the supplied peg to compare to the rest of the code for duplicates
	 * @param pegs
	 * @param cp
	 * @param coloredIndex
	 * @return
	 */
	private Boolean hasDuplicate(ColorPeg[] pegs, ColorPeg cp, int coloredIndex)
	{
		for(int count = 0; count<pegs.length; count++)
		{
			if(count != coloredIndex && pegs[count] == cp)
				return true;
		}
		
		return false;
	}

}
