package com.chatting.ejecutable;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chatting.controlador.ControladorCliente;
import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaCliente;

public class Cliente {
	
	private static JFrame ventana;
	private static VistaCliente vista;
	private static ControladorCliente controlador;
	private static Socket cliente;
	private static UtilidadesCliente utilidades;

	public static void main(String[] args) {
		
		configurarVentana();
		lanzarVentana();
		
		try {
			iniciarConexion();
		}catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ventana, "Debes introducir un número de puerto válido.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(ventana, "Servidor no alcanzado.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
		}
		
		while(true) {
			
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
    
    private static void iniciarConexion() throws UnknownHostException {
    	String host = JOptionPane.showInputDialog(ventana, "Introduce la ip del host", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String puerto = JOptionPane.showInputDialog(ventana, "Introduce el puerto", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String nickname = JOptionPane.showInputDialog(ventana, "Introduce tu nickname", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	
    	try {
			cliente = new Socket(host, Integer.parseInt(puerto));
			ventana.setVisible(true);
			vista.setEnabled(true);
			utilidades = new UtilidadesCliente(cliente);
			controlador.setCliente(utilidades);
			utilidades.enviar(nickname);
			vista.setClientesConectados(Integer.parseInt(utilidades.enviarComando("/clientesConectados")));
			vista.setMaxClientes(Integer.parseInt(utilidades.enviarComando("/maxClientesConectados")));
		} catch (IOException e) {
			// Debemos finalizar el programa
		}
    }
}
