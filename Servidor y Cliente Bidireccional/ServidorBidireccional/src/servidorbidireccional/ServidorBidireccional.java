
package servidorbidireccional;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorBidireccional {

    public static void main(String[] args){
        try {
            String a=args[0];
        } catch (Exception e) {
            System.out.println("Error al ingresar el numero de puerto\n Siga el formato ej. \"2001\" "+e.toString());
            System.exit(25);
        }
        ServerSocket socketServidor=null;
        Socket socket=null;
        if(!args[0].isEmpty()){
          int puerto=-1;
            if(args[0].length()>0){puerto=Integer.valueOf(args[0]);}
        try{
            if(puerto>0){
             socketServidor = new ServerSocket(puerto);
             socket=socketServidor.accept();
            }else{
                System.out.println("Numero de puerto no valido\nNo se permiten numeros negativos");
                System.exit(0);
            }
        }catch(IOException e){
            System.out.println("Error al crear el socket "+e.toString());
        }
        BufferedReader lector =null;
        try{
        lector = new BufferedReader(
         new InputStreamReader(socket.getInputStream())
        );  
        }catch(IOException e){
            System.out.println("Error al crear el lector "+e.toString());
        }
        String entrada="";
        Scanner scanner= new Scanner(System.in);
        String salida;
        
        PrintWriter escritor=null;
        try{
         escritor = new PrintWriter(socket.getOutputStream(),true);
        }catch(IOException e){
            System.out.println("Error al crear el escritor "+e.toString());
        }
        do{
            try{
            entrada=lector.readLine();
            System.out.println(entrada);
            }catch(IOException e){
                System.out.println("Error al leer el mensaje "+e.toString());
            }
            
            if(entrada.equalsIgnoreCase("fin")){
              try{
                System.out.println("Me voy");
                socket.close();
                socketServidor.close();
                System.exit(0);         
              }catch(IOException e){
                  System.out.println("Error al cerrar el socket "+e.toString());
                          
              }
            }
            try{
             salida=scanner.nextLine();
             escritor.println(salida);
            }catch(NullPointerException e){
                System.out.println("Error al enviar el mensaje "+e.toString());
            }
        }while(!entrada.equalsIgnoreCase("fin"));
        
        }else{
            System.out.println("No se ingreso puerto");
        }
    }
    
}
