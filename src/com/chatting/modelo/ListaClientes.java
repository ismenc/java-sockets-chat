package com.chatting.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.chatting.Constantes;

import java.util.Set;

/**
 * Clase que almacena los clientes que están conectados.
 * He elegido HashMap para almacenar el nick como clave de los clientes.
 * @author Ismael Núñez
 *
 */
public class ListaClientes {

	private static HashMap<String, HiloServidor> mapaClientes;
	
	public ListaClientes(){
		mapaClientes = new HashMap<String, HiloServidor>();
	}
	
	/* ======================== Métodos Básicos ========================== */
	
	public int getClientesConectados() {
		return mapaClientes.size();
	}
	
	public void add(String nombre, HiloServidor cliente) {
		mapaClientes.put(nombre, cliente);
	}
	
	public void remove(String nombre) {
		mapaClientes.remove(nombre);
	}
	
	/**
	 * Devuelve true si un cliente se encuentra en la lista.
	 */
	public boolean yaEstaDentro(String nombre) {
		return mapaClientes.containsKey(nombre);
	}
	
	/* ======================== Métodos Avanzados ========================== */
	
	/**
	 * Devuelve un string con la lista de los nombres de los clientes.
	 * @return
	 */
	public String getListaClientes() {
		StringBuilder clientes = new StringBuilder(250);
		
		// Recorremos las claves (nombres) de los clientes
		Set<String> claves = mapaClientes.keySet();
		for (String clave : claves) {
		   clientes.append(clave + ", ");
		}
		
		// Al final quitamos la coma e imprimimos punto
		clientes.setLength(clientes.length()-2);
		clientes.append(".");
				
		return clientes.toString().trim();
	}
	
	/**
	 * Envía a todos los clientes actualización de número de clientes conectados.
	 */
	public void actualizarConectados() {
    	emitirATodos(Constantes.CODIGO_ACTUALIZAR_CONECTADOS);
    	emitirATodos(getClientesConectados() + "/" + Constantes.MAX_CONEXIONES);
    }
	
	/**
	 * Desconecta a todos los clientes del servidor (los echa).
	 */
	public void desconectarTodos() {
		Set<Map.Entry<String, HiloServidor>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
			((HiloServidor) entry.getValue()).cerrarConexion();
		}
	}
	
	/**
	 * Recorremos todos los clientes enviándoles el mensaje
	 * @param msg
	 */
	public void emitirATodos(String msg) {
		Set<Map.Entry<String, HiloServidor>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
		   ((HiloServidor) entry.getValue()).enviarTCP(msg);
		}
	}
}
