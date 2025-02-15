package es.studium.B_Servidor;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelos.Modelo;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * @author Javier Pueyo
 * @version 1.0
 */
public class Servidor_ChatT3 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextField txtMostrarConexiones;
	static JTextArea txaMostrarChatServidor;
	private JScrollPane scrollPaneMostrarChat;
	private JLabel lblParticipantes;
	private JButton btnSalirServidor;
	static JTextArea txaParticipantesServidor;
	private JScrollPane scrollPaneParticipantes;
	private JLabel lblNunmeroOculto;
	private JLabel numOculto;
	static JTextField txtInformacion;

	static ServerSocket servidor;
	static final int PUERTO = 44445;
	private static int CONEXIONES = 0;
	static int ACTUALES = 0;
	static int MAXIMO = 4;
	static Socket[] tabla = new Socket[MAXIMO];
	
	private Random random = new Random();
	static String aleatorio;
	private static int cerrarServidor = 0;
	private static Servidor_ChatT3 pantalla;
	
	/**
	 * Constructor vacío de la clase.
	 */
	public Servidor_ChatT3() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\icono_app.jpg"));
		setTitle("Número oculto - Ventana del servidor del chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 413);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txaMostrarChatServidor = new JTextArea();
		txaMostrarChatServidor.setEnabled(false);
		txaMostrarChatServidor.setDisabledTextColor(Color.BLACK);
		scrollPaneMostrarChat = new JScrollPane(txaMostrarChatServidor);
		scrollPaneMostrarChat.setBounds(20, 63, 452, 212);
		contentPane.add(scrollPaneMostrarChat);

		txtMostrarConexiones = new JTextField();
		txtMostrarConexiones.setBounds(20, 20, 452, 23);
		txtMostrarConexiones.setEnabled(false);
		txtMostrarConexiones.setDisabledTextColor(Color.BLACK);
		contentPane.add(txtMostrarConexiones);
		txtMostrarConexiones.setColumns(10);

		// Elementos parte de participantes.

		lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setHorizontalAlignment(SwingConstants.CENTER);
		lblParticipantes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblParticipantes.setBounds(497, 20, 113, 23);
		contentPane.add(lblParticipantes);

		txaParticipantesServidor = new JTextArea();
		txaParticipantesServidor.setEnabled(false);
		txaParticipantesServidor.setDisabledTextColor(Color.BLACK);
		scrollPaneParticipantes = new JScrollPane(txaParticipantesServidor);
		scrollPaneParticipantes.setBounds(497, 63, 113, 212);
		contentPane.add(scrollPaneParticipantes);

		btnSalirServidor = new JButton("Salir");
		btnSalirServidor.setBounds(20, 338, 113, 23);
		contentPane.add(btnSalirServidor);

		// Elementos número oculto.
		lblNunmeroOculto = new JLabel("Número oculto:");
		lblNunmeroOculto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNunmeroOculto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNunmeroOculto.setBounds(372, 337, 100, 23);
		contentPane.add(lblNunmeroOculto);

		numOculto = new JLabel("100");
		numOculto.setHorizontalAlignment(SwingConstants.CENTER);
		numOculto.setFont(new Font("Tahoma", Font.PLAIN, 70));
		numOculto.setBounds(497, 296, 120, 65);
		contentPane.add(numOculto);
		aleatorio = random.nextInt(101)+"";
		numOculto.setText(aleatorio);

		txtInformacion = new JTextField();
		txtInformacion.setEnabled(false);
		txtInformacion.setDisabledTextColor(Color.BLACK);
		txtInformacion.setBounds(20, 295, 452, 23);
		contentPane.add(txtInformacion);
		txtInformacion.setColumns(10);

	}

	public static void main(String args[]) throws Exception {
		// Desde el main se inicia el servidor
		// y las variables y se prepara la pantalla
		servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");
		pantalla = new Servidor_ChatT3();
		pantalla.setVisible(true);
		txtMostrarConexiones.setText("Número de conexiones actuales: " + 0);
		// Se usa un bucle para controlar el número de conexiones.
		// Dentro del bucle el servidor espera la conexión
		// del cliente y cuando se conecta se crea un socket
		while (getCONEXIONES() < MAXIMO) {
			Socket socket;
			try {
				socket = servidor.accept();
			} catch (SocketException sex) {
				// Sale por aquí si pulsamos el botón salir
				break;
			}
			// El socket creado se añade a la tabla,
			// se incrementa el número de conexiones
			// y se lanza el hilo para gestionar los mensajes
			// del cliente que se acaba de conectar
			tabla[getCONEXIONES()] = socket;
			setCONEXIONES(getCONEXIONES() + 1);
			ACTUALES++;
			ServidorHilo hilo = new ServidorHilo(socket);
			hilo.start();
			
			
			
		}
		// Si se alcanzan 15 conexiones o se pulsa el botón Salir,
		// el programa se sale del bucle.
		// Al pulsar Salir se cierra el ServerSocket
		// lo que provoca una excepción (SocketException)
		// en la sentencia accept(), la excepción se captura
		// y se ejecuta un break para salir del bucle
		
		if (!servidor.isClosed()) {
			
			try {
				txtInformacion.setForeground(Color.red);
				txtInformacion.setText("Máximo Nº de conexiones establecidas: " + getCONEXIONES());
				servidor.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Servidor finalizado...");
		}
	}
	
	public static int getCerrarServidor() {
		return cerrarServidor;
	}

	public static void setCerrarServidor(int cerrarServidor) {
		Servidor_ChatT3.cerrarServidor = cerrarServidor;
	}

	public static int getCONEXIONES() {
		return CONEXIONES;
	}

	public static void setCONEXIONES(int cONEXIONES) {
		CONEXIONES = cONEXIONES;
	}
	
	public static void verificarCierreServidor() {
	    if (ACTUALES == 0) {
	        try {
	            if (!servidor.isClosed()) {
	            	Modelo.mostrarMensajeServidor(pantalla);
	            	servidor.close();
	                System.out.println("Servidor cerrado automáticamente.");
	                System.exit(0);	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

}
