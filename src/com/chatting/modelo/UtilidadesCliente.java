package com.chatting.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class UtilidadesCliente {

	private BufferedReader entrada;
	private PrintWriter salida;
	private Socket cliente;
	
	public UtilidadesCliente(Socket cliente) throws IOException {
		this.cliente = cliente;
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream(), true);
	}
	
	public String enviarYRecibir(String cadena) throws SocketException {
		String respuesta = "";
		enviarTCP(cadena);
		respuesta = recibirTCP();
		return respuesta;
	}
	
	/**
	 * Espera hasta recibir una cadena y envía confirmación.
	 * @return
	 */
	public String recibirTCP() {
		String cadenaRecibida = null;
		do {
			try {
				cadenaRecibida = entrada.readLine();
			} catch (IOException e) { cadenaRecibida = null; }
		} while(cadenaRecibida==null);
			
		return cadenaRecibida;
	}
	
	/**
	 * Envía un dato hasta que reciba confimación de llegada.
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
