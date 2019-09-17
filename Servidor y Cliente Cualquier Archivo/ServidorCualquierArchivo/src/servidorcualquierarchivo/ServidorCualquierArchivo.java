
package servidorcualquierarchivo;
import java.net.*;
import java.io.*;

public class ServidorCualquierArchivo {

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
                System.out.println(entrada);
               }
           } catch (IOException e) {
               System.out.println("Error al leer el archivo a buscar");
               System.exit(5);
           }
          if(entrada!=null){
              File archivo= new File(entrada);
              if(archivo!=null){
                  if(archivo.isFile()){
                      if(archivo.canRead()){
                          FileInputStream archivoIS=null;
                          try{
                             archivoIS = new FileInputStream(archivo);
                          }catch(IOException e){
                              System.out.println("Error al crear el lector de archivo "+e.toString());
                              System.exit(6);
                          }
                           BufferedInputStream buffIS = new BufferedInputStream(archivoIS); 
                           OutputStream flujoSalida=null;
                          try{
                             flujoSalida = socket.getOutputStream(); 
                          }catch(IOException e){
                              System.out.println("Error al crear el flujo para envio "+e.toString());
                              System.exit(7);
                          }
                          byte[] contenido;
                          long tamanoArchivo = archivo.length(); 
                          long posicionActual = 0;
                          escritor.println(archivo.getName());
                          escritor.println(tamanoArchivo);
                          while(posicionActual!=tamanoArchivo){ 
                              int tamano = 10000;
                              if(tamanoArchivo - posicionActual >= tamano)
                                  posicionActual += tamano;    
                              else{ 
                                  tamano = (int)(tamanoArchivo - posicionActual); 
                                  posicionActual = tamanoArchivo;
                              } 
                              contenido = new byte[tamano]; 
                             try{
                              buffIS.read(contenido, 0, tamano); 
                             }catch(IOException e){
                                 System.out.println("Error al leer los datos "+e.toString());
                                 System.exit(8);
                             }
                             try{
                                 flujoSalida.write(contenido);
                             }catch(IOException e){
                                 System.out.println("Error al enviar los datos "+e.toString());
                                 System.exit(9);
                             }  
                              System.out.print("Enviando archivo ... "+(posicionActual*100)/tamanoArchivo+"% completado\r");
                            }  
                       try{
                          flujoSalida.flush(); 
                       }catch(IOException e){
                           System.out.println("Error al vaciar el flujo de salida "+e.toString());
                           System.exit(10);
                       }
                       cerrar="si";
                       }   
                  }else{
                      escritor.println("No es un archivo");
                      System.out.println("No es un archivo");
                      cerrar="si";
                  }
              }else{
                  escritor.println("Archivo no encontrado");
                  System.out.println("Archivo no encontrado");
                  cerrar="si";
              }
             if(cerrar!=null){
                 if(cerrar.equalsIgnoreCase("si")){
                      try{
                        socket.close();
                        socket=null;
                     }catch(IOException e){
                        System.out.println("Error al cerrar el socket "+e.toString()); 
                        System.exit(11);
                     }
                     try{
                         socketServidor.close();
                     }catch(IOException e){
                         System.out.println("Error al cerrar el socketServidor "+e.toString());
                         System.exit(12);
                     }  
                 }
             } 
              
          }
            
        }
       }
    }
     
}
