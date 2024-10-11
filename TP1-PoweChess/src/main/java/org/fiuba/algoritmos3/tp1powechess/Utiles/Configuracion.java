package org.fiuba.algoritmos3.tp1powechess.Utiles;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.PeonAscendente;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.PeonDescendente;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;

import java.util.HashMap;
import java.util.Map;

import static org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion.ColoresJugadores.BLANCO;
import static org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion.ColoresJugadores.NEGRO;

public class Configuracion {
    public static enum ColoresJugadores {BLANCO, NEGRO};

    public static enum EstadoJuego {
        EN_JUEGO,
        ACTIVO,
        JAQUE_MATE,
        FINALIZADO,
        TABLAS
    }

    public enum CategoriaPoder {
        DURACION,
        ACCION,
        EVOLUCION
    }

    public static class TamanioVentana{
        public static final double ANCHO = 900;
        public static final double ALTO = 750;
    }

    public static enum EstadoJugador {RENDIDO, ACTIVO}

    public static class Jugadores {
        public static final int BLANCAS = 0;
        public static final int NEGRAS = 1;
    }

    private final static Map<Character, Pieza> mapaPiezas = new HashMap<>();
     static {
        mapaPiezas.put('p', new PeonDescendente(NEGRO));
        mapaPiezas.put('r', new Torre(NEGRO));
        mapaPiezas.put('n', new Caballo(NEGRO));
        mapaPiezas.put('b', new Alfil(NEGRO));
        mapaPiezas.put('q', new Reina(NEGRO));
        mapaPiezas.put('k', new Rey(NEGRO));

        mapaPiezas.put('P', new PeonAscendente(BLANCO));
        mapaPiezas.put('R', new Torre(BLANCO));
        mapaPiezas.put('N', new Caballo(BLANCO));
        mapaPiezas.put('B', new Alfil(BLANCO));
        mapaPiezas.put('Q', new Reina(BLANCO));
        mapaPiezas.put('K', new Rey(BLANCO));
    }

    public static Pieza getPieza(Character caracter) {
        return mapaPiezas.get(caracter);
    }

    final static Map<String, String> piezasNegras = Map.ofEntries(
            Map.entry("Caballo", "imagenes/caballo_negro.png"),
            Map.entry("Alfil", "imagenes/alfil_negro.png"),
            Map.entry("Torre", "imagenes/torre_negra.png"),
            Map.entry("Rey", "imagenes/rey_negro.png"),
            Map.entry("Reina", "imagenes/reina_negra.png"),
            Map.entry("Peon", "imagenes/peon_negro.png")
    );
    final static Map<String, String> piezasBlancas = Map.ofEntries(
            Map.entry("Caballo", "imagenes/caballo_blanco.png"),
            Map.entry("Alfil", "imagenes/alfil_blanco.png"),
            Map.entry("Torre", "imagenes/torre_blanca.png"),
            Map.entry("Rey", "imagenes/rey_blanco.png"),
            Map.entry("Reina", "imagenes/reina_blanca.png"),
            Map.entry("Peon", "imagenes/peon_blanco.png")
    );

    public static Map<ColoresJugadores, Map<String, String>> pathPiezas = Map.ofEntries(
            Map.entry(BLANCO, piezasBlancas),
            Map.entry(NEGRO, piezasNegras)
    );
}
