package model;

public class IllegalMoveNiaksException extends NiaksException {
	
	private static final long serialVersionUID = -1225693269436343105L;
	
	
	private Coup coup;
	

	public IllegalMoveNiaksException(Coup coup, String msg) {
		super(msg);
		this.coup = coup;
	}


	public Coup getCoup() {
		return coup;
	}
	

}
