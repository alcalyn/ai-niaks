package niakwork;

import java.util.EventListener;

public interface NiakworkListener extends EventListener {
	
	
	public void hostFound(NiakworkHostSocket socket);
	
	public void clientFound(NiakworkPlayerSocket socket);
	
}
