package niakwork;

public class NiakworkQuery {
	
	public static final String
		I_WANT_TO_JOIN = "JOIN",
		OK_COME_ON = "OK_JOIN",
		DENY_JOIN = "DENY_JOIN",
		DENY_JOIN_GAME_STARTED = "DENY_JOIN_GAME_STARTED",
		GAME_STARTED = "GAME_STARTED";
	
	
	
	
	public String opcode;
	public String[] args;
	
	
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
	
	
	public boolean is(String opcode) {
		return this.opcode.equalsIgnoreCase(opcode);
	}
	
	public String arg(int n) {
		if(args == null || n >= args.length) {
			return null;
		} else {
			return args[n];
		}
	}
	
	
	public String toString() {
		String s = opcode;
		
		if(args != null) {
			for (String a : args) {
				s += " " + a;
			}
		}
		
		return s;
	}

}
