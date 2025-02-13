package es.studium.B_Servidor;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
	private JTextArea txaParticipantesServidor;
	private JScrollPane scrollPaneParticipantes;
	private JLabel lblNunmeroOculto;
	private JLabel numOculto;
	static JTextField txtInformacion;

	static ServerSocket servidor;
	static final int PUERTO = 44445;
	static int CONEXIONES = 0;
	static int ACTUALES = 0;
	static int MAXIMO = 2;
	static Socket[] tabla = new Socket[MAXIMO];

	/**
	 * Constructor vacío de la clase.
	 */
	public Servidor_ChatT3() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\icono_app.jpg"));
		setTitle("Número oculto - Ventana del servidor del chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 413);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txaMostrarChatServidor = new JTextArea();
		txaMostrarChatServidor.setEnabled(false);
		txaMostrarChatServidor.setDisabledTextColor(Color.BLACK);
		scrollPaneMostrarChat = new JScrollPane(txaMostrarChatServidor);
		scrollPaneMostrarChat.setBounds(20, 63, 400, 212);
		contentPane.add(scrollPaneMostrarChat);

		txtMostrarConexiones = new JTextField();
		txtMostrarConexiones.setBounds(20, 20, 400, 23);
		txtMostrarConexiones.setEnabled(false);
		txtMostrarConexiones.setDisabledTextColor(Color.BLACK);
		contentPane.add(txtMostrarConexiones);
		txtMostrarConexiones.setColumns(10);

		// Elementos parte de participantes.

		lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setHorizontalAlignment(SwingConstants.CENTER);
		lblParticipantes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblParticipantes.setBounds(435, 20, 113, 23);
		contentPane.add(lblParticipantes);

		txaParticipantesServidor = new JTextArea();
		txaParticipantesServidor.setEnabled(false);
		txaParticipantesServidor.setDisabledTextColor(Color.BLACK);
		scrollPaneParticipantes = new JScrollPane(txaParticipantesServidor);
		scrollPaneParticipantes.setBounds(435, 63, 113, 212);
		contentPane.add(scrollPaneParticipantes);

		btnSalirServidor = new JButton("Salir");
		btnSalirServidor.setBounds(20, 338, 113, 23);
		contentPane.add(btnSalirServidor);

		// Elementos número oculto.
		lblNunmeroOculto = new JLabel("Número oculto:");
		lblNunmeroOculto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNunmeroOculto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNunmeroOculto.setBounds(320, 338, 100, 23);
		contentPane.add(lblNunmeroOculto);

		numOculto = new JLabel("100");
		numOculto.setHorizontalAlignment(SwingConstants.CENTER);
		numOculto.setFont(new Font("Tahoma", Font.PLAIN, 70));
		numOculto.setBounds(425, 296, 120, 65);
		contentPane.add(numOculto);

		txtInformacion = new JTextField();
		txtInformacion.setEnabled(false);
		txtInformacion.setDisabledTextColor(Color.BLACK);
		txtInformacion.setBounds(20, 295, 400, 23);
		contentPane.add(txtInformacion);
		txtInformacion.setColumns(10);

	}

	public static void main(String args[]) throws Exception {
		// Desde el main se inicia el servidor
		// y las variables y se prepara la pantalla
		servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");
		Servidor_ChatT3 pantalla = new Servidor_ChatT3();
		pantalla.setVisible(true);
		txtMostrarConexiones.setText("Número de conexiones actuales: " + 0);
		// Se usa un bucle para controlar el número de conexiones.
		// Dentro del bucle el servidor espera la conexión
		// del cliente y cuando se conecta se crea un socket
		while (CONEXIONES < MAXIMO) {
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
			tabla[CONEXIONES] = socket;
			CONEXIONES++;
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
				txtInformacion.setText("Máximo Nº de conexiones establecidas: " + CONEXIONES);
				servidor.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Servidor finalizado...");
		}
	}
}
