package socketproject.leo;


import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.awt.Color;


public class servidor {
    public static void main(String args[]){
        // TODO Auto-generated method stub
        server server=new server();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class server extends JFrame implements Runnable {
    public server(){

        setResizable(false); // Desactivar la redimensión de la ventana
        setBounds(600,300,400,400); //Se establece el tamaño de la ventana y su posicion
        Laminaserver mylamina = new Laminaserver();
        add(mylamina);
        setVisible(true);

        Thread myhilo=new Thread(this); //Crea nuevamente un hilo para que el servidor este a la escucha de la informacion que puede recibir
        myhilo.start();
    }

    class Laminaserver extends JPanel {
        public Laminaserver() {



            setLayout(new BorderLayout());
            add(new JScrollPane(server.areadetexto), BorderLayout.CENTER);  // Agregar JTextArea al panel




        }
    }




    @Override
    public void run() { //Mediante esta clase se recibe la informacion enviada desde el socket y se almacena dentro del servidor

        //System.out.println("Estoy escuchando");

        try {
            ServerSocket servidor=new ServerSocket(9999);

            String ip,mensaje,nick;

            sendpackage recievedpackage;

            while(true) {

                Socket misocket = servidor.accept();

                ObjectInputStream data_package = new ObjectInputStream(misocket.getInputStream());

                recievedpackage=(sendpackage) data_package.readObject();

                ip=recievedpackage.getIp();
                mensaje=recievedpackage.getMensaje();
                nick=recievedpackage.getNick();

                //DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
                //String mensajede_texto = flujo_entrada.readUTF();

                //areadetexto.append("\n" + mensajede_texto);

                areadetexto.append(("\n" + nick +  ": " + mensaje + " para " +  ip));

                Socket sendtoclient = new Socket(ip,9191); //Se crea un socket con la ip definoda y en el puerto 9990
                ObjectOutputStream sendtocpackage = new ObjectOutputStream(sendtoclient.getOutputStream()); //Crea el flujo mediante el cual la informacion pasa atraves del socket

                sendtocpackage.writeObject(recievedpackage);

                sendtocpackage.close();

                sendtoclient.close(); //Se cierra el socket
                misocket.close();// Se cierra el socket
            }

        } catch (IOException e) { //Toma cualquier error o excepcion
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static JTextArea areadetexto = new JTextArea(); //Crea una variable privada  estatica

    static{
        areadetexto.setEditable(false);
    }



}
