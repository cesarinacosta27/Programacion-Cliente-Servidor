
package clientesimple;
import java.io.*;
import java.net.*;

public class ClienteSimple {

    public static void main(String[] args) {
        try {
            String a=args[0];
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos \n Siga el formato ej. \"127.0.0.1\" \"2001\" \"Hola\" "+ e.toString());
            System.exit(25);
        }
        try {
            String a=args[1];
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos \n Siga el formato ej. \"127.0.0.1\" \"2001\" \"Hola\" "+ e.toString());
            System.exit(26);
        }
        try {
            String a=args[2];
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos \n Siga el formato ej. \"127.0.0.1\" \"2001\" \"Hola\" "+ e.toString());
            System.exit(26);
        }
       Socket socket=null;
       PrintWriter escritor=null;
       String ip=args[0];
       if(ip.length()==0){
           System.out.println("No ingreso ip");
           System.exit(4);
       }
            
            int puerto=-1;
       if(args[1].length()==0){
           System.out.println("No ingreso puerto");
           System.exit(5);
       }else{
           puerto=Integer.valueOf(args[1]);
       }
             
       try {
              socket = new Socket(ip,puerto);
        } catch (IOException e) {
            System.out.println("Error al crear el socket "+e.toString());
            System.exit(1);
         }
       
       
      try{
            escritor= new PrintWriter(
            socket.getOutputStream(),true);  
       }catch(IOException e){
            System.out.println("Error al crear es escritor "+ e.toString());            
            System.exit(2);
       }     
     String mensaje= args[2];
     if(mensaje.length()==0){
         System.out.println("No ingresó mensaje");
         escritor.println("No se ingresó mensaje");
         //System.exit(6);
     }else{
       escritor.println(mensaje);
     }
        try {
         socket.close();       
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket "+ e.toString());
            System.exit(3);
        }
       
     
    }
    
}
