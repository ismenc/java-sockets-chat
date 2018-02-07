package com.chatting.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.chatting.modelo.Constantes;
import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaCliente;

/**
 * Clase que tratará los eventos sobre los botones en la vista.
 * @author Ismael Núñez
 *
 */
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
				vista.addText("<CLIENT> Has abandonado la sala de chat.");
				vista.setClientes("Unknown");
				cliente.enviarTCP(Constantes.CODIGO_SALIDA);
				cliente.cerrarConexion();
				vista.setEnabled(false);
			break;
			case "enviar":
				cliente.enviarTCP(vista.getTextoCampo());
				vista.vaciarTextoCampo();
			break;
			case "listado":
				cliente.enviarTCP(Constantes.CODIGO_LISTAR);
				vista.addText(cliente.recibirTCP());
			case "limpiar":
				vista.limpiarChat();
			break;
			default:
			break;
		}
	}
	


}
