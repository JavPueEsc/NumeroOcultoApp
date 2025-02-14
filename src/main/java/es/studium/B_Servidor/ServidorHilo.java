package es.studium.B_Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidorHilo extends Thread {
	DataInputStream fentrada;
	Socket socket;
	boolean fin = false;

	public ServidorHilo(Socket socket) {
		this.socket = socket;
		try {
			fentrada = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error de E/S");
			e.printStackTrace();
		}
	}

// En el método run() lo primero que hacemos
// es enviar todos los mensajes actuales al cliente que se
// acaba de incorporar
	public void run() {
		Servidor_ChatT3.txtMostrarConexiones.setText("Número de conexiones actuales: " + Servidor_ChatT3.ACTUALES);
		String texto = Servidor_ChatT3.txaMostrarChatServidor.getText();
		EnviarMensajes(texto);
// Seguidamente, se crea un bucle en el que se recibe lo que el cliente escribe en el chat.
// Cuando un cliente finaliza con el botón Salir, se envía un * al servidor del Chat,
// entonces se sale del bucle while, ya que termina el proceso del cliente,
// de esta manera se controlan las conexiones actuales
		while (!fin) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF();
				if (cadena.trim().equals("*")) {
					Servidor_ChatT3.ACTUALES--;
					Servidor_ChatT3.txtMostrarConexiones.setText("Número de conexiones actuales: " + Servidor_ChatT3.ACTUALES);
					fin = true;
				}
// El texto que el cliente escribe en el chat,
// se añade al textarea del servidor y se reenvía a todos los clientes
				else {
					//String[] cortar = cadena.split(" ");
					//String nombreCortado = cortar[5];
					//Servidor_ChatT3.txaParticipantesServidor.append(nombreCortado + "\n");
					Servidor_ChatT3.txaMostrarChatServidor.append(cadena + "\n");
					texto = Servidor_ChatT3.txaMostrarChatServidor.getText();
					EnviarMensajes(texto);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				fin = true;
			}
		}
	}
	
// El método EnviarMensajes() envía el texto del textarea a
// todos los sockets que están en la tabla de sockets,
// de esta forma todos ven la conversación.
// El programa abre un stream de salida para escribir el texto en el socket
	private void EnviarMensajes(String texto) {
		for (int i = 0; i < Servidor_ChatT3.CONEXIONES; i++) {
			Socket socket = Servidor_ChatT3.tabla[i];
			try {
				DataOutputStream fsalida = new DataOutputStream(socket.getOutputStream());
				fsalida.writeUTF(texto);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
