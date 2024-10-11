package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Juego {
    private Configuracion.EstadoJuego estado;
    private final List<Jugador> jugadores;
    private final Turno turno;
    private final TableroCuadrado tablero;
    private String ganador;
    private List<Pieza> piezasEnJuego;


    public Juego(List<Jugador> jugadores) throws IOException {
        this.estado = Configuracion.EstadoJuego.EN_JUEGO;
        this.jugadores = jugadores;
        turno = new Turno(jugadores);
        tablero = new TableroCuadrado();
        cargarPartida();
    }

    public boolean estaEnJaque(Jugador jugadorActual) {
        Rey rey = jugadorActual.getRey();
        if (!rey) {
            return false;
        }
        Jugador jugadorOponente = turno.obtenerOponente(jugadorActual);

        for (Pieza piezaOponente : jugadorOponente.getPiezasEnJuego()) {
            if (piezaOponente.esCapturaValida(piezaOponente.getPosicion().getX(), piezaOponente.getPosicion().getY(),
                    posicionRey.getX(), posicionRey.getY())) {
                return true;
            }
        }
        return false;
    }

    public void gestionarTablas() {
        estado = Configuracion.EstadoJuego.TABLAS;
    }

    public void gestionarRendicion() {
        terminarPartida();
        ganador = turno.getNombreOponente();
    }

    public String getNombreJugadorBlancas() {
        return jugadores.get(Configuracion.Jugadores.BLANCAS).getNombre();
    }

    public String getNombreJugadorNegras() {
        return jugadores.get(Configuracion.Jugadores.NEGRAS).getNombre();
    }

    public String getNombreJugadorActual() {return turno.getTurno().getNombre();}

    public TableroCuadrado getTablero() {return tablero;}

    //Si esto funciona bien, se queda asi
    public Boolean mover(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        try {
            Pieza pieza = tablero.moverPieza(origenFila, origenColumna, destinoFila, destinoColumna);
            return true;
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            return false;
        }
    }

    public void cambiarTurno() {turno.gestionarTurno();}

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

    public Optional<Pieza> getPiezaActual(Integer i, Integer j) {
        return tablero.getPieza(i, j);
    }

    public boolean esCasilleroLibre(int fila, int columna) {
        return tablero.casilleroLibre(fila, columna);
    }

    public ArrayList<Jugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }
}

