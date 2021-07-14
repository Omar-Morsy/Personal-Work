package eg.edu.guc.yugioh.exceptions;

public class UnknownCardTypeException extends UnexpectedFormatException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unknownType;
	
	public UnknownCardTypeException(String s, int sl, String sf) {
		super(s,sl);
		setUnknownType(sf);
		
	}

	public String getUnknownType() {
		return unknownType;
	}

	public void setUnknownType(String unknownType) {
		this.unknownType = unknownType;
	}
}

