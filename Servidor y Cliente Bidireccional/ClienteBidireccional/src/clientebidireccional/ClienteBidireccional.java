
package clientebidireccional;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteBidireccional {

    public static void main(String[] args){
        try {
            String a=args[0];
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos \n Siga el formato ej. \"127.0.0.1\" \"2001\" "+ e.toString());
            System.exit(25);
        }
        try {
            String a=args[1];
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos \n Siga el formato ej. \"127.0.0.1\" \"2001\" "+ e.toString());
            System.exit(26);
        }
        Socket socket=null;
        String ip="";
        int puerto=-1;
        if(!args[0].isEmpty()){
            ip=args[0];
        }else{
            System.out.println("No ingreso ip");
            System.exit(8);
        }
        if(!args[1].isEmpty()){
            try {
                puerto=Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertit el numero de puerto\nNo fue ingresado un numero");
                System.exit(10);
            }
        }else{
            System.out.println("No ingreso puerto");
            System.exit(9);
        }
            
        try{
            if(puerto>0){
          socket = new Socket(ip,puerto);         
            }else{
                System.out.println("Numero de puerto invalido");
                System.exit(0);
            }
        }catch(IOException e){
            System.out.println("Error al crear el socket "+e.toString()); 
            System.exit(1);
        }
        PrintWriter escritor=null;
        try{
         escritor= new PrintWriter(
            socket.getOutputStream(),true);  
        }catch(IOException e){
            System.out.println("Error al crear el escritor "+e.toString());
            System.exit(2);
        }
        BufferedReader lector=null;
        try{
          lector = new BufferedReader(
          new InputStreamReader(socket.getInputStream())
        );  
        }catch(IOException e){
            System.out.println("Error al crear el lector "+e.toString());
            System.exit(3);
        }
        String datos="";
        String datosEntrada;
        Scanner scanner= new Scanner(System.in);
        
        do{
         try{
          datos=scanner.nextLine();
          escritor.println(datos);
         }catch(NullPointerException e){
             System.out.println("Error al leer los datos "+e.toString());
             System.exit(4);
         }
          try{
          if(datos.equalsIgnoreCase("fin")){
              socket.close();
              System.exit(5);
          }
          }catch(IOException e){
              System.out.println("Error al cerrar el socket "+e.toString());
              System.exit(6);
          }
          try{
          datosEntrada=lector.readLine();
          System.out.println(datosEntrada);
          }catch(IOException e){
              System.out.println("Error al enviar los datos "+e.toString());
              System.exit(7);
          }
        }while(!datos.equalsIgnoreCase("fin"));
    }
    
}
