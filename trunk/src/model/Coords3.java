package model;

public class Coords3 {
	
	public int x, y, z;

	public Coords3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coords3() {
	}
	
	
	public Coords toCoords() {
		return new Coords(x - z, y + z);
	}
	
	
	public Coords3 add(Coords3 c) {
		return new Coords3(x + c.x, y + c.y, z + c.z);
	}
	
	public Coords3 mul(Coords coords) {
		if(x == 0) return new Coords3(0, coords.x * this.y, coords.y * this.z);
		if(y == 0) return new Coords3(coords.x * this.x, 0, coords.y * this.z);
		if(z == 0) return new Coords3(coords.x * this.x, coords.y * this.y, 0);
		
		return null;
	}
	
	public Coords3 mul(int c) {
		return new Coords3(x * c, y * c, z * c);
	}

	
}
