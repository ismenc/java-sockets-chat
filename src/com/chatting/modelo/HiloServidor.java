package com.chatting.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.chatting.Constantes;
import com.chatting.ejecutable.Servidor;
import com.chatting.vista.VistaServidor;

/**
 * Hilo del servidor que tratará al cliente.
 * @author Ismael Núñez
 *
 */
public class HiloServidor extends Thread {

	private Socket cliente;
	private VistaServidor vista;
	
	private BufferedReader entrada;
	private PrintWriter salida;	
	
	private String nombre;
	
	/* ======================== Constructor y ejecución ========================== */
	
	public HiloServidor(VistaServidor vista, Socket cliente) throws IOException {
		this.vista = vista;
		this.cliente = cliente;
		this.cliente.setSoTimeout(5000);
		nombre = "";
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream(), true);
	}
	
	public void run() {
		String cadena;
		inicializacionCliente();
		try {
			do {
				cadena = recibirTCP();
				messageHandler(cadena.trim());
			}while(!cadena.trim().equals(Constantes.CODIGO_SALIDA));
			
			entrada.close();
			salida.close();
			cliente.close();
		} catch(SocketTimeoutException e) { 
			Servidor.imprimirTodos("<SERVER> "+nombre+" se ha caído (connection timeout).");
			Servidor.sacarCliente(nombre);
		} catch(IOException e) { Servidor.imprimirTodos("<SERVER> "+nombre+" desconectado dolorosamente."); }
		vista.setClientesConectados(Servidor.getClientes().getClientesConectados());
	}
	
	/* ======================== Métodos ========================== */
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void cerrarConexion() {
		enviarTCP(Constantes.CODIGO_SALIDA);
	}
	
	/**
	 * Aquí tratamos los mensajes que le lleguen al server.
	 * @param mensaje
	 */
	private void messageHandler(String mensaje) {
		switch(mensaje.trim()) {
			case Constantes.CODIGO_NICK:
				
				cambioNick();
				
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
	 * Lo que hacemos cuando un cliente envía el código de cambio de nick.
	 */
	private void cambioNick() {
		String nombreAnterior = nombre;
		Servidor.sacarCliente(nombreAnterior);
		nombre = nombreNoRepetido(recibirTCP());
		Servidor.meterCliente(this);
		Servidor.imprimirTodos("<SERVER> "+ nombreAnterior + " ha cambiado su nombre por "+ nombre +".");
	}

	/**
	 * Aquí inicializamos el cliente (darle nombre y meterlo en listaClientes)
	 */
	private void inicializacionCliente() {
    	nombre = nombreNoRepetido(recibirTCP());
		
		Servidor.meterCliente(this);
		Servidor.getClientes().actualizarConectados();
		Servidor.imprimirTodos("<SERVER> "+ nombre + " se ha unido al chat.");
	}
	
	/**
	 * Modifica el nombre que recibe para que no esté repetido en la lista de clientes.
	 * @param nombreViejo
	 * @return
	 */
	private String nombreNoRepetido(String nombreViejo) {
		// Si ya existe un cliente que se llame así, lo renombramos
    	String nuevoNombre = nombreViejo; int i = 1;
    	while(Servidor.getClientes().yaEstaDentro(nuevoNombre)) { 
    		nuevoNombre = nombreViejo.concat(Integer.toString(i));
    		i++; 
    	}
    	return nuevoNombre;
	}
	
	/**
	 * Recibe un dato.
	 * @return
	 */
	public String recibirTCP() {
		String cadenaRecibida = null;
		do {
			try {
				cadenaRecibida = entrada.readLine();
			} catch (IOException e) { cadenaRecibida = null; }
		} while(cadenaRecibida == null);
			
		return cadenaRecibida;
	}
	
	/**
	 * Envía un dato.
	 * @param cadena
	 */
	public void enviarTCP(String cadena) {
			salida.println(cadena );
	}
	
}
