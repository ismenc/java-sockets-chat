package SocketsTCP.Ejem1Socket;


import java.io.*;
import java.net.*;


public class ServidorEjemplo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try
		{
				
			int numeroPuerto=6000;
			
			ServerSocket servidor = new ServerSocket(numeroPuerto);
			Socket clienteConectado = null;
			
			while(true) {
				System.out.println("Esperando a un cliente...");
				clienteConectado = servidor.accept();
				
				//Creo flujo de entrada con cliente
				InputStream entrada=null;
				entrada = clienteConectado.getInputStream();
				DataInputStream flujoEntrada = new DataInputStream (entrada);
				
				// Recibo el mensaje del cliente
				
				String msgCliente = flujoEntrada.readUTF();
				int numeroCliente = flujoEntrada.readInt();
				System.out.println("Servidor recibe: "+ msgCliente + numeroCliente);
				// Crear fljo de salida al cliente
				
				OutputStream salida = null;
				salida = clienteConectado.getOutputStream();
				DataOutputStream flujoSalida=new DataOutputStream(salida);
				
				
				// Envio un saludo al cliente
				
				flujoSalida.writeUTF("Adiós cliente " + numeroCliente);
				
				// Cerrar streams y socket
				
				entrada.close();
				flujoEntrada.close();
				salida.close();
				flujoSalida.close();
				clienteConectado.close();
			}
			//servidor.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
