package niakwork;

import java.io.Serializable;

public class NiakworkQuery implements Serializable {
	
	private static final long serialVersionUID = 6561275838191848914L;



	public static final String
		I_WANT_TO_JOIN = "JOIN",
		MY_NAME_IS = "MY_NAME_IS",
		OK_COME_ON = "OK_JOIN",
		DENY_JOIN = "DENY_JOIN",
		DENY_JOIN_GAME_STARTED = "DENY_JOIN_GAME_STARTED",
		UPDATE_PARTIE_PREPARATOR = "UPDATE_PARTIE_PREPARATOR",
		GAME_STARTED = "GAME_STARTED",
		UPDATE_CURRENT_PLAYER = "UPDATE_CURRENT_PLAYER",
		UPDATE_PIONS = "UPDATE_PIONS",
		SOCKET_CLOSE = "TCHAO",
		UPDATE_JOUEUR_WON = "WE_HAVE_A_WINNER";
	
	
	
	
	public String opcode;
	public Object [] args;
	
	
	public NiakworkQuery(String data) {
		String [] tokens = data.split("[ ]+");
		
		this.opcode = tokens[0];
		
		this.args = new String[tokens.length - 1];
		
		for(int i=1;i<tokens.length;i++) {
			args[i-1] = tokens[i];
		}
	}
	
	public NiakworkQuery(String opcode, String arg1) {
		this.opcode = opcode;
		this.args = new String [] {arg1};
	}
	public NiakworkQuery(String opcode, Object o) {
		this.opcode = opcode;
		this.args = new Object [] {o};
	}
	
	
	public NiakworkQuery(String opcode, Object o, Object o2) {
		this.opcode = opcode;
		this.args = new Object [] {o, o2};
	}

	public boolean is(String opcode) {
		return this.opcode.equalsIgnoreCase(opcode);
	}
	
	public Object arg(int n) {
		if(args == null || n >= args.length) {
			return null;
		} else {
			return args[n];
		}
	}
	
	
	public String toString() {
		String s = opcode;
		
		if(args != null) {
			for (Object a : args) {
				s += " " + a.toString();
			}
		}
		
		return s;
	}

}
