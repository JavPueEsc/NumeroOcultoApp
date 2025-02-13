package es.studium.A_Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import Modelos.Modelo;

/**
 * @author Javier Pueyo
 * @version 1.0
 */
public class Cliente_Controlador implements ActionListener{

	
	Cliente_vista vista;
	Modelo modelo;
	Socket socket;
	String nombre;
	DataInputStream fentrada;
	DataOutputStream fsalida;
	
	
	
	
	/**
	 * Constructor por parámetros.
	 * @param v Parámetro referente a la vista de los componentes gráficos
	 * @param m Parámetro referente a la clase que contiene los métodos
	 */
	public Cliente_Controlador(Cliente_vista v, Modelo m, Socket s, String n) {
		this.vista = v;
		this.modelo =m;
		this.socket =s;
		this.nombre=n;
		
		//adición listeners a los componentes
		v.getBtnEnviarCliente().addActionListener(this);
		v.getBtnSalirCliente().addActionListener(this);
		
		v.setVisible(true);

	}
	
	/**
	 * Gestiona los eventos.
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent evento) {
		
		
	}

}
