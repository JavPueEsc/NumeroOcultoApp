package es.studium.A_Cliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

/**
 * @author Javier Pueyo
 * @version 1.0
 */
public class Cliente_ChatT3 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextField txtEnviarMensajeCliente;
	static JTextArea txaMostrarChatCliente;
	private JScrollPane scrollPaneMostrarChat;
	private JButton btnEnviarCliente;
	private JLabel lblParticipantes;
	private JLabel lblChat;
	private JButton btnSalirCliente;
	static JTextArea txaParticipantesCliente;
	private JScrollPane scrollPaneParticipantes;
	
	Socket socket;
	DataInputStream fentrada;
	DataOutputStream fsalida;
	String nombre;
	boolean repetir = true;

	/**
	 * Constructor vacío de la clase.
	 */
	public Cliente_ChatT3(Socket socket, String nombre) {
		super(" Conexión del cliente chat: " + nombre);
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\icono_app.jpg"));
		setTitle("Número oculto - Chat de cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 413);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Elementos parte de Chat.
		lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblChat.setBounds(20, 20, 49, 23);
		contentPane.add(lblChat);
		
		txaMostrarChatCliente = new JTextArea();
		scrollPaneMostrarChat = new JScrollPane(txaMostrarChatCliente);
		scrollPaneMostrarChat.setBounds(20, 63, 400, 255);
		contentPane.add(scrollPaneMostrarChat);
		
		txtEnviarMensajeCliente = new JTextField();
		txtEnviarMensajeCliente.setBounds(20, 338, 400, 23);
		contentPane.add(txtEnviarMensajeCliente);
		txtEnviarMensajeCliente.setColumns(10);
		
		btnEnviarCliente = new JButton("Enviar");
		btnEnviarCliente.setBounds(435, 338, 113, 23);
		contentPane.add(btnEnviarCliente);
		
		//Elementos parte de participantes.
	
		lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setHorizontalAlignment(SwingConstants.CENTER);
		lblParticipantes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblParticipantes.setBounds(435, 20, 113, 23);
		contentPane.add(lblParticipantes);
		
		txaParticipantesCliente = new JTextArea();
		scrollPaneParticipantes = new JScrollPane(txaParticipantesCliente);
		scrollPaneParticipantes.setBounds(435, 63, 113, 212);
		contentPane.add(scrollPaneParticipantes);
		
		btnSalirCliente = new JButton("Salir");
		btnSalirCliente.setBounds(435, 295, 113, 23);
		contentPane.add(btnSalirCliente);
		
		//Se crean los flujos de entrada y salida.
		//En el flujo de salida se escribe un mensaje
		//indicando que el cliente se ha unido al Chat.
		//El HiloServidor recibe este mensaje y
		//lo reenvía a todos los clientes conectados
		this.socket = socket;
		this.nombre = nombre;
		btnEnviarCliente.addActionListener(this);
		btnSalirCliente.addActionListener(this);
				
				try {
					fentrada = new DataInputStream(socket.getInputStream());
					fsalida = new DataOutputStream(socket.getOutputStream());
					String texto = "SERVIDOR> Entra en el chat... " + nombre;
					fsalida.writeUTF(texto);
				} catch (IOException ex) {
					System.out.println("Error de E/S");
					ex.printStackTrace();
					System.exit(0);
				}
		
	}
	
	//El método main es el que lanza el cliente,
	//para ello en primer lugar se solicita el nombre o nick del
	//cliente, una vez especificado el nombre
	//se crea la conexión al servidor y se crear la pantalla del Chat(ClientChat)
	//lanzando su ejecución (ejecutar()).
		public static void main(String[] args) throws Exception {
			int puerto = 44445;
			String nombre = JOptionPane.showInputDialog("Introduce tu nombre o nick:");
			Socket socket = null;
			try {
				socket = new Socket("127.0.0.1", puerto);
			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Imposible conectar con el servidor \n" + ex.getMessage(),
						"<<Mensaje de Error:1>>", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (!nombre.trim().equals("")) {
				Cliente_ChatT3 cliente = new Cliente_ChatT3(socket, nombre);
				//cliente.setBounds(0, 0, 540, 400);
				cliente.setVisible(true);
				cliente.setTitle("Número oculto - Chat de "+nombre);
				cliente.ejecutar();
			} else {
				System.out.println("El nombre está vacío...");
			}
		}
		
		// Cuando se pulsa el botón Enviar,
		// el mensaje introducido se envía al servidor por el flujo de salida
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnEnviarCliente) {
					String texto = nombre + "> " + txtEnviarMensajeCliente.getText();
					try {
						txtEnviarMensajeCliente.setText("");
						fsalida.writeUTF(texto);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				// Si se pulsa el botón Salir,
				// se envía un mensaje indicando que el cliente abandona el chat
				// y también se envía un * para indicar
				// al servidor que el cliente se ha cerrado
						else if (e.getSource() == btnSalirCliente) {
							String texto = "SERVIDOR> Abandona el chat... " + nombre;
							try {
								fsalida.writeUTF(texto);
								fsalida.writeUTF("*");
								repetir = false;
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
			// Dentro del método ejecutar(), el cliente lee lo que el
			// hilo le manda (mensajes del Chat) y lo muestra en el textarea.
			// Esto se ejecuta en un bucle del que solo se sale
			// en el momento que el cliente pulse el botón Salir
			// y se modifique la variable repetir
				public void ejecutar() {
					String texto = "";
					while (repetir) {
						try {
							texto = fentrada.readUTF();
							txaMostrarChatCliente.setText(texto);
						} catch (IOException ex) {
							JOptionPane.showMessageDialog(null, "Imposible conectar con el servidor \n" + ex.getMessage(),
									"<<Mensaje de Error:2>>", JOptionPane.ERROR_MESSAGE);
							repetir = false;
						}
					}
					try {
						socket.close();
						System.exit(0);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
}
