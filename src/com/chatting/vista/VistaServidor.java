package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.chatting.ejecutable.Servidor;

public class VistaServidor extends JPanel {

	private static final long serialVersionUID = 1L;
	
	@Deprecated
	private int clientesConectados;
	
	private JLabel labelConexiones, labelPuerto;
	private JButton botonSalir;
	private JTextArea texto;
	
	/* ============================| Constructores |============================ */
	
	public VistaServidor() {
		setLayout(new BorderLayout());
		JPanel panelNorte = new JPanel();
		GridLayout capa = new GridLayout(1, 2);
		
		/* --------------------- Inicializaciones --------------------- */
		clientesConectados = 0;
		labelConexiones = new JLabel("Clientes conectados: "+clientesConectados+"/"+Servidor.MAX_CONEXIONES);
		labelPuerto = new JLabel("Puerto: " + Servidor.PUERTO_SERVIDOR);
		botonSalir = new JButton("Apagar servidor");
		texto = new JTextArea();
		texto.setEditable(false);
		
		/* --------------------- Asignaciones --------------------- */
		panelNorte.setLayout(capa);
		panelNorte.add(labelConexiones);
		panelNorte.add(labelPuerto);
		this.add(panelNorte, BorderLayout.NORTH);
		this.add(botonSalir, BorderLayout.SOUTH);
		this.add(texto, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(480, 360));
	}
	
	/* ============================| Métodos |============================ */
	
	public void setControlador(ActionListener l) {
		botonSalir.setActionCommand("apagar");
		botonSalir.addActionListener(l);
	}
	
	public void setClientesConectados(int clientesConectados) {
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Servidor.MAX_CONEXIONES);
	}
	
	public void addText(String linea) {
		texto.append(linea+ "\n");
	}
	
	/* ============================| Métodos obsoletos |============================ */
	
	@Deprecated
	public void sumarCliente() {
		clientesConectados++;
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Servidor.MAX_CONEXIONES);
	}
	
	@Deprecated
	public void restarCliente() {
		clientesConectados--;
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Servidor.MAX_CONEXIONES);
	}
}
