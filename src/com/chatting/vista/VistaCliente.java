package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VistaCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelClientes;
	private JTextArea chat;
	private JTextField campo;
	private JButton botonEnviar, botonSalir, botonLimpiar, botonListado;
	
	/* ============================| Constructores |============================ */
	
	public VistaCliente(JFrame ventana) {
		setLayout(new BorderLayout());
		JPanel panelNorte = new JPanel(new FlowLayout());
		JPanel panelSur = new JPanel(new GridLayout(1,3));
		
		/* --------------------- Inicializaciones --------------------- */
		labelClientes = new JLabel("Clientes en el chat: 0/0");
		chat = new JTextArea();
		campo = new JTextField();
		botonListado = new JButton("Listado de clientes");
		botonSalir = new JButton("Salir");
		botonEnviar = new JButton("Enviar");
		botonLimpiar = new JButton("Limpiar chat");
		
		/* --------------------- Asignaciones --------------------- */
		panelNorte.add(labelClientes);
		panelNorte.add(botonListado);
		panelNorte.add(botonSalir);
		panelSur.add(botonLimpiar);
		panelSur.add(campo);
		panelSur.add(botonEnviar);
		
		add(panelNorte, BorderLayout.NORTH);
		add(panelSur, BorderLayout.SOUTH);
		add(chat, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(480, 360));
		
		chat.setEditable(false);
		this.setEnabled(false);
	}
	
	/* ============================| Métodos |============================ */
	
	public String getTextoCampo() {
		return campo.getText().toString();
	}
	
	public void vaciarTextoCampo() {
		campo.setText("");
	}
	
	public void setControlador(ActionListener l) {
		botonEnviar.setActionCommand("enviar");
		botonSalir.setActionCommand("salir");
		botonLimpiar.setActionCommand("limpiar");
		botonListado.setActionCommand("listado");
		
		botonEnviar.addActionListener(l);
		botonSalir.addActionListener(l);
		botonLimpiar.addActionListener(l);
		botonListado.addActionListener(l);
	}
	
	public void setClientes(String clientes) {
		labelClientes.setText("Clientes en el chat: "+ clientes);
	}
	
	public void addText(String linea) {
		chat.append(linea+"\n");
	}
	
	public void limpiarChat() {
		chat.setText("");
	}
}
