package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.io.*;

public class GestorDeArchivos {

    /*Implemento los metodos "leerArchivoFen" y "setearPiezasDesdeFEN"
    para cargar una partida desde un archivo de texto,la notacion de FEN
    es una forma de almacenar el estado de una partida en caso de ser guardada*/

    public static String leerArchivoFen(String rutaArchivo) throws IOException {
        InputStream inputStream = Constantes.class.getClassLoader().getResourceAsStream(rutaArchivo);

        if (inputStream == null) {
            throw new FileNotFoundException("InputStream is null, Archivo no encontrado: " + rutaArchivo);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea = reader.readLine();
            if (linea == null || linea.length() < 4) {
                throw new IllegalArgumentException("La cadena FEN no es válida");
            }
            return linea;
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                throw new FileNotFoundException("Archivo no encontrado: " + rutaArchivo);
            } else {
                throw new IOException("Error al leer el archivo: " + rutaArchivo, e);
            }
        }
    }

    public static void guardarFen(String estadoTablero) {
        try {
            FileWriter escritorArchivo = new FileWriter(Constantes.RUTA_ARCHIVO_GUARDAR_PARTIDA);
            BufferedWriter bufferEscritor = new BufferedWriter(escritorArchivo);
            bufferEscritor.write(estadoTablero);
            bufferEscritor.close();
            System.out.println("Archivo guardado exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo.");
            e.printStackTrace();
        }
    }
}
