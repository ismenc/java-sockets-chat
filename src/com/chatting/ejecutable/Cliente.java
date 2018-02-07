package com.chatting.ejecutable;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chatting.controlador.ControladorCliente;
import com.chatting.modelo.Constantes;
import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaCliente;

public class Cliente {
	
	private static JFrame ventana;
	private static VistaCliente vista;
	private static ControladorCliente controlador;
	private static Socket cliente;
	private static UtilidadesCliente utilidades;

	public static void main(String[] args) {
		
		String cadena;
		configurarVentana();
		lanzarVentana();
		
		try {
			iniciarConexion();
			
			while(cliente.isConnected()) {
				
				cadena = utilidades.recibirTCP();
				if(cadena.trim().equals(Constantes.CODIGO_ACTUALIZAR_CONECTADOS)) {
					vista.setClientes(utilidades.recibirTCP());
				}else
					vista.addText(cadena);
			}
			
		}catch (NumberFormatException e) {
			//JOptionPane.showMessageDialog(ventana, "Debes introducir un número de puerto válido.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventana, "Servidor no alcanzado.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	private static void configurarVentana() {
		/* --------------- Inicializaciones --------------- */
        ventana = new JFrame("Cliente de chat");
        vista = new VistaCliente(ventana);
        controlador = new ControladorCliente(vista);
        
        /* --------------- Configuraciones --------------- */
        ventana.setContentPane(vista);
        vista.setControlador(controlador);
	}
	
    private static void lanzarVentana(){
        ventana.pack();
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.addText("<CLIENT> Ventana iniciada.");
    }
    
    private static void iniciarConexion() throws NumberFormatException, IOException {
    	/*String host = JOptionPane.showInputDialog(ventana, "Introduce la ip del host", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String puerto = JOptionPane.showInputDialog(ventana, "Introduce el puerto", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String nickname = JOptionPane.showInputDialog(ventana, "Introduce tu nickname", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	*/String host="localhost"; String puerto = String.valueOf(Constantes.PUERTO_SERVIDOR); String nickname = "ISMAEL"; 
		
    	cliente = new Socket(host, Integer.parseInt(puerto));
		if(cliente.isConnected()) 
			iniciarChat(nickname);
    }
    
    private static void iniciarChat(String nick) throws IOException {
    	ventana.setVisible(true);
		vista.setEnabled(true);
		utilidades = new UtilidadesCliente(cliente);
		controlador.setCliente(utilidades);
		
		utilidades.enviarTCP(Constantes.CODIGO_INICIAL);
		utilidades.enviarTCP(nick);
    }
}
