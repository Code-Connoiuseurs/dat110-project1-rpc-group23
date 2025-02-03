package no.hvl.dat110.messaging;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {

		byte[] segment = null;
		byte[] data;

		// encapulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer
		segment = new byte[SEGMENTSIZE];
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
		byte[] payload = new byte[segment[0]];
		int length = segment[0];
		for (int i = 1; i < length + 1; i++) {
			payload[i - 1] = segment[i];
		}

		message = new Message(payload);

		return message;

	}

}
