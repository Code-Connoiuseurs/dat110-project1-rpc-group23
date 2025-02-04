package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {
		try {
		byte[] data = message.getData();

		// encapsulate the data contained in the Message and write to the output stream
		outStream.writeByte(data.length);
		outStream.write(data);
		outStream.flush();
		} catch (IOException ex) {
			System.out.println("Send error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public Message receive() {

		// read a segment from the input stream and decapsulate data into a Message
		Message message = null;
		try {
			int mLength = inStream.readByte();

			if(mLength < 1 || mLength > 127) {
				throw new IOException("Invalid message length recieved" + mLength);
			}

			byte[] data = new byte[mLength];
			inStream.readFully(data);	

			message = new Message(data); 

		} catch (IOException ex) {
			System.out.println("Receieve error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return message;
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}