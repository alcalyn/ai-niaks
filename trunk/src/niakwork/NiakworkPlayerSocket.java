
package niakwork;

import java.net.Socket;

public class NiakworkPlayerSocket extends NiakworkSocket {
	
	
	
	public NiakworkPlayerSocket(Niakwork niakwork, Socket socket) {
		super(niakwork, socket);
	}
	
	
	public void queryAcceptJoin() {
		send(NiakworkQuery.OK_COME_ON);
	}
	
	public void queryDenyJoin() {
		send(NiakworkQuery.DENY_JOIN);
	}
	
	public void queryGameAlreadyStarted() {
		send(NiakworkQuery.DENY_JOIN_GAME_STARTED);
	}
	
	

	@Override
	public void received(NiakworkQuery nquery) {
		System.out.println("NPlayersocket recoit : "+nquery.opcode);
		
		if(nquery.is(NiakworkQuery.I_WANT_TO_JOIN)) {
			niakwork.getNiaks().niakworkClientWantJoin(this, nquery.arg(0));
		}
	}

}
