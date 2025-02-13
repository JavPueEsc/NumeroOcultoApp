package es.studium.B_Servidor;

import java.awt.EventQueue;

import Modelos.Modelo;


public class Servidor_Principal {
	
	/**
	 * Método por el que se ejecuta la aplicación.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor_vista vista = new Servidor_vista();
					Modelo modelo = new Modelo();
					
					new Servidor_Controlador(vista, modelo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
