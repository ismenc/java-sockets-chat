package SocketsTCP.Ejem1Socket;

import java.io.*;
import java.net.*;

public class ClienteEjemplo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String host = "localhost";
		int puerto = 6000;
		String cadenaRecibida;
		
		try{
			System.out.println("Cola de clientes abierta");
			
			for(int i = 0; i < 5; i++) {
			
				Socket cliente = new Socket (host, puerto);
				
				DataOutputStream flujoSalida = new DataOutputStream( cliente.getOutputStream());
				flujoSalida.writeUTF("Saludos al servidor desde el cliente ");
				flujoSalida.writeInt(i);
				
				DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
				cadenaRecibida = flujoEntrada.readUTF();
				System.out.println("Cliente recibe: " + cadenaRecibida);
				
				flujoEntrada.close();
				flujoSalida.close();
				cliente.close();
			}
		}
		
		catch ( ConnectException e){
			System.out.println("Imposible conectar con el servidor");
		}
		catch  ( IOException e)	{
			e.printStackTrace();
		}
		
	}

}
