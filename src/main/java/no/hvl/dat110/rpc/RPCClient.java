package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.MessagingClient;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;

	public RPCClient(String server, int port) {

		msgclient = new MessagingClient(server, port);
	}

	public void connect() {

		// connect using the RPC client
		connection = msgclient.connect();
	}

	public void disconnect() {

		// disconnect by closing the underlying messaging connection
		connection.close();
	}

	/*
	 * Make a remote call om the method on the RPC server by sending an RPC request
	 * message and receive an RPC reply message
	 * 
	 * rpcid is the identifier on the server side of the method to be called param
	 * is the marshalled parameter of the method to be called
	 */

	public byte[] call(byte rpcid, byte[] param) {

		byte[] returnval = null;

		/*
		 * 
		 * The rpcid and param must be encapsulated according to the RPC message format
		 * 
		 * The return value from the RPC call must be decapsulated according to the RPC
		 * message format
		 * 
		 */
		byte[] data = RPCUtils.encapsulate(rpcid, param);
		Message msgToSend = new Message(data);
		connection.send(msgToSend);

		Message receivedMsg = connection.receive();
		byte[] receivedData = receivedMsg.getData();
		returnval = RPCUtils.decapsulate(receivedData);

		return returnval;

	}

}
