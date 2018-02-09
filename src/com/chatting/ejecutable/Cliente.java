package com.chatting.ejecutable;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chatting.Constantes;
import com.chatting.controlador.ControladorCliente;
import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaCliente;

/**
 *  Clase principal del usuario cliente de chat.
 * @author Ismael Núñez
 *
 */
public class Cliente {
	
	private static JFrame ventana;
	private static VistaCliente vista;
	private static ControladorCliente controlador;
	private static Socket cliente;
	private static UtilidadesCliente utilidades;
	
	/* ======================== Main ========================== */

	public static void main(String[] args) {
		
		configurarVentana();
		
		try {
			
			iniciarCliente();
			
			while(!cliente.isClosed()) {
				handleMessage();
			}
			
			while(true) {}
		} catch (SocketException e) {
			vista.setEnabled(false);
			JOptionPane.showMessageDialog(ventana, "Servidor no alcanzado. Apagado o fuera de covertura.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			// El mensaje que saldrá es servidor lleno
			JOptionPane.showMessageDialog(ventana, e.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
		} 
		
	}
	
	/* ======================== Métodos ========================== */

	private static void configurarVentana() {
		/* --------------- Inicializaciones --------------- */
        ventana = new JFrame("Cliente de chat");
        vista = new VistaCliente(ventana);
        controlador = new ControladorCliente(vista);
        
        /* --------------- Configuraciones --------------- */
        ventana.setContentPane(vista);
        vista.setControlador(controlador);
        ventana.pack();
        ventana.setResizable(false);
	}
    
    private static void iniciarCliente() throws NumberFormatException, IOException {
    	String host = JOptionPane.showInputDialog(ventana, "Introduce la ip del host", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String puerto = JOptionPane.showInputDialog(ventana, "Introduce el puerto", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String nickname = JOptionPane.showInputDialog(ventana, "Introduce tu nickname", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	//String host="localhost"; String puerto = String.valueOf(Constantes.PUERTO_SERVIDOR); String nickname = "ISMAEL"; 
		
    	try {
    		// Conectamos
    		cliente = new Socket(host, Integer.parseInt(puerto));
    		utilidades = new UtilidadesCliente(cliente);
    		
    		// Sino está lleno entramos, si está lleno lanzaremos el error.
    		if(utilidades.recibirTCP().trim().equals("aceptado")) {
    			iniciarChat(nickname);
    		}else {
    			utilidades = null;
    			throw new IOException("Servidor lleno");
    		}
    	}catch(NumberFormatException e) {
    		JOptionPane.showMessageDialog(ventana, "Debes introducir un número de puerto válido.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    /**
     * Activa la ventana hacemos asociaciones correspondientes al conectar por primera vez.
     * @param nick
     * @throws IOException
     */
    private static void iniciarChat(String nick) throws IOException {
    	ventana.setVisible(true);
		vista.setEnabled(true);
		controlador.setCliente(utilidades);
		utilidades.enviarTCP(nick);
    }
    
    /**
     * Interpretamos el mensaje leido en el cliente.
     */
    private static void handleMessage() {
    	String msg;
		try {
			msg = utilidades.recibirTCP();
		
			switch(msg.trim()){
				// recibimos código de desconectar.
				case Constantes.CODIGO_SALIDA:
					
					controlador.salir();
					vista.addText("<CLIENT> El servidor se ha apagado");
					
				break;
				// Recibimos actualizar numero clientes
				case Constantes.CODIGO_ACTUALIZAR_CONECTADOS:
					
					vista.setClientes(utilidades.recibirTCP());
					
				break;
				default: // Recibimos un mensaje normal y corriente
					
						vista.addText(msg);
						
				break;
			}
	    	
		} catch (IOException e) {
			controlador.salir();
			vista.addText("<CLIENT> Servidor desconectado.");
		}
    }
}
