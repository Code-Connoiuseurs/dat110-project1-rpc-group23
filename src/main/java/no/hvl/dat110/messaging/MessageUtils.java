package no.hvl.dat110.messaging;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {

		byte[] segment = new byte[SEGMENTSIZE];
		byte[] data;

		// encapulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer
//		segment = new byte[SEGMENTSIZE];
		data = message.getData();
		segment[0] = (byte) data.length;

		for (int i = 1; i < data.length + 1; i++) {
			segment[i] = data[i - 1];
		}

		return segment;

	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;
		// decapsulate segment and put received payload data into a message
		int length = (int) segment[0];
		if(length == 0) {
			return new Message(new byte[(int)segment[0]]);
		}
		byte[] payload = new byte[length];
		for (int i = 1; i < length + 1; i++) {
			payload[i - 1] = segment[i];
		}

		message = new Message(payload);

		return message;

	}

}
