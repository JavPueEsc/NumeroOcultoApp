package es.studium.B_Servidor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import Modelos.Modelo;

/**
 * @author Javier Pueyo
 * @version 1.0
 */
public class Servidor_Controlador implements ActionListener {

	Servidor_vista vista;
	Modelo modelo;
	Random random = new Random();

	static ServerSocket servidor;
	static final int PUERTO = 44445;
	static int CONEXIONES = 0;
	static int ACTUALES = 0;
	static int MAXIMO = 2;
	static Socket[] tabla = new Socket[MAXIMO];

	/**
	 * Constructor por parámetros
	 * 
	 * @param v Parámetro referente a la vista de los componentes gráficos
	 * @param m Parámetro referente a la clase que contiene los métodos
	 */
	public Servidor_Controlador(Servidor_vista v, Modelo m) {
		this.vista = v;
		this.modelo = m;

		// Adición listeners a los componentes.
		v.getBtnSalirServidor().addActionListener(this);

		// Establecer el número oculto.
		v.getNumOculto().setText(random.nextInt(101) + "");

		// Mostrar conexiones actuales
		v.getTxtMostrarConexiones().setText("Número de conexiones actuales: " + 0);

		// Al iniciar la clase se inicia el servidor y las variables y se prepara la
		// pantalla.
		try {
			servidor = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado...");
			// Se usa un bucle para controlar el número de conexiones. Dentro del bucle el
			// servidor espera la conexión del cliente y cuando se conecta se crea un socket.
			while (CONEXIONES < MAXIMO) {
				Socket socket;
				try {
					socket = servidor.accept();
				} catch (SocketException sex) {
					// Sale por aquí si pulsamos el botón salir
					break;
				}
				// El socket creado se añade a la tabla,se incrementa el número de conexiones
				// y se lanza el hilo para gestionar los mensajes del cliente que se acaba de conectar.
							tabla[CONEXIONES] = socket;
							CONEXIONES++;
							ACTUALES++;
							Servidor_Hilo hilo = new Servidor_Hilo(socket);
							hilo.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Si se alcanzan 2 conexiones o se pulsa el botón Salir, el programa se sale del bucle.
		// Al pulsar Salir se cierra el ServerSocket lo que provoca una excepción (SocketException)
		// en la sentencia accept(), la excepción se captura y se ejecuta un break para salir del bucle
				if (!servidor.isClosed()) {
					try {
						v.getTxtInformacion().setForeground(Color.red);
						v.getTxtInformacion().setText("Máximo Nº de conexiones establecidas: " + CONEXIONES);
						servidor.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else {
					System.out.println("Servidor finalizado...");
				}
				
				v.setVisible(true);

	}

	/**
	 * Gestiona los eventos.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
