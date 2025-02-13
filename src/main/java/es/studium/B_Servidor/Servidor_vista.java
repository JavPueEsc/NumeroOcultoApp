package es.studium.B_Servidor;

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
public class Servidor_vista extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMostrarConexiones;
	private JTextArea txaMostrarChatServidor;
	private JScrollPane scrollPaneMostrarChat;
	private JLabel lblParticipantes;
	private JButton btnSalirServidor;
	private JTextArea txaParticipantesServidor;
	private JScrollPane scrollPaneParticipantes;
	private JLabel lblNunmeroOculto;
	private JLabel numOculto;

	/**
	 * Método por el que se ejecuta la aplicación.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor_vista frame = new Servidor_vista();
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
	public Servidor_vista() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\icono_app.jpg"));
		setTitle("Número oculto - Ventana del servidor del chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txaMostrarChatServidor = new JTextArea();
		scrollPaneMostrarChat = new JScrollPane(txaMostrarChatServidor);
		scrollPaneMostrarChat.setBounds(20, 63, 400, 255);
		contentPane.add(scrollPaneMostrarChat);
		
		txtMostrarConexiones = new JTextField();
		txtMostrarConexiones.setBounds(20, 20, 400, 23);
		contentPane.add(txtMostrarConexiones);
		txtMostrarConexiones.setColumns(10);
		
		//Elementos parte de participantes.
	
		lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setHorizontalAlignment(SwingConstants.CENTER);
		lblParticipantes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblParticipantes.setBounds(435, 20, 113, 23);
		contentPane.add(lblParticipantes);
		
		txaParticipantesServidor = new JTextArea();
		scrollPaneParticipantes = new JScrollPane(txaParticipantesServidor);
		scrollPaneParticipantes.setBounds(435, 63, 113, 212);
		contentPane.add(scrollPaneParticipantes);
		
		btnSalirServidor = new JButton("Salir");
		btnSalirServidor.setBounds(20, 338, 113, 23);
		contentPane.add(btnSalirServidor);
		
		//Elementos número oculto.
		lblNunmeroOculto = new JLabel("Número oculto:");
		lblNunmeroOculto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNunmeroOculto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNunmeroOculto.setBounds(320, 338, 100, 23);
		contentPane.add(lblNunmeroOculto);
		
		numOculto = new JLabel("100");
		numOculto.setHorizontalAlignment(SwingConstants.CENTER);
		numOculto.setFont(new Font("Tahoma", Font.PLAIN, 38));
		numOculto.setBounds(435, 315, 113, 46);
		contentPane.add(numOculto);
	}

	//Métodos inspectores.
	public JTextField getTxtMostrarConexiones() {
		return txtMostrarConexiones;
	}

	public void setTxtMostrarConexiones(JTextField txtMostrarConexiones) {
		this.txtMostrarConexiones = txtMostrarConexiones;
	}

	public JTextArea getTxaMostrarChatServidor() {
		return txaMostrarChatServidor;
	}

	public void setTxaMostrarChatServidor(JTextArea txaMostrarChatServidor) {
		this.txaMostrarChatServidor = txaMostrarChatServidor;
	}

	public JButton getBtnSalirServidor() {
		return btnSalirServidor;
	}

	public void setBtnSalirServidor(JButton btnSalirServidor) {
		this.btnSalirServidor = btnSalirServidor;
	}

	public JTextArea getTxaParticipantesServidor() {
		return txaParticipantesServidor;
	}

	public void setTxaParticipantesServidor(JTextArea txaParticipantesServidor) {
		this.txaParticipantesServidor = txaParticipantesServidor;
	}

	public JLabel getNumOculto() {
		return numOculto;
	}

	public void setNumOculto(JLabel numOculto) {
		this.numOculto = numOculto;
	}
}
