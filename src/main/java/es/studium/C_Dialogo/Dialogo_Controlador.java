package es.studium.C_Dialogo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;
import Modelos.Modelo;
import es.studium.A_Cliente.Cliente_Controlador;
import es.studium.A_Cliente.Cliente_vista;


public class Dialogo_Controlador implements ActionListener{
	
	Dialogo_vista vista;
	Modelo modelo;
	
	final int PUERTO = 44445;
	String nombre;
	
	public Dialogo_Controlador(Dialogo_vista v, Modelo m) {
		this.vista = v;
		this.modelo =m;
		
		//Añadir Listeners
		v.getBtnDlgAceptar().addActionListener(this);
		v.getBtnDlgCancelar().addActionListener(this);
		
		v.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource().equals(vista.getBtnDlgAceptar())) {
			Socket socket = null;
			nombre = vista.getTxtNick().getText();
			try {
				socket = new Socket("127.0.0.1", PUERTO);
			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Imposible conectar con el servidor \n" + ex.getMessage(),
						"<<Mensaje de Error:1>>", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (!nombre.trim().equals("")) {
				Cliente_Controlador cliente = new Cliente_Controlador(new Cliente_vista(), new Modelo(), socket, nombre);
				//cliente.ejecutar();
			} else {
				System.out.println("El nombre está vacío...");
			}
		}
		
	}

}
