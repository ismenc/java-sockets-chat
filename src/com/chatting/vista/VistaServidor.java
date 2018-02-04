package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VistaServidor extends JPanel {

	private int clientesConectados;
	private JLabel labelConexiones;
	private JButton botonSalir;
	private JTextArea texto;
	
	/* ============================| Constructores |============================ */
	
	public VistaServidor() {
		setLayout(new BorderLayout());
		
		/* --------------------- Inicializaciones --------------------- */
		clientesConectados = 0;
		labelConexiones = new JLabel("Clientes conectados: "+clientesConectados);
		botonSalir = new JButton("Apagar servidor");
		texto = new JTextArea();
		texto.setEditable(false);
		
		/* --------------------- Asignaciones --------------------- */
		this.add(labelConexiones, BorderLayout.NORTH);
		this.add(botonSalir, BorderLayout.SOUTH);
		this.add(texto, BorderLayout.CENTER);
		setPreferredSize(new Dimension(480, 360));
	}
	
	/* ============================| Métodos |============================ */
	
	public void setControlador(ActionListener l) {
		botonSalir.setActionCommand("apagar");
		botonSalir.addActionListener(l);
	}
	
	public void sumarCliente() {
		clientesConectados++;
		labelConexiones.setText("Clientes conectados: " + clientesConectados);
	}
	
	public void restarCliente() {
		clientesConectados--;
		labelConexiones.setText("Clientes conectados: " + clientesConectados);
	}
	
	public void addText(String linea) {
		texto.append(linea);
	}
}
