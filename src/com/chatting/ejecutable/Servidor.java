package com.chatting.ejecutable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;

import com.chatting.controlador.ControladorServidor;
import com.chatting.modelo.Constantes;
import com.chatting.modelo.ServerThread;
import com.chatting.vista.VistaServidor;

public class Servidor {
	
	private static JFrame ventana;
	private static VistaServidor vista;
	private static ControladorServidor controlador;
	public static ServerSocket servidor;
	public static int clientesConectados;
	private static HashMap<String, ServerThread> mapaClientes;

	/* ======================== Principal ========================== */
	
	public static void main(String[] args) {
		
		configurarVentana();
        lanzarVentana();
        
        try {
		    iniciarServidor();
		    
		    do {
	    		if(clientesConectados < Constantes.MAX_CONEXIONES)
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
        mapaClientes = new HashMap<String, ServerThread>();
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
		servidor = new ServerSocket(Constantes.PUERTO_SERVIDOR);
		controlador.setServidor(servidor);
		vista.addText("<SERVER> Servidor iniciado en "+servidor.getLocalSocketAddress());
    }
    
    private static void clienteHandler(){
    	try {
	    	Socket cliente = servidor.accept();
			ServerThread thread = new ServerThread(vista, cliente);
			thread.start();
    	}catch(IOException e) { /* Cuando no hay clientes que conectar */ }
    }
    
    public static void meterCliente(ServerThread thread) {
    	mapaClientes.put(thread.getNombre(), thread);
    	clientesConectados++;
    	actualizarConectados();
    }
    
    public static void sacarCliente(String nombre) {
    	mapaClientes.remove(nombre);
    	clientesConectados--;
    	actualizarConectados();
    }
    
    public static void imprimirEnTodos(String msg) {
    	vista.addText(msg);

    	Iterator it = mapaClientes.entrySet().iterator();
        while (it.hasNext() && !mapaClientes.entrySet().isEmpty()) {
            Map.Entry hilo = (Map.Entry)it.next();
            ((ServerThread)hilo.getValue()).enviarTCP(msg);
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    
    public static String obtenerListadoClientes() {
    	return mapaClientes.keySet().toString();
    }
    
    public static void actualizarConectados() {
    	vista.setClientesConectados(clientesConectados);
    	imprimirEnTodos(Constantes.CODIGO_ACTUALIZAR_CONECTADOS);
    	imprimirEnTodos(String.valueOf(clientesConectados));
    	imprimirEnTodos(String.valueOf(Constantes.MAX_CONEXIONES));
    }
}
