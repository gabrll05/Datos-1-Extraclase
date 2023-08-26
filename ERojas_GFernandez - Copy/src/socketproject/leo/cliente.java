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


        areadet = new JTextArea();  // Se crea un area de texto en la cual se escrubiran los mensajes
        areadet.setBounds(40,60,320,240);  // Ajustado el tamaño para que sea más grande
        JScrollPane scroll  = new JScrollPane(areadet); //Se crea una barra para bajar en el texto
        scroll.setBounds(40,60,320,240);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Se establece que la barra este activada todo el tiempo
        areadet.setEditable(false); //No permitir e que pueda ser editado el JTextArea
        add(scroll);


        Thread mihilo = new Thread(this); //Se crea un hilo de forma de que la interfaz este constantemente a la escucha de la informacion enviada por los sockets
        mihilo.start();



        setBackground(Color.GREEN);
    }

    @Override
    public void run() { //Se crea un metodo run el cual es el curerpo del hilo y controla la informacion de entrada al socket
        try{

            ServerSocket servidor_cliente = new ServerSocket(9191); //Se crea un socket en el puerto establecido por el usuario

            Socket clientee;

            sendpackage recievedpackage;
            while(true){
                clientee=servidor_cliente.accept(); //Mientras la instancia anterior sea verdad, el clientee va a aceptar la informacion enviada desde el socket del servidor

                ObjectInputStream entranceobject = new ObjectInputStream(clientee.getInputStream()); //El objeto entrance object entra dentro del clientee

                recievedpackage= (sendpackage) entranceobject.readObject(); //El paquete de recibo se convierte en el packete enviado desde el servidor medisante el metodo .readObject, el cual lea la informacion dentro del paquete enviado

                areadet.append("\n" + recievedpackage.getNick() + ": " +  recievedpackage.getMensaje() + " " + recievedpackage.getIp()); //Se pega la infromacion leida del recievedpackage entro del area de texto

            }


        }catch(Exception e){ //Toma las excepciones dentro del metodo try/catch
            System.out.println(e.getMessage());
        }
    }

    private class EnviaTexto implements ActionListener { //Se crea una clase privada que implementa el action listener para poder tomar la informacion, almacenarla en el socket y enviarla al servidor
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                Socket misocket = new Socket("127.0.0.1",9999); //Se destina una direccion Ip y un puerto en el wue el socket estara establecido
                sendpackage datos = new sendpackage(); //se crea un paquetebde envio en el cual el socket almacenara la informacion

                datos.setIp(areadeip.getText()); //Toma el texto el texto escrito en el area del ip
                datos.setNick(areadenick.getText());//Toma el texto el texto escrito en el area del nick
                datos.setMensaje(caja.getText()); //Toma el texto el texto escrito en el area del texto
                ObjectOutputStream data_package = new ObjectOutputStream(misocket.getOutputStream());//Crea la variable data package como un OutputStream del socket creado anterirormente
                data_package.writeObject(datos);



                misocket.close(); //Se encarga de cerrar el socket
                //DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
                //flujo_salida.writeUTF(texto);
                //flujo_salida.close();

            } catch (IOException ex) { //Toma cualquier error o excepcion dentro del proceso anterior
                JOptionPane.showMessageDialog(null,"Error al conectar al servidor"); //Envia un mensaje en clase de excepcion
            }
        }
    }
}

class sendpackage implements Serializable { //Crea una clase para la informacion enviada atraves de los sockets en los cuadros de texto

    private String ip,mensaje,nick;

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip=ip;
    }

    public String getNick(){return nick;}

    public void setNick(String nick){this.nick=nick;}

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje=mensaje;
    }
}


