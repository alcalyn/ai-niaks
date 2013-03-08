package model;

import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Coords {
	
	public int x, y;

	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coords() {
	}
	
	public Coords(Coords copy) {
		this.x = copy.x;
		this.y = copy.y;
	}
	
	
	public Coords mul(int coef) {
		return new Coords(x * coef, y * coef);
	}
	
	public Coords add(Coords c) {
		return new Coords(x + c.x, y + c.y);
	}
	
	public Coords rotate(double rotation) {
		if(rotation != 0) {
			Point a = new Point(x, y);
			AffineTransform.getRotateInstance(rotation).transform(a, a);
			return new Coords(a.x, a.y);
		}
		
		return new Coords(x, y);
	}
	
	public Coords toWindow() {
		return toWindow(0);
	}
	
	public Coords toWindow(double rotation) {
		int _x = x + y/2;
		int _y = - (int) Math.round((y * Math.sqrt(3)) / 2);
		
		if(rotation != 0) {
			Point a = new Point(_x, _y);
			AffineTransform.getRotateInstance(rotation).transform(a, a);
			_x = a.x;
			_y = a.y;
		}
		
		return new Coords(_x, _y);
	}
	
	public boolean equals(Coords other) {
		return other.x == x && other.y == y ;
	}
	
	
	public String toString() {
		return "Coords ("+x+", "+y+")";
	}
	
	
}
