package socketproject.leo;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.awt.Color;

public class cliente { //Se crea una clase principal para empezar con el codigo
    public static void main(String args[]){
        client client = new client();
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class client extends JFrame { //Se crea un cuadro en el que se empezara cin la interfaz
    public client() {
        setResizable(false); // Desactivar la redimensión de la ventana
        setBounds(600,300,400,400); //Se esrablecen las dimensiones de la ventana creada
        Laminaclientt milamina = new Laminaclientt(); //Se crea una lamina encima de esta ventana, esta lamina seria el equivalente de un canva en python

        add(milamina); //Se añade esta lamina o canva encima de la ventana ya creada
        setVisible(true); //Se establece que esta pueda ser visible
    }
}

class Laminaclientt extends JPanel implements Runnable { //Se crea la clase de la lamina para que pueda ser editada y se establece como runnable para implementar hilos dentro de la clase
    private JTextField caja; //Se implementan clases privadas y publicas dentro de la clase
    private JButton boton;
    private JTextArea areadet;  // Hacer esto una variable de instancia

    public JTextField areadeip;

    public JTextField areadenick;

    public Laminaclientt() {
        setLayout(null); //Esto sirve para que se puedan realizar cambios dentro de la lamina y para que esta pueda ser editada

        areadenick = new JTextField(8); //Se establece un JTextfield para que el usuario escriba su nombre dentro de la lamina
        areadenick.setBounds(50,30,80,20); //Se establece la posicion en la que estara este JTextfield y su respewctivo tamaño
        add(areadenick); //Se agrega a la lamina

        areadeip = new JTextField(8); //Se crea un area al igual del area de nick para la el servidor Ip que se estara utilizando
        areadeip.setBounds(280,30,80,20);
        add(areadeip);

        JLabel titulo = new JLabel("WhatsApp"); //Se establece un titulo para el pprograma
        titulo.setBounds(175,-10,100,100);
        add(titulo);

        boton = new JButton("Enviar"); //Se crea un boton para enviar los mensajes de un usuario al otro
        boton.setBounds(295,315,100,30);
        EnviaTexto mievento = new EnviaTexto(); //Se crea un evento, para que asi cuando se presiona el boton se envoen los dd
        boton.addActionListener(mievento); //Se establece un action listener para el boton, de tal manera el evemto sera utilizado al presionar el boton
        add(boton);

        caja = new JTextField(20); //Se crea un JTextfield
        caja.setBounds(40,315,220,30);
        add(caja);




