package com.chatting.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.chatting.ejecutable.Servidor;
import com.chatting.vista.VistaServidor;

/**
 * Hilo del servidor que tratará con los clientes.
 * @author Ismael Núñez
 *
 */
public class ServerThread extends Thread {

	private Socket cliente;
	private BufferedReader entrada;
	private PrintWriter salida;	
	private VistaServidor vista;
	
	private String nombre;
	
	/* ======================== Constructor y ejecución ========================== */
	
	public ServerThread(VistaServidor vista, Socket cliente) throws IOException {
		this.vista = vista;
		this.cliente = cliente;
		nombre = "";
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream(), true);
		this.cliente.setSoTimeout(500);
	}
	
	public void run() {
		String cadena;
		try {
			do {
				cadena = recibirTCP();
				messageHandler(cadena.trim());
			}while(!cadena.trim().equals(Constantes.CODIGO_SALIDA));
			
			entrada.close();
			salida.close();
			cliente.close();
		}catch(IOException e) { Servidor.imprimirTodos("<SERVER> "+nombre+" desconectado dolorosamente."); }
		vista.setClientesConectados(Servidor.getClientes().getClientesConectados());
	}
	
	/* ======================== Métodos ========================== */
	
	private void messageHandler(String mensaje) {
		switch(mensaje.trim()) {
			case Constantes.CODIGO_INICIAL:
				
				nombre = recibirTCP();
				Servidor.meterCliente(this);
				Servidor.getClientes().actualizarConectados();
		    	Servidor.imprimirTodos("<SERVER> "+ nombre + " se ha unido al chat.");
				
			break;
			case Constantes.CODIGO_NICK:
				
				String nombreAnterior = nombre;
				Servidor.sacarCliente(nombreAnterior);
				nombre = recibirTCP();
				Servidor.imprimirTodos("<SERVER> "+ nombreAnterior + " ha cambiado su nombre por "+ nombre +".");
				Servidor.meterCliente(this);
				
			break;
			case Constantes.CODIGO_SALIDA:
				
				Servidor.imprimirTodos("<SERVER> "+ nombre+ " ha abandonado el chat.");
				Servidor.sacarCliente(nombre);
				
			break;
			case Constantes.CODIGO_LISTAR:
				
				enviarTCP("<SERVER> CLIENTES CONECTADOS: " + new String(Servidor.getClientes().getListaClientes()));
				
			break;
			default:
				
				Servidor.imprimirTodos(nombre+": "+ mensaje);
				
			break;
		}
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
	
	public String getNombre() {
		return nombre;
	}
}



/*
/**
	 * Espera hasta recibir una cadena y envía confirmación.
	 * @return
	 * /
	private String recibirTCP() {
		String cadenaRecibida = "";
		do {
			try {
				cadenaRecibida = entrada.readLine();
			} catch (IOException e) { cadenaRecibida = ""; }
		} while(!cadenaRecibida.trim().contains(Constantes.CODIGO_FIN_CADENA));
			salida.println(Constantes.CODIGO_RECIBIDO_CADENA);
		return cadenaRecibida.subSequence(0, cadenaRecibida.length()-(Constantes.CODIGO_FIN_CADENA).length()).toString();
	}
	
	/**
	 * Envía un dato hasta que reciba confimación de llegada.
	 * @param cadena
	 * /
	public void enviarTCP(String cadena) {
		String comprobante;
		do {
			salida.println(cadena + Constantes.CODIGO_FIN_CADENA);
			try {
				comprobante = entrada.readLine().trim();
			} catch (IOException e) { comprobante = "";	}
		}while(!comprobante.equals(Constantes.CODIGO_RECIBIDO_CADENA));
	}
*/
