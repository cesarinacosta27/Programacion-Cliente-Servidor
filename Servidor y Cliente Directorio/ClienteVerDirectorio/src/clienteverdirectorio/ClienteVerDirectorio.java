
package clienteverdirectorio;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteVerDirectorio {

    public static void main(String[] args) {
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
            System.exit(0);
        }
        if(!args[1].isEmpty()){
            try {
                puerto=Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertit el numero de puerto\nNo fue ingresado un numero");
                System.exit(1);
            }
        }else{
            System.out.println("No ingreso puerto");
            System.exit(2);
        }
       try{
        socket = new Socket(ip,puerto);         
       }catch(IOException e){
           System.out.println("Error al crear el socket "+e.toString());
           System.exit(3);
       }
       PrintWriter escritor=null;
       try{
            escritor= new PrintWriter(
            socket.getOutputStream(),true);  
       }catch(IOException e){
           System.out.println("Error al crear el escritor "+e.toString());
           System.exit(4);
       }
       BufferedReader lector=null;
       try{
           lector = new BufferedReader(
           new InputStreamReader(socket.getInputStream())
          ); 
       }catch(IOException e){
           System.out.println("Error al crear el lector "+e.toString());
           System.exit(5);
       }
       String datos;
        String datosEntrada=null;
        Scanner scanner= new Scanner(System.in);
        
        
            datos=scanner.nextLine();
            escritor.println(datos);
        do{
            try{
              datosEntrada=lector.readLine();
              if(datosEntrada!=null){
                if(datosEntrada.equalsIgnoreCase("No cuenta con permiso para leer este directorio")){
                    System.out.println(datosEntrada);
                    System.exit(30);
                }
                if(datosEntrada.equalsIgnoreCase("La ruta especificada no coincide con un directorio")){
                    System.out.println(datosEntrada);
                    System.exit(31);
                }
                if(datosEntrada.equalsIgnoreCase("No se encontró el directorio solicitado")){
                    System.out.println(datosEntrada);
                    System.exit(32);
                }
                if(!datosEntrada.equalsIgnoreCase("yaacabamosveteya")){
                 System.out.println(datosEntrada);
                }
              }else{
                  datosEntrada="yaacabamosveteya";
              }
            }catch(IOException e){
                System.out.println("Error al recibir los datos "+e.toString());
                System.exit(15);
            }
        }while(!datosEntrada.equalsIgnoreCase("yaacabamosveteya")); 
        escritor.close();
        try {
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el lector "+e.toString());
        }
        try{
          socket.close();
        }catch(IOException e){
            System.out.println("Error al cerrar el socket "+e.toString());
        }
        
        
        
    }
    
}
