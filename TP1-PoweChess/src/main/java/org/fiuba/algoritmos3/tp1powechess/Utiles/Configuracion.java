package org.fiuba.algoritmos3.tp1powechess.Utiles;

import org.fiuba.algoritmos3.tp1powechess.Model.*;

import java.util.HashMap;
import java.util.Map;

public class Configuracion {
    public static enum ColoresJugadores { NEGRO, BLANCO };

    public static enum EstadoJuego {
        ACTIVO,
        TABLAS,
        JAQUE_MATE,
        FINALIZADO
    }

    private final static Map<Character, Pieza> mapaPiezas = new HashMap<>();

     static {
        mapaPiezas.put('p', new Pieza(ColoresJugadores.NEGRO, new PeonNegro()));
        mapaPiezas.put('r', new Pieza(ColoresJugadores.NEGRO, new Torre()));
        mapaPiezas.put('n', new Pieza(ColoresJugadores.NEGRO, new Caballo()));
        mapaPiezas.put('b', new Pieza(ColoresJugadores.NEGRO, new Alfil()));
        mapaPiezas.put('q', new Pieza(ColoresJugadores.NEGRO, new Reina()));
        mapaPiezas.put('k', new Pieza(ColoresJugadores.NEGRO, new Rey()));

        mapaPiezas.put('P', new Pieza(ColoresJugadores.BLANCO, new PeonBlanco()));
        mapaPiezas.put('R', new Pieza(ColoresJugadores.BLANCO, new Torre()));
        mapaPiezas.put('N', new Pieza(ColoresJugadores.BLANCO, new Caballo()));
        mapaPiezas.put('B', new Pieza(ColoresJugadores.BLANCO, new Alfil()));
        mapaPiezas.put('Q', new Pieza(ColoresJugadores.BLANCO, new Reina()));
        mapaPiezas.put('K', new Pieza(ColoresJugadores.BLANCO, new Rey()));
    }

    public static Pieza getPieza(Character caracter) {
        return mapaPiezas.get(caracter);
    }
}
