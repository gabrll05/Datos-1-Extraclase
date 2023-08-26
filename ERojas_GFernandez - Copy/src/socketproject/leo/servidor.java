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






