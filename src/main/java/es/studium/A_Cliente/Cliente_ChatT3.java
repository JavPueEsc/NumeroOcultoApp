package es.studium.A_Cliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelos.Modelo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.Timer;
import javax.swing.UIManager;
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
	static JLabel lblChat;
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
		setBounds(100, 100, 650, 413);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Elementos parte de Chat.
		lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblChat.setBounds(20, 20, 400, 23);
		contentPane.add(lblChat);
		
		txaMostrarChatCliente = new JTextArea();
		scrollPaneMostrarChat = new JScrollPane(txaMostrarChatCliente);
		scrollPaneMostrarChat.setBounds(20, 63, 452, 255);
		contentPane.add(scrollPaneMostrarChat);
		
		txtEnviarMensajeCliente = new JTextField();
		txtEnviarMensajeCliente.setBounds(20, 338, 452, 23);
		contentPane.add(txtEnviarMensajeCliente);
		txtEnviarMensajeCliente.setColumns(10);
		
		btnEnviarCliente = new JButton("Enviar");
		btnEnviarCliente.setBounds(497, 338, 113, 23);
		contentPane.add(btnEnviarCliente);
		
		//Elementos parte de participantes.
	
		lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setHorizontalAlignment(SwingConstants.CENTER);
		lblParticipantes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblParticipantes.setBounds(497, 20, 113, 23);
		contentPane.add(lblParticipantes);
		
		txaParticipantesCliente = new JTextArea();
		scrollPaneParticipantes = new JScrollPane(txaParticipantesCliente);
		scrollPaneParticipantes.setBounds(497, 63, 113, 212);
		contentPane.add(scrollPaneParticipantes);
		
		btnSalirCliente = new JButton("Salir");
		btnSalirCliente.setBounds(497, 295, 113, 23);
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
					String texto = "SERVIDOR> "+ nombre+""+" ..." +" Entra en el chat.";
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
				lblChat.setText("Chat de "+nombre);
				cliente.ejecutar();
			} else {
				System.out.println("El nombre está vacío...");
			}
		}
		
		// Cuando se pulsa el botón Enviar,
		// el mensaje introducido se envía al servidor por el flujo de salida
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnEnviarCliente) {
					String texto = nombre + " > " + txtEnviarMensajeCliente.getText();
					System.out.println(txtEnviarMensajeCliente.getText());
					try {
			            int numero = Integer.parseInt(txtEnviarMensajeCliente.getText());
			            System.out.println("El número es: " + numero);
			            try {
							txtEnviarMensajeCliente.setText("");
							fsalida.writeUTF(texto);
							//Desabilitar botón por 3 segundos.
							btnEnviarCliente.setEnabled(false);
							btnEnviarCliente.setText("Bloqueado");
							//Cambio el color del texto de los botonesuando están desabilitados
							UIManager.put("Button.disabledText", Color.yellow);
							btnEnviarCliente.setBackground(new Color(220, 20, 60));
							
							// Temporizador para reactivar el botón después de 3 segundos (3000 ms)
			                Timer timer = new Timer(3000, new ActionListener() {
			                    @Override
			                    public void actionPerformed(ActionEvent evt) {
			                    	btnEnviarCliente.setEnabled(true); // Volver a habilitar el botón
			                    	btnEnviarCliente.setBackground(new Color(240, 240, 240));
			                    	btnEnviarCliente.setText("Enviar");
			                    }
			                });

			                timer.setRepeats(false); // Ejecutar solo una vez
			                timer.start();
							
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					} catch (NumberFormatException excep) {
			        	JOptionPane.showMessageDialog(null, "'"+txtEnviarMensajeCliente.getText()+"' no es un número.",
								"<<Error>>", JOptionPane.ERROR_MESSAGE);
			            txtEnviarMensajeCliente.setText("");
			        }
					//----------------
				}
				// Si se pulsa el botón Salir,
				// se envía un mensaje indicando que el cliente abandona el chat
				// y también se envía un * para indicar
				// al servidor que el cliente se ha cerrado
						else if (e.getSource() == btnSalirCliente) {
							String texto = "SERVIDOR -> "+ nombre+ " ... Abandona el chat.";
							
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
					//String texto = "";
					while (repetir) {
						try {
							String textoEntrada = fentrada.readUTF();
							
							//Capturar la última frase
							int indiceUltimaFrase = textoEntrada.lastIndexOf("SERVIDOR");
							String ultimaFrase = (indiceUltimaFrase != -1) ? textoEntrada.substring(indiceUltimaFrase + 10).trim() : textoEntrada;
							System.out.println("ulima frase: "+ultimaFrase);
							
							if(ultimaFrase.contains("Entra")) {
								String textoProcesado = Modelo.extraerNombres(textoEntrada);
//								String cadenaProcesada1 = textoEntrada.replace("SERVIDOR> ", "");
//								String nombreParticipante = cadenaProcesada1.replace(" ... Entra en el chat.", "");
//								//Controlo que solo los nombres de los participantes se coloquen en el área de texto.
//								if(!nombreParticipante.contains(">") && !nombreParticipante.contains("->")) {
									Cliente_ChatT3.txaParticipantesCliente.setText(textoProcesado + "\n");
//									System.out.println("Persona Entra");
//								}
							}
							else if (ultimaFrase.contains("Abandona")){
								//ELIMINAR PARTICIPANTES DEL TXA PARTICIPANTES
								int indiceParaTrocear = textoEntrada.lastIndexOf(" -> ");
								String trozoConNombre = (indiceParaTrocear != -1) ? textoEntrada.substring(indiceParaTrocear + 4).trim() : textoEntrada;
								//System.out.println(trozoConNombre);
								String[] trozoConNombreTroceado = trozoConNombre.split(" ");
								String nombreParticipanteAbandona = trozoConNombreTroceado[0];
								//System.out.println("->"+nombreParticipanteAbandona+"<-");
//							
								String ActualizacionParticipantes = Cliente_ChatT3.txaParticipantesCliente.getText().replace(nombreParticipanteAbandona, "");
								Cliente_ChatT3.txaParticipantesCliente.setText(ActualizacionParticipantes);
								System.out.println("Persona abandona");
							}
							else {
								System.out.println("El sistema responde a persona que ha intentado acertar.");
							}
							txaMostrarChatCliente.setText(textoEntrada);
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
