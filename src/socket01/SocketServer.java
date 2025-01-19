package socket01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private int port;
	
	public SocketServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.port = port;
	}
	
	public void start() throws IOException {
		this.socket = serverSocket.accept();
		this.is = socket.getInputStream();
		this.os = socket.getOutputStream();
	}
	
	public void stop() throws IOException {
		is.close();
		os.close();
		socket.close();
		serverSocket.close();
	}
	
	public static void main(String[] args) {
		try {
			SocketServer ss = new SocketServer(9999);
			ss.start();
			System.out.println("Server escuchando...");
			int num = ss.is.read();
			System.out.println("Server ha leído el número " + num);
			num += 1;
			ss.os.write(num);
			System.out.println("Server ha envíado el número " + num);
			
		} catch (IOException e) {
			//System.err.println(e.getMessage());
		}
	}
}
