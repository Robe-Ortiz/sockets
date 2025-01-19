package socket04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketCliente01 {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private String address;
	private int port;
	private Scanner scanner = new Scanner(System.in);
	
	public SocketCliente01(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void start() throws UnknownHostException, IOException {
		this.socket = new Socket(address,port);
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}
	
	public void work() throws IOException {
		do {
			System.out.println("Escribe un número para contactar con el servidor entre el 0 & 254: ");
			int num = scanner.nextInt();
			
			if(num < 0 || num > 254) break;
			
			os.write(num);
			System.out.println("El server contesta " + is.read());
		}while(true);
		stop();
	}
	
	public void stop() throws IOException {
		is.close();
		os.close();
		socket.close();
		System.out.println("El cliente ha cerrado conexión.");
	}
	
	public static void main(String[] args) {
		SocketCliente01 sc01 = new SocketCliente01("localhost", 9999);
		try {
			sc01.start();
			sc01.work();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
