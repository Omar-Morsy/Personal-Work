package eg.edu.guc.yugioh.exceptions;

public class UnknownSpellCardException extends UnexpectedFormatException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unknownSpell;
	
	
	public  UnknownSpellCardException(String s, int sl, String us) {
		super(s, sl);
		setUnknownSpell(us);
	}


	public String getUnknownSpell() {
		return unknownSpell;
	}


	public void setUnknownSpell(String unknownSpell) {
		this.unknownSpell = unknownSpell;
	}

}
