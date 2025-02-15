package Modelos;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.studium.B_Servidor.ServidorHilo;

public class Modelo {

	public static String extraerNombres(String texto) {
        StringBuilder nombres = new StringBuilder();
        Pattern pattern = Pattern.compile("SERVIDOR>\\s+(\\w+)\\s+\\.\\.\\.\\s+Entra en el juego\\."); 
        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {
            nombres.append(matcher.group(1)).append("\n");  
        }

        return nombres.toString().trim();
    }
	
	public static void mostrarMensajeServidor(JFrame ventana) {
	    JOptionPane.showMessageDialog(ventana, 
	        "La partida ha finalizado. Reinicie el servidor para jugar de nuevo", 
	        "<<Información>>", 
	        JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean hanTerminadoTodosLosHilos(List<ServidorHilo> hilosActivos) {
	    for (ServidorHilo hilo : hilosActivos) {
	        if (hilo.isAlive()) {
	            return false; // Al menos un hilo sigue activo
	        }
	    }
	    return true; // Todos han terminado
	}
}
