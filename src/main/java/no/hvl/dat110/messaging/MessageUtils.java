package no.hvl.dat110.messaging;

import java.util.Arrays;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = new byte[SEGMENTSIZE];
		byte[] data = message.getData();
		
		// encapulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer

		int mLength = data.length;
		segment[0] = (byte) mLength;
		System.arraycopy(data, 0, segment, 1, mLength);

		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {
		
		int mLength = segment[0];

		// decapsulate segment and put received payload data into a message
		if(mLength < 0 || mLength > 127) {
			throw new IllegalArgumentException("Invalid message length " + mLength);
		}
		
		byte[] data = Arrays.copyOfRange(segment, 1, mLength + 1);
		return new Message(data);
		
	}
	
}
