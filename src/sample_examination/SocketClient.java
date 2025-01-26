package sample_examination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClient {

	private Socket socket;
	private String address;
	private int port;
	private PrintWriter pw;
	private BufferedReader bfr;
	private Scanner scanner = new Scanner(System.in);
	
	public SocketClient(String address, int port) throws UnknownHostException, IOException {
		this.address = address;
		this.port = port;
		socket = new Socket(address, port);
		pw = new PrintWriter(socket.getOutputStream(),true);
		bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void stopClient() {
		try {
			socket.close();
			System.out.println("Cliente - Cierra conexión.");
		} catch (IOException e) {
			System.err.println("Error crítico");
		}
	}
	

	
	private boolean checkBufferedReader(String message) {

		if("#Finalizado#".equals(message)) {
			System.out.println("Fin de la conexión.");
			return false;
		} 
		return true;
	}

	public void work() throws IOException {
		boolean work = true;
		String message;
		
		while(work) {
			System.out.print("Introducir mensaje: ");
			message = scanner.nextLine();
			pw.println(message);
						
			message = bfr.readLine();
			System.out.println(message.equals("#Error#")?"Mensaje no adecuadamente formateado para su tratamiento.":message);
				
			work = checkBufferedReader(message);
		}
	}
	
	
	public static void main(String[] args) {
		SocketClient sc = null;
		try {
			sc = new SocketClient("localhost", 9999);
			sc.work();
			
		} catch (IOException e) {	
			System.err.println("El servidor no responde");
		}finally {
			sc.stopClient();
		}
	}
}
