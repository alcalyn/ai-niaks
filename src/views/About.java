package views;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class About extends JFrame {
	
	private static final long serialVersionUID = -514697667291393262L;
	
	private static final String about_file = "about.html";
	
	public About() {
		super("A propos du projet");
		
		setSize(500, 300);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		
		JEditorPane pane = new JEditorPane();
		pane.setEditable(false);
		pane.setContentType("text/html");
		try {
			pane.setText(new Scanner(new File(getClass().getResource(about_file).getFile())).useDelimiter("\\A").next());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		panel.add(pane);
		
		panel.setBackground(Color.WHITE);
		add(panel);
		
		setVisible(true);
	}
	
	
	

}