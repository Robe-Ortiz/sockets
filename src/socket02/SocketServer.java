package socket02;

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
		System.out.println("Server esperando conexión ...");
		this.socket = serverSocket.accept();
		this.is = socket.getInputStream();
		this.os = socket.getOutputStream();
		System.out.println("Conexión establecida.");
		
	}
	
	public void work() throws IOException {
		int num;
		int controlNumber = -1;
		boolean continueWorking = true;
		do {
			num = is.read();
			
			if(num == controlNumber) {
				continueWorking = false;
			}else {
				System.out.println("Server ha leído el número " + num);
				os.write(++num);
				System.out.println("Server ha envíado el número " + num);
			}
		}while(continueWorking);
		
	}
	
	
	public void stop() throws IOException {
		is.close();
		os.close();
		socket.close();
		serverSocket.close();
		System.out.println("Server cierra conexión");
	}
	
	public static void main(String[] args) {
		try {
			SocketServer ss = new SocketServer(9999);
			ss.start();
			ss.work();
			ss.stop();			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
