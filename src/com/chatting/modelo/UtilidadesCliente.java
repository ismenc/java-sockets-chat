package com.chatting.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.chatting.Constantes;
import com.chatting.controlador.ControladorCliente;
import com.chatting.vista.VistaCliente;

/**
 * Clase que provee de las herramientas para manipular datos con el cliente.
 * @author Ismael Núñez
 *
 */
public class UtilidadesCliente {

	private VistaCliente vista;
	private ControladorCliente controlador;
	private Socket cliente;
	
	private BufferedReader entrada;
	private PrintWriter salida;
	
	/* ======================== Métodos ========================== */
	
	public UtilidadesCliente(Socket cliente, VistaCliente vista, ControladorCliente controlador) throws IOException {
		this.cliente = cliente;
		this.vista = vista;
		this.controlador = controlador;
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream(), true);
	}
	
	/* ======================== Métodos ========================== */
	
	/**
	 * Espera hasta recibir una cadena.
	 * @return
	 * @throws IOException 
	 */
	public String recibirTCP() throws IOException {
		String cadenaRecibida = null;
		do {
				cadenaRecibida = entrada.readLine();
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
	
	/**
	 * Cerramos la conexión del socket.
	 */
	public void cerrarConexion() {
		try {
			entrada.close();
			salida.close();
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Interpretamos el mensaje leido en el cliente.
     */
    public void handleMessage() {
		try {
			String msg = recibirTCP();
		
			switch(msg.trim()){
				// recibimos código de desconectar.
				case Constantes.CODIGO_SALIDA:
					
					controlador.salir();
					vista.addText("<CLIENT> El servidor se ha apagado");
					
				break;
				// Recibimos actualizar numero clientes
				case Constantes.CODIGO_ACTUALIZAR_CONECTADOS:
					
					vista.setClientes(recibirTCP());
					
				break;
				default: // Recibimos un mensaje normal y corriente
					
					vista.addText(msg);
						
				break;
			}
	    	
		} catch (IOException e) {
			controlador.salir();
			vista.addText("<CLIENT> Servidor desconectado.");
		}
    }
}
