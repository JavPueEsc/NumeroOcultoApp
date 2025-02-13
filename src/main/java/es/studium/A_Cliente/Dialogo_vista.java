package es.studium.A_Cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * @author Javier Pueyo
 * @version 1.0
 */
public class Dialogo_vista extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNick;
	private JLabel lblImagenDialogo;
	private JPanel panelBotonera;
	private JButton btnDlgAceptar;
	private JButton btnDlgCancelar;
	private JLabel lblNick;

	
	/**
	 * Constructor vacío de la clase.
	 */
	public Dialogo_vista() {
		setTitle("Entrada");
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\icono_app.jpg"));
		setBounds(100, 100, 340, 154);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		//Imagen.
		{
			lblImagenDialogo = new JLabel("New label");
			lblImagenDialogo.setIcon(new ImageIcon("imagenes\\imagen_dialogo.png"));
			lblImagenDialogo.setBounds(20, 20, 50, 50);
			contentPanel.add(lblImagenDialogo);
		}
		//Label y TextField de nombre/nick.
		{
			txtNick = new JTextField();
			txtNick.setBounds(90, 50, 209, 20);
			contentPanel.add(txtNick);
			txtNick.setColumns(10);
		}
		{
			lblNick = new JLabel("Introduzca su nombre o nick:");
			lblNick.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNick.setBounds(90, 20, 209, 14);
			contentPanel.add(lblNick);
		}
		
		//Botonera y botones.
		{
			panelBotonera = new JPanel();
			panelBotonera.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(panelBotonera, BorderLayout.SOUTH);
			{
				btnDlgAceptar = new JButton("Aceptar");
				btnDlgAceptar.setActionCommand("OK");
				panelBotonera.add(btnDlgAceptar);
				getRootPane().setDefaultButton(btnDlgAceptar);
			}
			{
				btnDlgCancelar = new JButton("Cancel");
				btnDlgCancelar.setActionCommand("Cancel");
				panelBotonera.add(btnDlgCancelar);
			}
		}
	}

}
