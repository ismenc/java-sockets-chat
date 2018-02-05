package SocketsTCP.ConObjects;

/**
 * @author Adrián
 * NOTA: si lo hiciesemos con UDP usar:
 *     ByteArrayOutputStream
 *     ByteArrayInputStream
 */ 

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class S_TCP_objects {
    public static void main(String args [] ) throws IOException, ClassNotFoundException {
        int puertoLocal = 6000; //puerto origen
        Persona pENV = new Persona("Adry",30);//objeto persona
        
        //Creo socket servidor
        ServerSocket servidor = new ServerSocket(puertoLocal);
        
        //Espero a un cliente
        System.out.println("PROGRAMA SERVIDOR ESPERANDO A UN CLIENTE....");
        Socket cliente = servidor.accept();
        
        //ENVÍO DE OBJETO
        //Preparo flujo de salida para objetos
        ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
        //Envío el objeto       
        outObjeto.writeObject(pENV);//enviando objeto
        System.out.println("\tENVÍO: "+pENV.getNombre()+"  "+pENV.getEdad());
        
        //RECIBO DE OBJETO
        //Preparo flujo de entrada para objetos
        ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
        Persona pREC = (Persona) inObjeto.readObject();
        System.out.println("\tRECIBO: "+pREC.getNombre()+"  "+pREC.getEdad());
        
        //CERRAMOS FLUJOS Y SOCKETS
        outObjeto.close();
        inObjeto.close();
        cliente.close();
        servidor.close();
    }//fin main   
}//fin clase
