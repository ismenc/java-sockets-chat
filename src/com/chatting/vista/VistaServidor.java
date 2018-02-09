package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import com.chatting.Constantes;

/**
 * Ventana del servidor.
 * @author Ismael Núñez
 *
 */
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
		JPanel panelNorte = new JPanel(new GridLayout(1, 2));
		
		/* --------------------- Inicializaciones --------------------- */
		clientesConectados = 0;
		labelConexiones = new JLabel("Clientes conectados: 0/"+Constantes.MAX_CONEXIONES);
		labelPuerto = new JLabel("Puerto: " + Constantes.PUERTO_SERVIDOR);
		botonSalir = new JButton("Apagar servidor");
		texto = new JTextArea();
		texto.setEditable(false);
		JScrollPane scroll = new JScrollPane(texto);
		
		/* --------------------- Asignaciones --------------------- */
		panelNorte.add(labelConexiones);
		panelNorte.add(labelPuerto);
		this.add(panelNorte, BorderLayout.NORTH);
		this.add(botonSalir, BorderLayout.SOUTH);
		add(scroll, BorderLayout.CENTER);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		DefaultCaret caret = (DefaultCaret)texto.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		setPreferredSize(new Dimension(480, 360));
	}
	
	/* ============================| Métodos |============================ */
	
	public void setControlador(ActionListener l) {
		botonSalir.setActionCommand("apagar");
		botonSalir.addActionListener(l);
	}
	
	public void setClientesConectados(int clientesConectados) {
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Constantes.MAX_CONEXIONES);
	}
	
	public void addText(String linea) {
		texto.append(linea+ "\n");
	}
	
	public void apagar() {
		botonSalir.setEnabled(false);
		texto.setEnabled(false);
		labelConexiones.setText("Servidor apagado.");
		labelPuerto.setText("Puerto: -");
	}
	
	/* ============================| Métodos obsoletos |============================ */
	
	@Deprecated
	public void sumarCliente() {
		clientesConectados++;
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Constantes.MAX_CONEXIONES);
	}
	
	@Deprecated
	public void restarCliente() {
		clientesConectados--;
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Constantes.MAX_CONEXIONES);
	}
}
