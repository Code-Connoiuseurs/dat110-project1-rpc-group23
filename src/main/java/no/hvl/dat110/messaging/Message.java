package no.hvl.dat110.messaging;

public class Message {

	// the up to 127 bytes of data (payload) that a message can hold
	private byte[] data;

	// construction a Message with the data provided
	public Message(byte[] data) {
		
		// TODO - START
	
		if (data != null && data.length < 128) {
			this.data = new byte[data.length];
			for (int i = 0; i < data.length; i++) {
				this.data[i] = data[i];
			}
		}
			
		
		// TODO - END
	}

	public byte[] getData() {
		return this.data; 
	}

}
