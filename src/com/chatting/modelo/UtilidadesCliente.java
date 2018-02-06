package com.chatting.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UtilidadesCliente {

	private BufferedReader entrada;
	private PrintWriter salida;
	private Socket cliente;
	
	public UtilidadesCliente(Socket cliente) throws IOException {
		this.cliente = cliente;
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream());
	}
	
	public void enviar(String texto) {
		salida.println(texto);
	}
	
	public String enviarComando(String comando) {
		String respuesta;
		salida.println(comando);
		try {
			respuesta = entrada.readLine();
		} catch (IOException e) {
			respuesta = "<CLIENT> No se ha recibido respuesta a comando.";
		}
		
		return respuesta;
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
