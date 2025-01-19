package socket02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketCliente {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private String address;
	private int port;
	private Scanner scanner = new Scanner(System.in);

	public SocketCliente(String address, int port) throws UnknownHostException, IOException {
		this.address = address;
		this.port = port;
		socket = new Socket(address, port);
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}

	public void sendNumbers() throws IOException {
		int num;
		boolean continueSendNumbers = true;
		do {
			System.out.print("Introduzca un número: ");
			num = scanner.nextInt();
			if (num >= 0 && num <= 254) {
				os.write(num);
				System.out.println("Cliente envía el número " + num);
				num = is.read();
				System.out.println("Cliente recíbe el número " + num);
			}else {
				continueSendNumbers = false;
			}
		} while (continueSendNumbers);
	}

	public void stop() throws IOException {
		is.close();
		os.close();
		socket.close();
		System.out.println("Cliente cierra conexión.");
	}

	public static void main(String[] args) {
		try {
			SocketCliente sc = new SocketCliente("localhost", 9999);
			System.out.println("Cliente arrancado");

			sc.sendNumbers();

			sc.stop();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
