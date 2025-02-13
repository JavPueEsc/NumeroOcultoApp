package es.studium.B_Servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelos.Modelo;

/**
 * @author Javier Pueyo
 * @version 1.0
 */
public class Servidor_Controlador implements ActionListener{
	
	Servidor_vista vista;
	Modelo modelo;
	
	/**
	 * Constructor por parámetros
	 * @param v Parámetro referente a la vista de los componentes gráficos
	 * @param m Parámetro referente a la clase que contiene los métodos
	 */
	public Servidor_Controlador(Servidor_vista v, Modelo m){
		this.vista = v;
		this.modelo = m;
		
		//Adición listeners a los componentes.
		v.getBtnSalirServidor().addActionListener(this);
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
