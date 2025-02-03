package no.hvl.dat110.messaging;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = null;
		byte[] data;
		
		// TODO - START
		// Encapsulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer
		
		data = message.getData();
		
		segment = new byte[SEGMENTSIZE];
		
		segment[0] = (byte) data.length;
		
		for (int i = 0; i < data.length; i++) {
			segment[i+1] = data[i];
		}
			
		// TODO - END
		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;
		
		// TODO - START
		// Decapsulate segment and put received payload data into a message

		int dataLength = (int) segment[0];
		
		byte[] data = new byte[dataLength];
		
		for (int i = 0; i < dataLength; i++) {
			data[i] = segment[i+1];
		}

		message = new Message(data);
		
		// TODO - END
		
		return message;
		
	}
	
}
