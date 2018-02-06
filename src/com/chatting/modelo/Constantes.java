package com.chatting.modelo;

public abstract class Constantes {

	public static final int MAX_CONEXIONES = 5;
	public static final int PUERTO_SERVIDOR = 1004;
	
	public static final String CODIGO_NICK = "/nickname";
	public static final String CODIGO_SALIDA = "/salir";
	public static final String CODIGO_LISTAR = "/listar";
	public static final String CODIGO_CONECTADOS = "/clientesConectados";
	public static final String CODIGO_MAX_CLIENTES = "/maxClientes";
	
	public static final String CODIGO_FIN_CADENA = "*fin*";
	public static final String CODIGO_RECIBIDO_CADENA = "*recibido*";
}
