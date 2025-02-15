package es.studium.prueba;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.studium.B_Servidor.ServidorHilo;

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

public class Servidor_ChatT3 extends JFrame {

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
	private static Servidor_ChatT3 pantalla;

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
		aleatorio = random.nextInt(101) + "";
		numOculto.setText(aleatorio);

		txtInformacion = new JTextField();
		txtInformacion.setEnabled(false);
		txtInformacion.setDisabledTextColor(Color.BLACK);
		txtInformacion.setBounds(20, 295, 452, 23);
		contentPane.add(txtInformacion);
		txtInformacion.setColumns(10);
	}

	public static void main(String args[]) throws Exception {
		servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");
		pantalla = new Servidor_ChatT3();
		pantalla.setVisible(true);
		txtMostrarConexiones.setText("Número de conexiones actuales: " + 0);

		while (true) {
			synchronized (Servidor_ChatT3.class) {
				if (ACTUALES < MAXIMO) {
					try {
						Socket socket = servidor.accept();
						tabla[ACTUALES] = socket;
						ACTUALES++;

						ServidorHilo hilo = new ServidorHilo(socket);
						hilo.start();

						actualizarInterfazConexiones();
					} catch (SocketException sex) {
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					txtInformacion.setForeground(Color.RED);
					txtInformacion.setText("Máximo número de conexiones alcanzado: " + MAXIMO);
				}
			}
		}

		if (!servidor.isClosed()) {
			try {
				txtInformacion.setForeground(Color.RED);
				txtInformacion.setText("Máximo Nº de conexiones establecidas: " + getCONEXIONES());
				servidor.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Servidor finalizado...");
		}
	}

	public static int getCONEXIONES() {
		return CONEXIONES;
	}

	public static void setCONEXIONES(int cONEXIONES) {
		CONEXIONES = cONEXIONES;
	}

	public static void actualizarInterfazConexiones() {
		txtMostrarConexiones.setText("Número de conexiones actuales: " + ACTUALES);

		if (ACTUALES < MAXIMO) {
			txtInformacion.setText("");
		}
	}
}

