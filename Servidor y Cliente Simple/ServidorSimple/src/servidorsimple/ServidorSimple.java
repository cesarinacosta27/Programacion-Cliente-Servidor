package servidorsimple;

import java.net.*;
import java.io.*;

public class ServidorSimple {
  
    public static void main(String[] args){
        try {
            String a=args[0];
        } catch (Exception e) {
            System.out.println("Error al ingresar el numero de puerto\n Siga el formato ej. \"2001\""+e.toString());
            System.exit(25);
        }
       //verificar si ingresaron dato
       if(!args[0].isEmpty()){
         Socket socket=null;
         int puerto=-1;
         //asignando el valor del puerto
         if(args[0].length()>0){
              puerto=Integer.valueOf(args[0]);
         }else{
             System.out.println("No ingreso el puerto");
             System.exit(4);
         }
         //creando socket
       try{
         if(puerto>0){
         ServerSocket socketServidor = new ServerSocket(puerto);
         socket=socketServidor.accept();
         }else{
             System.out.println("Numero de puerto no valido\nNo se permiten numeros negativos");
             System.exit(0);
         }
       }catch(IOException e){
            System.out.println("Error al crear el socket "+e.toString());
            System.exit(1);
       }
        //creando lector
         BufferedReader lector=null;
        try {
          lector= new BufferedReader(
           new InputStreamReader(socket.getInputStream())
          );    
        } catch (IOException e) {
            System.out.println("Error al crear el lector "+e.toString());
            System.exit(2);
        }
        
        String entrada;
        //leyendo linea
        try {
          while ((entrada = lector.readLine())!=null) {
            System.out.println("me dijeron "+entrada);
          }   
        } catch (IOException e) {
            System.out.println("Error al leer el mensaje "+e.toString());
            System.exit(3);
        }
        //en caso de que no se ingrese puerto 
        }else{
            System.out.println("No ingresó el puerto");
        } 
    }
    
}
