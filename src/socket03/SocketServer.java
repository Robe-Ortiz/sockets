package socket03;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketServer {

	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private int port;
	private Random random = new Random();
	
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
		int randomNumber = random.nextInt(251);
		System.out.println("El número secreto es " + randomNumber);
		int num;
		int lower = 251;
		int larger = 252;
		int congratulation = 253;
		int controlNumber = -1;
		boolean continueWorking = true;
		do {
			num = is.read();
			
			if(num == controlNumber)
				continueWorking = false;
			if(num == randomNumber) {
				os.write(congratulation);
				continueWorking = false;
			}else {
				if(num < randomNumber)
					os.write(lower);
				else
					os.write(larger);
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
