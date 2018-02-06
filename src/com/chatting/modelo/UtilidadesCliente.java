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
		String cadenaRecibida = "";
		do {
			try {
				cadenaRecibida = entrada.readLine();
			} catch (IOException e) { cadenaRecibida = ""; }
		} while(!cadenaRecibida.trim().contains(Constantes.CODIGO_FIN_CADENA));
			salida.println(Constantes.CODIGO_RECIBIDO_CADENA);
		return cadenaRecibida.subSequence(0, cadenaRecibida.length()-(Constantes.CODIGO_FIN_CADENA).length()).toString().trim();
	}
	
	/**
	 * Envía un dato hasta que reciba confimación de llegada.
	 * @param cadena
	 */
	public void enviarTCP(String cadena) {
		String comprobante;
		do {
			salida.println(cadena + Constantes.CODIGO_FIN_CADENA);
			try {
				comprobante = entrada.readLine().trim();
			} catch (IOException e) { comprobante = "";	}
		}while(!comprobante.equals(Constantes.CODIGO_RECIBIDO_CADENA));
	}
	
	public void cerrarConexion() {
		try {
			cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
