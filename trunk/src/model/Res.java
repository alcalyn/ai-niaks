package model;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Res {
	
	private static URL getRes(String res) {
		return new Res().getClass().getResource("/res/"+res);
	}
	
	public static Image getImage(String s) {
		try {
			return ImageIO.read(getRes(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
