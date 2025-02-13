package es.studium.C_Dialogo;

import java.awt.EventQueue;

import Modelos.Modelo;


public class Cliente_Principal {
	/**
	 * Método por el que se ejecuta la aplicación.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dialogo_vista vista = new Dialogo_vista();
					Modelo modelo = new Modelo();
					
					new Dialogo_Controlador(vista, modelo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
