package socket04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SocketServidor {

	private ServerSocket serverSocket;
	private int port;
	private Random random = new Random();
	
	public SocketServidor(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.port = port;
		System.out.println("Server ready");
		while(true) {
			Socket socket = serverSocket.accept();
			new SocketGestor(socket).start();
		}
	}
	
	public void stop() throws IOException {
		serverSocket.close();
		System.out.println("Server off");
	}
	
	
	class SocketGestor extends Thread {

		private Socket socket;
		private InputStream is;
		private OutputStream os;
		
		private SocketGestor(Socket socket) throws IOException {
			this.socket = socket;
			is = socket.getInputStream();
			os = socket.getOutputStream();
		}
		
		private void work() throws IOException, InterruptedException {
			int num;
			do {
				int timeOfWaiting = random.nextInt(11);			
				num = is.read();
				if(num < 0 || num > 254) break;
				System.out.println("Gestor generado, esperando " + timeOfWaiting);
				TimeUnit.SECONDS.sleep(timeOfWaiting);
				os.write(timeOfWaiting);
				System.out.println("Número devuelto!");
			}while(true);
			System.out.println("El gestor ha cerrado la conexión.");
		}
		
		public void run() {
			try {
				work();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	
	}
	
	public static void main(String[] args) {
		try {
			SocketServidor ss = new SocketServidor(9999);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
