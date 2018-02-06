package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VistaCliente extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFrame ventana;
	private int maxClientes;
	
	private JLabel labelClientes;
	private JTextArea chat;
	private JTextField campo;
	private JButton botonEnviar, botonSalir, botonLimpiar, botonListado;
	
	/* ============================| Constructores |============================ */
	
	public VistaCliente(JFrame ventana) {
		this.ventana = ventana;
		JPanel panelNorte = new JPanel(new FlowLayout());
		JPanel panelSur = new JPanel(new FlowLayout());
		
		/* --------------------- Inicializaciones --------------------- */
		labelClientes = new JLabel("Clientes en el chat: 0");
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
		
		setPreferredSize(new Dimension(480, 360));
		campo.setMinimumSize(new Dimension(380, 15));
		
		this.setEnabled(false);
	}
	
	/* ============================| Métodos |============================ */
	
	public String getTextoCampo() {
		return campo.getText().toString();
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
	
	public void setClientesConectados(int clientes) {
		// FIXME poner máximo clientes al iniciar conexion
		labelClientes.setText("Clientes en el chat: "+ clientes+"/"+maxClientes);
	}
	
	public void setMaxClientes(int maxClientes) {
		this.maxClientes = maxClientes;
	}
	
	public void addText(String linea) {
		chat.append(linea+"\n");
	}
	
	public void limpiarChat() {
		chat.setText("");
	}
	
	public void mostrarClientes(String clientes) {
		JDialog dialogo = new JDialog(ventana, "Listado de clientes", true);
		JTextArea area = new JTextArea();
		dialogo.setPreferredSize(new Dimension(200, 150));
		area.setEditable(false);
		dialogo.add(area);
		area.setText(clientes);
	}
}
