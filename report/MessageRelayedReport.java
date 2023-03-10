package report;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.DTNHost;
import core.Message;
import core.MessageListener;

public class MessageRelayedReport extends Report implements MessageListener{
	
	private Map<DTNHost,Integer> forwardCounts;

	/** Constructor */
	public MessageRelayedReport() {
		init();
	}

	@Override
	protected void init() {
		super.init();
		this.forwardCounts = new HashMap<>();

	}


	public void messageDeleted(Message m, DTNHost where, boolean dropped) {
	}


	public void messageTransferAborted(Message m, DTNHost from, DTNHost to) {
	}


	public void messageTransferred(Message m, DTNHost from, DTNHost to,
								   boolean finalTarget) {

		int hostMessageRelayed =this.getMessageRelayed(from);
		if (forwardCounts.containsKey(from)){
			forwardCounts.put(from,hostMessageRelayed+1);
		} else {
			forwardCounts.put(from, 1);
		}
	}


	public void newMessage(Message m) {
	}


	public void messageTransferStarted(Message m, DTNHost from, DTNHost to) {
	}


	@Override
	public void done() {

            write("Host/Relay");
		for (Map.Entry<DTNHost, Integer> entry : forwardCounts.entrySet()) {
			DTNHost a = entry.getKey();
			Integer b = a.getAddress();

			write(b +" "+ ' ' + entry.getValue());
		}

		super.done();
	}

	private int getMessageRelayed(DTNHost host){
		if (forwardCounts.containsKey(host)){
			return forwardCounts.get(host);
		} else {
			return 0;
		}
	}

}
