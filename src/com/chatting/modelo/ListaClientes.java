package com.chatting.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	
	public String getListaClientes() {
		return mapaClientes.keySet().toString();
	}
	
	public void actualizarConectados() {
    	emitirATodos(Constantes.CODIGO_ACTUALIZAR_CONECTADOS);
    	emitirATodos(getClientesConectados() + "/" + Constantes.MAX_CONEXIONES);
    }
	
	public void emitirATodos(String msg) {

		Set<Map.Entry<String, ServerThread>> entrySet = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : entrySet) {
		   ((ServerThread) entry.getValue()).enviarTCP(msg);;
		}


	}
}
