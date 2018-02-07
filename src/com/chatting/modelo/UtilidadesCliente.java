package com.chatting.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clase que provee de las herramientas para manipular datos con el cliente.
 * @author Ismael Núñez
 *
 */
public class UtilidadesCliente {

	private BufferedReader entrada;
	private PrintWriter salida;
	private Socket cliente;
	
	/* ======================== Métodos ========================== */
	
	public UtilidadesCliente(Socket cliente) throws IOException {
		this.cliente = cliente;
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream(), true);
	}
	
	/* ======================== Métodos ========================== */
	
	/**
	 * Espera hasta recibir una cadena.
	 * @return
	 */
	public String recibirTCP() {
		String cadenaRecibida = null;
		do {
			try {
				cadenaRecibida = entrada.readLine();
			} catch (IOException e) { e.printStackTrace();cadenaRecibida = null; }
		} while(cadenaRecibida==null);
			
		return cadenaRecibida;
	}
	
	/**
	 * Envía un dato.
	 * @param cadena
	 */
	public void enviarTCP(String cadena) {
			salida.println(cadena );
			
	}
	
	public void cerrarConexion() {
		try {
			entrada.close();
			salida.close();
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
