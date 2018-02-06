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
			cliente.enviarTCP("/salir");
			cliente.cerrarConexion();
		break;
		case "enviar":
			cliente.enviarTCP(vista.getTextoCampo());
			vista.vaciarTextoCampo();
		break;
		case "listado":
			cliente.enviarTCP("/listarClientes");
			String clientes = cliente.recibirTCP();
			vista.mostrarClientes(clientes);
		break;
		case "limpiar":
			vista.limpiarChat();
		break;
		default:
		break;
		}
	}
	


}
