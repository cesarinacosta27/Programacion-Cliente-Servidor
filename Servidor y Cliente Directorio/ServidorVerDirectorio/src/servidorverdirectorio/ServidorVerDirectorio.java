
package servidorverdirectorio;
import java.net.*;
import java.io.*;
public class ServidorVerDirectorio {
    
    public static void main(String[] args) {
       try {
            String a=args[0];
        } catch (Exception e) {
            System.out.println("Error al ingresar el numero de puerto\n Siga el formato ej. \"2001\" "+e.toString());
            System.exit(25);
        }
       String cerrar=null;
       if(!args[0].isEmpty()){ 
        ServerSocket socketServidor=null;
        Socket socket =null;
        int puerto=-1;
        if(args[0].length()>0){
             puerto=Integer.valueOf(args[0]);
        }
        while(true){
        if(socket==null){
            try{
                if(puerto>0){
                 socketServidor= new ServerSocket(puerto);
                 socket=socketServidor.accept();
                }else{
                    System.out.println("Numero de puerto no valido");
                    System.exit(1);
                }
            }catch(IOException e){
                System.out.println("Error al crear el socket");
                System.exit(2);
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
              System.exit(3);
          }
         }
         
         PrintWriter escritor=null;
        if(!socket.isClosed()){    
            try {
                 escritor = new PrintWriter(socket.getOutputStream(),true);  
            } catch (IOException e) {
                System.out.println("Error al crear el escritor "+e.toString());
                System.exit(4);
            }
        }
        String entrada=null;
           try {
               if(lector!=null){
                entrada=lector.readLine();
               }
           } catch (IOException e) {
               System.out.println("Error al leer el directorio a buscar");
               System.exit(5);
           }
          
           File directorio=null;
           File[] dir=null;
           try {
               if(entrada!=null){
                directorio=new File(entrada);
               }
           } catch (Exception e) {
               System.out.println("Error al buscar el directorio");
               System.exit(6);
           }
           if(directorio!=null){
            if(directorio.exists()){
                if(directorio.isDirectory()){
                   if(directorio.canRead()){
                       try {
                           dir=directorio.listFiles();
                           for(int i=0;i<dir.length;i++){
                           String linea="";
                            if(dir[i].isDirectory()){
                              linea="Carpeta "+dir[i].getName();
                            }else if(dir[i].isFile()){
                              linea="Archivo "+dir[i].getName();
                            }
                              escritor.println(linea);
                            }
                           escritor.println("yaacabamosveteya");
                          cerrar="cierraTodo";  
                       } catch (Exception e) {
                           System.out.println("Error al listar el directorio "+e.toString());
                           System.exit(7);
                       }
                   } else{
                       escritor.println("No cuenta con permiso para leer este directorio");
                       cerrar="cierraTodo"; 
                   }
                }else{
                    escritor.println("La ruta especificada no coincide con un directorio");
                    cerrar="cierraTodo"; 
                }
            }else{
                escritor.println("No se encontró el directorio solicitado");
                cerrar="cierraTodo"; 
            }
           }
           if(cerrar!=null){
             if(cerrar.equalsIgnoreCase("cierraTodo")){
                escritor.close();
                try{
                   lector.close();
                }catch(IOException e){
                    System.out.println("Error al cerrar el lector "+e.toString());
                    System.exit(8);
                }
                try{
                   socket.close();
                   socket=null;
                }catch(IOException e ){
                    System.out.println("Error al cerrar el socket "+e.toString());
                    System.exit(9);
                }
                try{
                  socketServidor.close();
                }catch(IOException e){
                    System.out.println("Error al cerrar el socket servidor "+e.toString());
                    System.exit(10);
                }
             }
           }
         }
       }
       
    }
    
}
