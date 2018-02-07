package com.chatting.ejecutable;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import com.chatting.controlador.ControladorServidor;
import com.chatting.modelo.Constantes;
import com.chatting.modelo.ListaClientes;
import com.chatting.modelo.ServerThread;
import com.chatting.vista.VistaServidor;

/**
 * Clase principal para el servidor del chat.
 * @author Ismael Núñez
 *
 */
public class Servidor {
	
	private static JFrame ventana;
	private static VistaServidor vista;
	private static ControladorServidor controlador;
	public static ServerSocket servidor;
	private static ListaClientes clientes;
	
	/* ======================== Principal ========================== */
	
	public static void main(String[] args) {
		
		configurarVentana();
        lanzarVentana();
        
        try {
		    iniciarServidor();
		    
		    do {
	    		if(clientes.getClientesConectados() < Constantes.MAX_CONEXIONES)
	    			handleClient();
		    }while(!servidor.isClosed());
        } catch (BindException e) {
			vista.addText("Ya tienes una instancia del server abierta, MELÓN");
		} catch(IOException e) {
        	vista.addText("<SERVER FATAL ERROR> No fue posible iniciar el servidor (already running bruh?).");
        	e.printStackTrace();
        }
        System.out.println("olo"+clientes.getListaClientes()+"olo");
        while(true) {}
    }
    
    /* ======================== Métodos ========================== */
    
	public static void imprimirConsola(String msg) {
		vista.addText(msg);
	}
	
	public static void imprimirTodos(String msg) {
		imprimirConsola(msg);
		clientes.emitirATodos(msg);
	}
	
	public static ListaClientes getClientes() {
		return clientes;
	}
		
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
    }
    
    private static void iniciarServidor() throws IOException {
		servidor = new ServerSocket(Constantes.PUERTO_SERVIDOR);
		servidor.setSoTimeout(500);
		clientes = new ListaClientes();
		controlador.setServidor(servidor);
		vista.addText("<SERVER> Servidor iniciado en "+servidor.getLocalSocketAddress());
    }
    
    private static void handleClient(){
    	try {
	    	Socket cliente = servidor.accept();
			ServerThread thread = new ServerThread(vista, cliente);
			thread.start();
    	}catch(IOException e) { /* Cuando no hay clientes que conectar */ }
    }
    
    public static void meterCliente(ServerThread thread) {
    	clientes.add(thread.getNombre(), thread);
    	clientes.actualizarConectados();
    	vista.setClientesConectados(clientes.getClientesConectados());
    }
    
    public static void sacarCliente(String nombre) {
    	clientes.remove(nombre);
    	clientes.actualizarConectados();
    	vista.setClientesConectados(clientes.getClientesConectados());
    }
    
    
}
