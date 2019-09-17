
package clientearchivotexto;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class ClienteArchivoTexto {

    
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
         try{
           datosEntrada=lector.readLine();
         }catch(IOException e){
             System.out.println("Error al recibir el nombre del archivo "+e.toString());
             System.exit(6);
         }
         
     if(datosEntrada!=null){
     if(!datosEntrada.equalsIgnoreCase("fin")){
         if(datosEntrada.equalsIgnoreCase("El archivo solicitado no existe")){
             System.out.println("El archivo solicitado no existe");
             System.exit(14);
         }
        File archivo=null;
        String linkEntero="";
               try {
                  ClienteArchivoTexto cliente= new ClienteArchivoTexto();
                  linkEntero=cliente.getLink();
               } catch (Exception e) {
               }
               
        int endIndex= linkEntero.length()-28;
        String link=linkEntero.substring(6,endIndex);
           link=link.replaceAll("%20", " ");
        try{
           archivo= new File(link+datosEntrada);
        }catch(Exception e){
            System.out.println("Error al crear el archivo "+e.toString());     
            System.exit(7);
        }
        FileWriter fw=null;
        PrintWriter pr=null;
         try{
           fw = new FileWriter(archivo);
           pr = new PrintWriter(fw);
         }catch(IOException e){
             System.out.println("Error al crear el escritor de archivo "+e.toString());
             System.exit(8);
         }
           try {
               datosEntrada=lector.readLine();
           } catch (IOException e) {
               System.out.println("Error al recibir el contenido del archivo "+e.toString());
               System.exit(13);
           }
          if(datosEntrada!=null){
           do{
              try{
		  pr.println(datosEntrada);
                  datosEntrada=lector.readLine();
              }catch(IOException e){
                  System.out.println("Error al escribir el archivo "+e.toString());
                  System.exit(9);
              }
           }while(!datosEntrada.equalsIgnoreCase("acabe"));
          }
         try{
	   pr.close();
           fw.close();
         }catch(IOException e){
             System.out.println("Error al cerrar el escritor de archivo "+e.toString());
             System.exit(10);
         }      
        }else{
            System.out.println("fin");
            System.exit(11);
        }
           }else{
               //System.out.println("No se recibio el archivo");
               System.exit(12);
           }
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket "+e.toString());
            System.exit(13);
        }
         
    }
    
    
    public String getLink(){
      URL link=this.getClass().getProtectionDomain().getCodeSource().getLocation(); 
      return link.toString();
    }
    
}
