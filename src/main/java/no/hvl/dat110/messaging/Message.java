package no.hvl.dat110.messaging;

public class Message {

	// the up to 127 bytes of data (payload) that a message can hold
	private byte[] data;

	// construction a Message with the data provided
	public Message(byte[] data) {
		if(data == null || data.length > 127) {
			throw new IllegalArgumentException("Data must be between 1 and 127 bytes.");
		}
		this.data = data.clone();			
	}
	
	public byte[] getData() {
		return this.data.clone(); 
	}

}
