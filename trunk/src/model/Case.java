package model;

public class Case {

	private Coords coordCase;
	private boolean empty;
	
	public Case (Coords c, boolean b){
		this.coordCase= c;
		this.empty = b;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty() {
		this.empty = true;
	}
	
	public void setOccupied (){
		this.empty = false;
	}
	
	public Coords getCoordCase() {
		return coordCase;
	}
	
}
