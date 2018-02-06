package com.chatting.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			String respuesta = cliente.enviarComando("/salir");
			vista.addText(respuesta);
			cliente.cerrarConexion();
		break;
		case "enviar":
			cliente.enviar(vista.getTextoCampo());
		break;
		case "listado":
			String clientes = cliente.enviarComando("/listarClientes");
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
