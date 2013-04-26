package model;

import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Res {
	
	
	
	private static URL getRes(String res) {
		return new Res().getClass().getResource("/"+res);
	}
	
	private static InputStream getResStream(String res) {
		return new Res().getClass().getResourceAsStream("/"+res);
	}
	
	
	
	public static String getFileContent(String r) {
		String content = null;
		
		try {
			BufferedInputStream bis = new BufferedInputStream(getResStream(r));
			byte[] chars = new byte[(int) bis.available()];
			bis.read(chars);
			content = new String(chars);
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static Image getImage(String res) {
		ImageIcon img = new ImageIcon(getRes(res));
		return img.getImage();
	}
	
	public static Icon getIcon(String res) {
		return new ImageIcon(getRes(res));
	}
	
	public static Icon getIcon(String res, Dimension dimension) {
		return new ImageIcon(getImage(res).getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH));
	}
	
	
}
