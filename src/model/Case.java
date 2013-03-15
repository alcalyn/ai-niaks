package model;

public class Case {

	private Coords coordCase;
	private boolean empty;
	
	public Case (Coords c){
		this.coordCase= c;
		this.empty = true;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty() {
		this.empty = true;
	}
	
	public void cetOccupied (){
		this.empty = false;
	}
	
	public Coords getCoordCase() {
		return coordCase;
	}
	
	public void setCoordCase(Coords coordCase) {
		this.coordCase = coordCase;
	}
	
}
