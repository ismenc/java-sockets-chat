package com.chatting.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.chatting.ejecutable.Servidor;
import com.chatting.vista.VistaServidor;

public class ServerThread extends Thread {

	Socket cliente;
	BufferedReader entrada;
	PrintWriter salida;	
	VistaServidor vista;
	
	public ServerThread(VistaServidor vista, Socket cliente) throws IOException {
		this.vista = vista;
		this.cliente = cliente;
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream());
	}
	
	public void run() {
		String cadena = "";
		String nombre = "";
		try {
			nombre = entrada.readLine();
			salida.println("<SERVER> Bienvenido "+nombre+"!");
			vista.addText("<SERVER> "+ nombre+ " se ha unido al chat.");
		
			while(!cadena.trim().equals(Servidor.CODIGO_SALIDA)) {
				cadena = entrada.readLine();
				
				// Lo que hacemos mientras el cliente no envía el código de salida
				if(cadena.trim().equals(Servidor.CODIGO_SALIDA)) {
					vista.addText(nombre+": "+ cadena.trim());
					salida.println(nombre+": "+ cadena.trim());
				}
				// Cuando recibimos el código de salida
				else {
					vista.addText("<SERVER> "+ nombre+ " ha abandonado el chat.");
					salida.println("<SERVER> Te has desconectado del chat.");
				}
			}
			
			entrada.close();
			salida.close();
			cliente.close();
		}catch(IOException e) { vista.addText("<SERVER> "+nombre+" desconectado involuntariamente."); }
		Servidor.clientesConectados--;
		vista.setClientesConectados(Servidor.clientesConectados);
	}
	
}
