package com.chatting.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.chatting.Constantes;

import java.util.Set;

/**
 * Clase que almacena los clientes que están conectados.
 * @author Ismael Núñez
 *
 */
public class ListaClientes {

	private static HashMap<String, ServerThread> mapaClientes;
	
	public ListaClientes(){
		mapaClientes = new HashMap<String, ServerThread>();
	}
	
	/* ======================== Métodos Básicos ========================== */
	
	public int getClientesConectados() {
		return mapaClientes.size();
	}
	
	public void add(String nombre, ServerThread cliente) {
		mapaClientes.put(nombre, cliente);
	}
	
	public void remove(String nombre) {
		mapaClientes.remove(nombre);
	}
	
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
		
		Set<String> claves = mapaClientes.keySet();
		for (String clave : claves) {
		   clientes.append(clave + ", ");
		}
		
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
		Set<Map.Entry<String, ServerThread>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
			((ServerThread) entry.getValue()).cerrarConexion();
		}
	}
	
	/**
	 * Envía el mensaje a todos los clientes
	 * @param msg
	 */
	public void emitirATodos(String msg) {
		Set<Map.Entry<String, ServerThread>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
		   ((ServerThread) entry.getValue()).enviarTCP(msg);
		}
	}
}
