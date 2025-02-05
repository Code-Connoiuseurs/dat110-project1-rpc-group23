package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCLocalStub;
import no.hvl.dat110.rpc.RPCUtils;

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public void write (String message) {
		
		// implement marshalling, call and unmarshalling for write RPC method
		byte[] param = RPCUtils.marshallString(message);
		
		byte[] response = rpcclient.call((byte) Common.WRITE_RPCID, param);

		RPCUtils.unmarshallVoid(response);
	}
}
