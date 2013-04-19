package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProfilManager {
	
	public static final String app_dir_location = "";
	public static final String app_dir_name = "niaks";
	
	private static File app_dir = null;
	private static boolean init = false;
	
	
	public static void initDir() {
		if(!init) {
			app_dir = new File(app_dir_location+app_dir_name);
			
			if(!app_dir.isDirectory()) {
				if(!app_dir.mkdir()) {
					System.out.println("ProfilManager n'a pas pu creer le dossier de l'application");
				}
			}
			
			init = true;
		}
	}
	
	public static String[] getPseudos() {
		initDir();
		
		ArrayList<String> pseudos = new ArrayList<String>();
		
		for (File f : app_dir.listFiles()) {
			if(f.isFile()) {
				pseudos.add(f.getName());
			}
		}
		
		String [] ret = new String[pseudos.size()];
		pseudos.toArray(ret);
		return ret;
	}
	
	public static void addPseudo(String pseudo) {
		initDir();
		
		String [] pseudos = getPseudos();
		
		boolean exists = false;
		
		for (String string : pseudos) {
			if(string.equals(pseudos)) {
				exists = true;
				break;
			}
		}
		
		if(!exists) {
			try {
				new File(app_dir_location+app_dir_name+File.separator+pseudo).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getPseudo() {
		String [] pseudos = getPseudos();
		
		if(pseudos.length == 1) {
			return pseudos[0];
		} else {
			return null;
		}
	}
	
}
