package socket03;

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
		int count = 0;
		int num;
		int lower = 251;
		int larger = 252;
		int congratulation = 253;
		boolean continueSendNumbers = true;
		do {
			System.out.print("Introduzca un número entre 0 & 250: ");
			num = scanner.nextInt();
			count++;
			if (num >= 0 && num <= 250) {
				os.write(num);
				num = is.read();
				if(num == congratulation) {
					System.out.println("Enhorabuena, has acertado el número.");
					continueSendNumbers = false;
				}else if(num == lower) {
					System.out.println("Lo sentimos, el número secreto es mayor");
				}else if(num == larger) {
					System.out.println("Lo sentimos, el número secreto es menor");
				}
			}else {
				continueSendNumbers = false;
			}
		} while (continueSendNumbers);
		System.out.println("El número de intentos ha sido " +  count);
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
