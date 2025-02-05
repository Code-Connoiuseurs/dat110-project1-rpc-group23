package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;
	
	// hashmap to register RPC methods which are required to extend RPCRemoteImpl
	// the key in the hashmap is the RPC identifier of the method
	private HashMap<Byte,RPCRemoteImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {
		
		// the stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP,this);
		register(RPCCommon.RPIDSTOP, rpcstop);

		System.out.println("RPC SERVER RUN - Services: " + services.size());
			
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {

	    try {

			// - receive a Message containing an RPC request
			Message requestmsg = connection.receive();
			byte[] rpcRequest = requestmsg.getData();

			// - extract the identifier for the RPC method to be invoked from the RPC request
			byte rpcid = rpcRequest[0];
			byte[] param = RPCUtils.decapsulate(rpcRequest);

			// - extract the method's parameter by decapsulating using the RPCUtils
			RPCRemoteImpl method = services.get(rpcid);
			if(method == null) {
				throw new IllegalStateException("No RPC method registered for id: " + rpcid);
			}
			// - lookup the method to be invoked
			byte[] result = method.invoke(param);

			// - invoke the method and pass the param
			byte[] rpcReply = RPCUtils.encapsulate(rpcid, result);

		   // - send back the message containing the RPC reply
		   connection.send(new Message(rpcReply));

			// stop the server if it was stop methods that was called
			if(rpcid == RPCCommon.RPIDSTOP) {
				stop = true;
			}

		} catch(Exception ex) {
			System.out.println("Error during RPC process: " + ex.getMessage());
			}
		}
		stop();
	}
	
	// used by server side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {

		if (connection != null) {
			connection.close();
		} else {
			System.out.println("RPCServer.stop - connection was null");
		}
		
		if (msgserver != null) {
			msgserver.stop();
		} else {
			System.out.println("RPCServer.stop - msgserver was null");
		}
		
	}
}
