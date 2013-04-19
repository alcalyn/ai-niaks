package exceptions;

import model.Coup;

public class IllegalMoveNiaksException extends NiaksException {
	
	private static final long serialVersionUID = -1225693269436343105L;
	
	
	private Coup coup;
	private boolean important;
	

	public IllegalMoveNiaksException(Coup coup, String msg) {
		super(msg);
		this.coup = coup;
		important = true;
	}
	public IllegalMoveNiaksException(Coup coup, String msg, boolean important) {
		super(msg);
		this.coup = coup;
		this.important = important;
	}

	
	public boolean isImportant() {
		return important;
	}

	public Coup getCoup() {
		return coup;
	}
	

}
