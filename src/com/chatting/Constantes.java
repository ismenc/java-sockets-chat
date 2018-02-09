package com.chatting;

/**
 * Algunas constantes usadas en el proyecto, como códigos de comunicación.
 * @author Ismael
 *
 */
public abstract class Constantes {

	public static final int MAX_CONEXIONES = 5;
	public static final int PUERTO_SERVIDOR = 42455;
	
	public static final String CODIGO_NICK = "/nickname"; // ponemos esto y luego enviamos otra cadena con el nombre y se cambia
	public static final String CODIGO_SALIDA = "/salir";
	public static final String CODIGO_LISTAR = "/listar";
	public static final String CODIGO_ACTUALIZAR_CONECTADOS = "/actualizarConectados";
}
