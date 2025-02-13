package es.studium.A_Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelos.Modelo;
/**
 * @author Javier Pueyo
 * @version 1.0
 */
public class Cliente_Controlador implements ActionListener{

	Cliente_vista vista;
	Modelo modelo;
	
	/**
	 * Constructor por parámetros.
	 * @param v Parámetro referente a la vista de los componentes gráficos
	 * @param m Parámetro referente a la clase que contiene los métodos
	 */
	public Cliente_Controlador(Cliente_vista v, Modelo m) {
		this.vista = v;
		this.modelo =m;
		
		//adición listeners a los componentes
		v.getBtnEnviarCliente().addActionListener(this);
		v.getBtnSalirCliente().addActionListener(this);
	}
	
	/**
	 * Gestiona los eventos.
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
