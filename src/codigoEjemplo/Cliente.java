
package SocketsTCP.TiemProc;

/**
 * @author Adrián
 */

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Random;

public class Cliente {
    static final String HOST = "localhost";
    static final int Puerto=2000;

    public static void main (String[] args) {
        String datos, num_cliente;        

        //Genero aleatoriamiente el número del cliente
        Random serie = new Random();
        int num  = serie.nextInt(100) + 1;
        num_cliente = Integer.toString(num);
        
        /* para leer del teclado
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));*/

        try{
            // Se crea la conexión
            Socket sCliente = new Socket( HOST , Puerto );

            // Creo los flujos de entrada y salida
            DataInputStream flujo_entrada = new DataInputStream(sCliente.getInputStream());
            DataOutputStream flujo_salida= new DataOutputStream(sCliente.getOutputStream());
            
            //Envío de mensaje: número de cliente
            flujo_salida.writeUTF(num_cliente);

            // CALCULO DEL TIEMPO DE PROCESAMIENTO
            datos = flujo_entrada.readUTF();     
            long tiempo1 = Long.valueOf(datos);//Tiempo 
            long tiempo2 = (new Date()).getTime(); //Tiempo actual
            System.out.println("Tiempo1: "+tiempo1+" Tiempo2: "+tiempo2);
            System.out.println("El tiempo en milisegundos es:"+(tiempo2-tiempo1));     

            // Se cierra la conexión
            sCliente.close();
        } catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }//FIN main
}//FIN CLASE