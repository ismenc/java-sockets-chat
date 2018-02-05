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
import java.net.Socket;


public class C_TCP_Objects {
    public static void main(String args [] ) throws IOException, ClassNotFoundException {
        int puertoDestino = 6000; //puerto destino
        String servidor = "localhost";
        
        
        //Creo socket cliente y lo inicio
        System.out.println("PROGRAMA CLIENTE INICIADO....");
        Socket cliente = new Socket(servidor, puertoDestino);
        
        //RECIBO DE OBJETO
        //Preparo flujo de entrada para objetos
        ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
        //Se recibe un objeto
        Persona pREC = (Persona) inObjeto.readObject();
        System.out.println("\tRECIBO: "+pREC.getNombre()+"  "+pREC.getEdad());        
        
        //MODIFICO OBJETO RECIBIDO
        pREC.setNombre("Pepito Grillo");
        pREC.setEdad(1000);
        
        //ENVÍO DE OBJETO
        //Preparo flujo de salida para objetos
        ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
        //Preparo objeto y lo envío   
        outObjeto.writeObject(pREC);//enviando objeto
        System.out.println("\tENVÍO: "+pREC.getNombre()+"  "+pREC.getEdad());
                
        //CERRAMOS FLUJOS Y SOCKETS
        outObjeto.close();
        inObjeto.close();
        cliente.close();        
    }//fin main   
}//fin clase
