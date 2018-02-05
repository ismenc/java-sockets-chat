package com.chatting.ejecutable;

import javax.swing.JFrame;

import com.chatting.vista.VistaServidor;

public class Servidor {
	
	public static final int MAX_CONEXIONES = 5;
	
	private static JFrame ventana;
	private static VistaServidor vista;

	/* ======================== Principal ========================== */
	
	public static void main(String[] args) {
		
        /* --------------- Inicialización --------------- */
		configurarVentana();
        lanzarVentana();
        
    }
    
    /* ======================== Métodos ========================== */
    
	private static void configurarVentana() {
		/* ************* Inicializaciones ************* */
        ventana = new JFrame("Servidor de chat");
        vista = new VistaServidor();
        // Iniciar controlador o Menu menu = new Menu();
        // Controlador controlador = new Controlador(ventanaPrincipal, menu, panelPrincipal);
        
        /* ************* Configuraciones ************* */
        //ventanaPrincipal.setJMenuBar(menu);
        ventana.setContentPane(vista); // Si ponemos menú sería con add
        //menu.setControlador(controlador);
        //panelPrincipal.setControlador(controlador);
	}
	
    private static void lanzarVentana(){
        ventana.pack();
        ventana.setVisible(true);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.addText("<SERVER> Ventana iniciada.");
    }
}
