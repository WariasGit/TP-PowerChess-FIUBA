package org.fiuba.algoritmos3.tp1powechess.Model.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class Juego {
    private Configuracion.EstadoJuego estado;
    private List<Jugador> jugadores;
    private Turno turno;
    private TableroCuadrado tablero;
    private Jugador ganador;
    private List<Pieza> piezasEnJuego;


    public Juego(List<Jugador> jugadores) throws IOException {
        this.estado = Configuracion.EstadoJuego.ACTIVO;
        this.jugadores = jugadores;
        turno = new Turno(jugadores);
        tablero = new TableroCuadrado();
        cargarPartida();
    }

    public boolean verificarRendido() {
        if (getJugadorActual().estaRendido()) {
            this.ganador = turno.getOponente();
            return true;
        }
        return false;
    }

    /* Esto lo comento porque voy a tratar de implementarlo de otra forma
    public void gestionarTablas() {
        if(getJugadorActual().ofrecioTablas()) {
            return turno.getOponente().aceptarTablas();
        }
    }

     */

    public String getNombreJugadorBlancas() {
        return jugadores.get(Configuracion.Jugadores.BLANCAS).getNombre();
    }

    public String getNombreJugadorNegras() {
        return jugadores.get(Configuracion.Jugadores.NEGRAS).getNombre();
    }

    //Esto hay que cambiarlo
    public Jugador getJugadorActual() {
            return turno.getTurno();
    }

    public TableroCuadrado getTablero() {
        return tablero;
    }

    //Si esto funciona bien, se queda asi
    public Boolean mover(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        try {
            Optional<Optional<Pieza>> pieza = tablero.moverPieza(origenFila, origenColumna, destinoFila, destinoColumna);
            return true;
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            return false;
        }
    }

    public void cambiarTurno() {
        turno.gestionarTurno();
    }

    public void terminarPartida() {estado = Configuracion.EstadoJuego.FINALIZADO;}


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

    public void setearPiezasDesdeFEN(String cadenaFen) {
        String[] secciones = cadenaFen.split(" ");
        String posicionPiezas = secciones[0];
        int fila = 0;
        int columna = 0;
        for (char caracter : posicionPiezas.toCharArray()) {
            if (caracter == '/') {
                fila++;
                columna = 0;
            } else if (Character.isDigit(caracter)) {
                columna += Character.getNumericValue(caracter);
            } else {
                Pieza pieza = Configuracion.getPieza(caracter);
                if (pieza != null) {
                    tablero.setPiezaInicial(fila, columna, pieza);
                }
                columna++;
            }
        }
    }

    public void cargarPartida() throws IOException {
        String linea = leerArchivoFen(Constantes.RUTA_ARCHIVO_INICIO_FEN);
        setearPiezasDesdeFEN(linea);
    }

}

