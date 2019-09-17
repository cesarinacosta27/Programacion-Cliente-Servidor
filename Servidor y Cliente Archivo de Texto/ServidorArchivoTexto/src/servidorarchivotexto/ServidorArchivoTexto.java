package servidorarchivotexto;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorArchivoTexto {

    public static void main(String[] args){
       try {
            String a=args[0];
        } catch (Exception e) {
            System.out.println("Error al ingresar el numero de puerto\n Siga el formato ej. \"2001\" "+e.toString());
            System.exit(25);
        }
       //verificar si ingresaron dato
       if(!args[0].isEmpty()){
         ServerSocket socketServidor=null;
         Socket socket=null;
         int puerto=-1;
         //asignando el valor del puerto
         if(args[0].length()>0){
             puerto=Integer.valueOf(args[0]);
         }
         do{
         //creando socket
         
         if(socket==null){
           try {
              if(puerto>0){
               socketServidor = new ServerSocket(puerto);
               socket=socketServidor.accept();
              }else{
                  System.out.println("Numero de puerto no valido\nNo se permiten numeros negativos");
                  System.exit(0);
              }
           } catch (IOException e) {
               System.out.println("Error al crear el socket "+e.toString());
               System.exit(7);
           }
         }
         BufferedReader lector=null;
         if(!socket.isClosed()){
          try{
              lector = new BufferedReader(
              new InputStreamReader(socket.getInputStream())
            );  
          }catch(IOException e){
              System.out.println("Error al crear el lector "+e.toString());
              System.exit(1);
          }
         }
        String entrada=null;
        String salida;
        String cerrar="";
        PrintWriter escritor=null;
        if(!socket.isClosed()){    
            try {
                 escritor = new PrintWriter(socket.getOutputStream(),true);  
            } catch (IOException e) {
                System.out.println("Error al crear el escritor "+e.toString());
                System.exit(2);
            }
        }
            try{
              if(lector!=null){entrada=lector.readLine();}
            }catch(IOException e){
                System.out.println("Error al recibir la ruta "+e.toString());
                System.exit(3);
            }
          if(entrada!=null){
            if(entrada.equalsIgnoreCase("fin")){
                System.out.println("Me voy");
                try{
                 socket.close();
                 socketServidor.close();
                }catch(IOException e){
                    System.out.println("Error al cerrar el socket "+e.toString());
                    System.exit(4);
                }
                System.exit(5);         
            }
             try{
                 File archivo =new File(entrada);
                if(archivo.exists()){
                   if(archivo.canRead()){
                    escritor.println(archivo.getName());
                    Scanner scanner = new Scanner(archivo);
                       while (scanner.hasNextLine()) {
                           salida=scanner.nextLine();
                           escritor.println(salida);
                       }
                     cerrar="si";
                   }else{
                       escritor.println("No cuenta con permiso para leer el archivo");
                       cerrar="si";
                   }
                 }else{
                     escritor.println("El archivo solicitado no existe");
                     cerrar="si";
                     //System.exit(8);
                 }
                }catch(IOException e){
                    System.out.println("Error al leer el archivo "+e.toString());
                    System.exit(6);
                }
                    escritor.println("acabe"); 
                }
           if(cerrar!=null){
               if(cerrar.equalsIgnoreCase("si")){
                    escritor.close();
                    try {
                       lector.close();
                   } catch (IOException e) {
                       System.out.println("Error al cerrar el lector");
                   }
                   try {
                       socket.close();
                       socket=null;
                   } catch (IOException e) {
                       System.out.println("Error al cerrar el socket");
                   }
                   try {
                       socketServidor.close();
                   } catch (IOException e) {
                       System.out.println("Error al cerrar el socket servidor");
                   }
                }
           }
        }while(true);
       
       }else{
           System.out.println("No ingreso el puerto");
       }
    }
    
}
