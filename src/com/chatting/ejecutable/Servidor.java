package com.chatting.ejecutable;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.chatting.vista.VistaServidor;

public class Servidor {
	
	public static final int MAX_CONEXIONES = 5;

	public static void main(String[] args) {
		
		/* ************* Inicializaciones ************* */
        JFrame ventana = new JFrame("Servidor de chat");
        VistaServidor vista = new VistaServidor();
        // Iniciar controlador o Menu menu = new Menu();
        // Controlador controlador = new Controlador(ventanaPrincipal, menu, panelPrincipal);
        
        /* ************* Configuraciones ************* */
        //ventanaPrincipal.setJMenuBar(menu);
        ventana.setContentPane(vista); // Si ponemos menú sería con add
        //menu.setControlador(controlador);
        //panelPrincipal.setControlador(controlador);
        
        lanzarVentana(ventana);
        vista.addText("<SERVER> Ventana iniciada.");
    }
    
    /* ------------------- Metodos ------------------- */
    
    private static void lanzarVentana(JFrame ventana){
        // Inicialización de ventana
        ventana.pack();
        ventana.setVisible(true);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
