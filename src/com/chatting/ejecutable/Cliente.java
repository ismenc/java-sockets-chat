package com.chatting.ejecutable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
				utilidades.handleMessage();
			}
			
			while(true) {}
		} catch (SocketTimeoutException e) {
			vista.setEnabled(false);
			JOptionPane.showMessageDialog(ventana, "Conexión perdida (connection timeout)", "Error de conexión", JOptionPane.ERROR_MESSAGE);
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
    
	/**
	 * Lanza la ventana e inicia la conexión con el servidor.
	 * @throws NumberFormatException
	 * @throws IOException
	 */
    private static void iniciarCliente() throws NumberFormatException, IOException {
    	String host = JOptionPane.showInputDialog(ventana, "Introduce la ip del host (nada = localhost)", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String puerto = JOptionPane.showInputDialog(ventana, "Introduce el puerto (nada = 42455)", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String nickname = JOptionPane.showInputDialog(ventana, "Introduce tu nickname", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	
    	if(puerto.equals(""))
    		puerto = "42455";
    	if(host.equals(""))
    		host = "localhost";
		
    	try {
    		if(nickname.equals(""))
    			throw new IOException("Nickname no válido.");
    		// Conectamos estableciendo un TIMEOUT
    		cliente = new Socket();
    		cliente.connect(new InetSocketAddress(host, Integer.parseInt(puerto)), 5000);
    		utilidades = new UtilidadesCliente(cliente, vista, controlador);
    		
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
    
    
}
