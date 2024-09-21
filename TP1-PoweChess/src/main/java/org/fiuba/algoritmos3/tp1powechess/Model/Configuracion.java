package org.fiuba.algoritmos3.tp1powechess.Model;

public class Configuracion {
    public static enum ColoresJugadores { NEGRO, BLANCO };

    public static enum EstadoJuego {
        ACTIVO,
        TABLAS,
        JAQUE_MATE,
        FINALIZADO
    }
}
