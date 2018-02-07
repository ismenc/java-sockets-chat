package com.chatting.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Clase que almacena los clientes que están conectados.
 * @author Ismael Núñez
 *
 */
public class ListaClientes {

	private HashMap<String, ServerThread> mapaClientes;
	
	public ListaClientes(){
		mapaClientes = new HashMap<String, ServerThread>();
	}
	
	
	
	public int getClientesConectados() {
		return mapaClientes.size();
	}
	
	public void add(String nombre, ServerThread cliente) {
		mapaClientes.put(nombre, cliente);
	}
	
	public void remove(String nombre) {
		mapaClientes.remove(nombre);
	}
	
	// Envía una cadnea rara, el .toString también
	public String getListaClientes() {
		StringBuilder clientes = new StringBuilder(250);
		
		Set<String> claves = mapaClientes.keySet();
		for (String clave : claves) {
		   clientes.append(clave);
		}
		return clientes.toString().trim()+"\n";
	}
	
	public void actualizarConectados() {
    	emitirATodos(Constantes.CODIGO_ACTUALIZAR_CONECTADOS);
    	emitirATodos(getClientesConectados() + "/" + Constantes.MAX_CONEXIONES);
    }
	
	public void emitirATodos(String msg) {
		Set<Map.Entry<String, ServerThread>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
		   ((ServerThread) entry.getValue()).enviarTCP(msg);;
		}
	}
}
