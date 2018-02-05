package SocketsTCP.TiemProc;

/**
 * @author Adrián
 */

import java.io.* ;
import java.net.* ;
import java.util.Date;

public class Servidor {
    static final int Puerto=2000;

    public static void main( String[] arg ) {
        try {
            // Inicio el servidor en el puerto
            ServerSocket sServidor = new ServerSocket(Puerto);
            System.out.println("Escucho el puerto " + Puerto );

            // Se espera hasta que se conecta un cliente
            Socket sCliente = sServidor.accept(); 
            System.out.println("Cliente conectado");

            // Creo los flujos de entrada y salida
            DataInputStream flujo_entrada = new DataInputStream(sCliente.getInputStream());
            DataOutputStream flujo_salida= new DataOutputStream(sCliente.getOutputStream());

            // Recibo número de cliente
            System.out.println("\tCliente número: "+ flujo_entrada.readUTF());
            
            // CALCULO DEL TIEMPO DE PROCESAMIENTO
            long tiempo1=(new Date()).getTime();
            flujo_salida.writeUTF(Long.toString(tiempo1));     

            // Se cierra la conexión
            sCliente.close();
            System.out.println("Cliente desconectado");

        } catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }//FIN main
}//FIN clase