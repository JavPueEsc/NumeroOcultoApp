package Modelos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Modelo {

	public static String extraerNombres(String texto) {
        StringBuilder nombres = new StringBuilder();
        Pattern pattern = Pattern.compile("SERVIDOR>\\s+(\\w+)\\s+\\.\\.\\.\\s+Entra en el chat\\."); 
        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {
            nombres.append(matcher.group(1)).append("\n");  
        }

        return nombres.toString().trim();
    }
}
