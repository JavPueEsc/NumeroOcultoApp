package es.studium.A_Cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
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
public class Cliente_vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEnviarMensajeCliente;
	private JTextArea txaMostrarChatCliente;
	private JScrollPane scrollPaneMostrarChat;
	private JButton btnEnviarCliente;
	private JLabel lblParticipantes;
	private JLabel lblChat;
	private JButton btnSalirCliente;
	private JTextArea txaParticipantesCliente;
	private JScrollPane scrollPaneParticipantes;

	/**
	 * Método por el que se ejecuta la aplicación.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente_vista frame = new Cliente_vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor vacío de la clase.
	 */
	public Cliente_vista() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\icono_app.jpg"));
		setTitle("Número oculto - Chat de cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 413);
		contentPane = new JPanel();
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
		
	}

	public JTextArea getTxaMostrarChatCliente() {
		return txaMostrarChatCliente;
	}

	public void setTxaMostrarChatCliente(JTextArea txaMostrarChatCliente) {
		this.txaMostrarChatCliente = txaMostrarChatCliente;
	}

	public JButton getBtnEnviarCliente() {
		return btnEnviarCliente;
	}

	public void setBtnEnviarCliente(JButton btnEnviarCliente) {
		this.btnEnviarCliente = btnEnviarCliente;
	}

	public JButton getBtnSalirCliente() {
		return btnSalirCliente;
	}

	public void setBtnSalirCliente(JButton btnSalirCliente) {
		this.btnSalirCliente = btnSalirCliente;
	}

	public JTextArea getTxaParticipantesCliente() {
		return txaParticipantesCliente;
	}

	public void setTxaParticipantesCliente(JTextArea txaParticipantesCliente) {
		this.txaParticipantesCliente = txaParticipantesCliente;
	}
}
