package es.studium.B_Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidorHilo extends Thread {
	DataInputStream fentrada;
	Socket socket;
	boolean fin = false;
	static String cadena;
	private String mensajeIntento;

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
			cadena = "";
			try {
				cadena = fentrada.readUTF();
				if (cadena.trim().equals("*")) {
					 synchronized (Servidor_ChatT3.class) {
	                        Servidor_ChatT3.ACTUALES--;
	                    }
					//Servidor_ChatT3.ACTUALES--;
					Servidor_ChatT3.txtMostrarConexiones.setText("Número de conexiones actuales: " + Servidor_ChatT3.ACTUALES);
					System.out.println(cadena);
					//Servidor_ChatT3.txaMostrarChatServidor.append(cadena + "\n");
					fin = true;
					synchronized (Servidor_ChatT3.class) {
                        Servidor_ChatT3.verificarCierreServidor();
                    }
				}
// El texto que el cliente escribe en el chat,
// se añade al textarea del servidor y se reenvía a todos los clientes
				else {
					if(cadena.contains("Entra")) {
						String[] cortar = cadena.split(" ");
						String nombreCortado = cortar[1];
						//Controloq ue solo los nombres de los participantes se coloquen en el área de texto.
						if(!nombreCortado.equals(">") && !nombreCortado.equals("->")) {
							Servidor_ChatT3.txaParticipantesServidor.append(nombreCortado + "\n");
						}					
					}
					else if (cadena.contains("Abandona")){
						String[] cortar = cadena.split(" ");
						String nombreCortado = cortar[2];
						
						String actualizacionParticipantes = Servidor_ChatT3.txaParticipantesServidor.getText().replace(nombreCortado, "");
						Servidor_ChatT3.txaParticipantesServidor.setText(actualizacionParticipantes);
					}
					else if (cadena.contains("HA")) {
						//String[] troceado = cadena.split(" ");
						//String nombre = troceado[2];
						//System.out.println("CUANTAS VECES: "+nombre);
						
						
					}
					
					else if(cadena.contains("sala")) {
//						String[] cadenaTroceada = cadena.split(" ");
//						String nombreExpulsado = cadenaTroceada[1];
//						cadena = "SERVIDOR -> "+nombreExpulsado+" ha sido expulsado de la sala. \n ¡Buena partida!¡HASTA LA PRÓXIMA!";
					}
					else {
						String[] cortar = cadena.split(" ");
						int numeroPartcipante = Integer.parseInt(cortar[2]);
						
						if(numeroPartcipante == Integer.parseInt(Servidor_ChatT3.aleatorio)) {
							//System.out.println("Son iguales");
							mensajeIntento = "HA ACERTADOOOOO!!!!!";
							cadena = "SERVIDOR> "+cortar[0]+". "+mensajeIntento+".";
						}
						else if (numeroPartcipante > Integer.parseInt(Servidor_ChatT3.aleatorio)){
							//System.out.println("El intento del participante es mayor que el número oculto");
							mensajeIntento = "Pero el número es menor a ";
							cadena = "SERVIDOR> "+cortar[0]+" piensa que el número es el "+cortar[2]+". "+mensajeIntento+" "+cortar[2]+".";
						}
						else {
							//System.out.println("El intento del participante es menor que el número oculto");
							mensajeIntento = "Pero el número es mayor a ";
							cadena = "SERVIDOR> "+cortar[0]+" piensa que el número es el "+cortar[2]+". "+mensajeIntento+" "+cortar[2]+".";
						}
						
						//cadena = "SERVIDOR> "+cortar[0]+" piensa que el número es el "+cortar[2]+". "+mensajeIntento+" "+cortar[2]+".";
					}
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
		for (int i = 0; i < Servidor_ChatT3.getCONEXIONES(); i++) {
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
