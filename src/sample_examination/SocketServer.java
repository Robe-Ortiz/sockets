package sample_examination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class SocketServer {

	private ServerSocket serverSocket;
	private int port;
	private Random random = new Random();
	
	public SocketServer(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(port);
		System.out.println("Server -  Servidor levantado.");
	}
	
	public void start() throws IOException {
		do {
			System.out.println("Server - Esperando nueva conexión...");
			new SocketManager(serverSocket.accept()).start();;
			System.out.println("Server -  Conexión desviada al gestor.");
		}while(true);
	}
	
	public void stopServer() throws IOException {
		serverSocket.close();
		System.out.println("Server - Servidor detenido correctamente");
	}
	
	
	private class SocketManager extends Thread{
		
		private Socket socket;
		private PrintWriter pw;
		private BufferedReader bfr;
		
		private SocketManager(Socket socket) throws IOException {
			this.socket = socket;
			pw = new PrintWriter(socket.getOutputStream(),true);
			bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Gestor - Gestor creado correctamente.");
		}
		
		private String error() {
			return String.format("#Error#");
		}
		
		private String numberRange(String[] stringMessage) {
			String message = "";
			int numberStart = Integer.parseInt(stringMessage[2]);
			int numberEnd = Integer.parseInt(stringMessage[3]);	
			
			for (int i = numberStart; i <= numberEnd; i++) {
			    message += i + "|";
			}
			
			return message;			
		}
		
		private String numberRandom(String[] stringMessage) {
			String message = "";
			int numberStart = Integer.parseInt(stringMessage[2]);
			int numberEnd = Integer.parseInt(stringMessage[3]);	
			
			int number = random.nextInt(numberStart,numberEnd+1);
			
			return String.format("" + number);
		}
		
		private String checkBufferedReader(String message) {
			
			
			if("#Fin#".equals(message)) return String.format("#Finalizado#");
			
			if(!message.startsWith("#") || !message.endsWith("#")) {
				System.err.println("Error de formato al inicio o al final");
				return error();
			} 
			
			String[] stringMessage = message.split("#");
			
			if(stringMessage.length != 4) {
				System.err.println("Error de formato, no contiene tres partes");
				return error();
			} 	
					
			int numberStart, numberEnd;
			
			try {
				numberStart = Integer.parseInt(stringMessage[2]);
				numberEnd = Integer.parseInt(stringMessage[3]);			
			}catch (Exception e) {
				System.err.println("Error en el casteo");
				return error();
			}
			
			if(numberStart >= numberEnd) {
				System.err.println("El número de inicio debe ser más pequeño que el final");
				return error();
			} 
			
			if(stringMessage[1].equals("Listado números")) {
				return numberRange(stringMessage);
			}else if(stringMessage[1].equals("Numero aleatorio")) {
				return numberRandom(stringMessage);
			}else {
				System.err.println("Instrucción errónea");
				return error();
			}						
		}
		
		private boolean checkPrintWriter(String message) {
			if("#Finalizado#".equals(message)) return false;
			return true;
		}
		
		
		private void work() throws IOException {
			boolean work = true;
			String message;
			while(work) {
				message = bfr.readLine();
				System.out.println("Gestor -  Mensaje recibido: " + message);
				message = checkBufferedReader(message);				
				pw.println(message);
				System.out.println("Gestor -  Mensaje enviado: " + message);
				work = checkPrintWriter(message);
			}
		}
		
		private void stopManager() {
			try {
				bfr.close();
				pw.close();
				socket.close();
				System.out.println("Gestor - Gestor cierra conexión.");
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}

			
		}

		@Override
		public void run() {
			try {
				work();
			} catch (IOException e) {
				System.err.println("Gestor - Cliente no responde");
			}finally {
				stopManager();
			}
		}	
	}
	
	public static void main(String[] args) {
		try {
			SocketServer ss = new SocketServer(9_999);
			ss.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
