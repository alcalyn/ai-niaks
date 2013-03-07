package model;

public class Coords {
	
	public int x, y;

	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coords() {
	}
	
	
	public Coords mul(int coef) {
		return new Coords(x * coef, y * coef);
	}
	
	public Coords toWindow() {
		return new Coords(x + y/2, - (int) Math.round((y * Math.sqrt(3)) / 2));
	}
	
	public boolean equals(Coords other) {
		return other.x == x && other.y == y ;
	}
	
	
	public String toString() {
		return "Coords ("+x+", "+y+")";
	}
	
	
}
