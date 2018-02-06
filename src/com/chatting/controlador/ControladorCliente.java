package com.chatting.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;

import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaCliente;

public class ControladorCliente implements ActionListener {

	private UtilidadesCliente cliente;
	private VistaCliente vista;
	
	public ControladorCliente(VistaCliente vista) {
		this.vista = vista;
	}

	public void setCliente(UtilidadesCliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "salir":
			String respuesta;
			try {
				respuesta = cliente.enviarYRecibir("/salir");
				vista.addText(respuesta);
				cliente.cerrarConexion();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		break;
		case "enviar":
			try {
				vista.addText(cliente.enviarYRecibir(vista.getTextoCampo()));
				vista.vaciarTextoCampo();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		break;
		case "listado":
			String clientes;
			try {
				clientes = cliente.enviarYRecibir("/listarClientes");
				vista.mostrarClientes(clientes);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		break;
		case "limpiar":
			vista.limpiarChat();
		break;
		default:
		break;
		}
	}
	


}
