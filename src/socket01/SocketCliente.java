package socket01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketCliente {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private String address;
	private int port;
	
	
	public SocketCliente(String address, int port) throws UnknownHostException, IOException {
		this.address = address;
		this.port = port;
		socket = new Socket(address, port);
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}
	
	public static void main(String[] args) {
		try {
			SocketCliente sc = new SocketCliente("localhost", 9999);
			System.out.println("Cliente arrancado");
			int num = 10;
			sc.os.write(num);
			System.out.println("Cliente envía el número " + num);
			num = sc.is.read();
			System.out.println("Cliente recíbe el número " + num);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
