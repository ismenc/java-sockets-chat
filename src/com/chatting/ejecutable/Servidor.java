package com.chatting.ejecutable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import com.chatting.controlador.ControladorServidor;
import com.chatting.modelo.ServerThread;
import com.chatting.vista.VistaServidor;

public class Servidor {
	
	public static final int MAX_CONEXIONES = 5;
	public static final int PUERTO_SERVIDOR = 21;
	public static final String CODIGO_SALIDA = "*-*";
	
	private static JFrame ventana;
	private static VistaServidor vista;
	private static ControladorServidor controlador;
	public static ServerSocket servidor;
	public static int clientesConectados;

	/* ======================== Principal ========================== */
	
	public static void main(String[] args) {
		
		configurarVentana();
        lanzarVentana();
        
        try {
		    iniciarServidor();
		    
		    do {
	    		if(clientesConectados < MAX_CONEXIONES)
	    			clienteHandler();
		    }while(!servidor.isClosed());
        }catch(IOException e) {
        	vista.addText("<SERVER FATAL ERROR> No fue posible iniciar el servidor.");
        	e.printStackTrace();
        }
        
        while(true) {}
    }
    
    /* ======================== Métodos ========================== */
    
	private static void configurarVentana() {
		/* --------------- Inicializaciones --------------- */
        ventana = new JFrame("Servidor de chat");
        vista = new VistaServidor();
        controlador = new ControladorServidor(vista);
        
        /* --------------- Configuraciones --------------- */
        ventana.setContentPane(vista);
        vista.setControlador(controlador);
	}
	
    private static void lanzarVentana(){
        ventana.pack();
        ventana.setVisible(true);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.addText("<SERVER> Ventana iniciada.");
    }
    
    private static void iniciarServidor() throws IOException {
		clientesConectados = 0;
		servidor = new ServerSocket();
		controlador.setServidor(servidor);
		vista.addText("<SERVER> Servidor iniciado en "+servidor.getInetAddress()+":"+servidor.getLocalPort());
    }
    
    private static void clienteHandler(){
    	try {
	    	Socket cliente = servidor.accept();
			ServerThread thread = new ServerThread(vista, cliente);
			thread.start();
			clientesConectados++;
			vista.setClientesConectados(clientesConectados);
    	}catch(IOException e) { /* Cuando no hay clientes que conectar */ }
    }
}
