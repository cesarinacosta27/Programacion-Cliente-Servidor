
package clientecualquierarchivo;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import sun.net.util.IPAddressUtil;
public class ClienteCualquierArchivo {

    
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
            if(!IPAddressUtil.isIPv4LiteralAddress(ip)){
                System.out.println("IP no valida");
                System.exit(28);
            }
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
        try {
         datosEntrada=lector.readLine();    
        } catch (IOException e) {
            System.out.println("Error al recibir en nombre del archivo "+e.toString());
            System.exit(6);
        }
        if(datosEntrada!=null){
           if(datosEntrada.equalsIgnoreCase("No es un archivo")){
               System.out.println("No es un archivo");
               System.exit(7);
           }
           if(datosEntrada.equalsIgnoreCase("Archivo no encontrado")){
               System.out.println("Archivo no encontrado");
               System.exit(8);
           }
                byte[] contenido = new byte[1000000000];
                FileOutputStream archivoOS=null;
                String linkEntero="";
                 try {
                    ClienteCualquierArchivo cualArch= new ClienteCualquierArchivo();
                    linkEntero=cualArch.getLink();
                 } catch (Exception e) {
                     System.out.println("Error al obtener la ruta de guardado");
                     System.exit(9);
                             
                 }
                 int endIndex= linkEntero.length()-32;
                 String link=linkEntero.substring(6,endIndex);
                 link=link.replaceAll("%20", " ");
                try{
                   archivoOS = new FileOutputStream(link+datosEntrada);
                }catch(IOException e){
                    System.out.println("Error al crear el escritor del archivo");
                    System.exit(10);
                }
                BufferedOutputStream buffOS=null;
                try{
                 buffOS = new BufferedOutputStream(archivoOS);
                }catch(Exception e){
                    System.out.println("Error al crear el buffer "+e.toString());
                    System.exit(11);
                }
                long longitudArchivo=-1;
                try{
                 longitudArchivo=Long.parseLong(lector.readLine());
                }catch(IOException e){
                    System.out.println(e.toString());
                    System.exit(12);
                }
                InputStream flujoSalida=null;
                try{
                  flujoSalida = socket.getInputStream();
                }catch(IOException e){
                    System.out.println("Error al crear el flujo de entrada "+e.toString());
                    System.exit(13);
                }
                int tamano = 0;
                long posicionActual=0;
                try{

                while((tamano=flujoSalida.read(contenido))!=-1){              
                    buffOS.write(contenido, 0, tamano);  
                    posicionActual+=tamano;
                    int porcentaje=(int) ((posicionActual*100)/longitudArchivo);
                    int cuadros=porcentaje/2;
                    int rayas=50-cuadros;
                    System.out.print("[");
                    for (int i = 0; i < cuadros; i++) {
                        System.out.print("■");  
                    }
                    for (int i = 0; i < rayas; i++) {
                        System.out.print("-");
                    }
                    System.out.print("] "+porcentaje+"% Completado\r");

                }
                    System.out.print("[");
                    for (int i = 0; i < 50; i++) {
                        System.out.print("■"); 
                    }
                    System.out.print("] 100% Completado");
                }catch(IOException e){
                    System.out.println("Error al escribir el achivo "+e.toString());
                    System.exit(14);
                }
                try{
                 buffOS.flush();
                 flujoSalida.close();
                }catch(IOException e){
                    System.out.println("Error al vaciar el buffer "+e.toString());
                    System.exit(15);
                }  
                try{
                 socket.close(); 
                }catch(IOException e){
                    System.out.println("Error al cerrar el socket "+e.toString());
                    System.exit(16);
                }
       }
        
    }
    
    public String getLink(){
      URL link=this.getClass().getProtectionDomain().getCodeSource().getLocation(); 
      return link.toString();
    }
}
