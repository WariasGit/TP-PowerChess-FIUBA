package org.fiuba.algoritmos3.tp1powechess.Utiles;
import javafx.event.EventType;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoPoder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.PeonAscendente;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.PeonDescendente;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;

import javax.swing.event.DocumentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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

    public static class PosicionInicialTorres{
        public static final int FILA_BLANCA = 7;
        public static final int FILA_NEGRA= 0;
        public static final int COLUMNA_IZQUIERDA = 0;
        public static final int COLUMNA_DERECHA= 7;
    }

    public enum TipoPoder {
        FREEZE,
        ESCUDO,
        VUELO,
        LIMPIEZA,
        ROBAR,

    }

    public static class PosicionInicialReyes{
        public static final int FILA_REY_NEGRO = 0;
        public static final int COLUMNA_REY_NEGRO = 4;
        public static final int FILA_REY_BLANCO = 7;
        public static final int COLUMNA_REY_BLANCO = 4;
    }

    public enum CategoriaPoder {
        DURACION,
        ACCION,
        EVOLUCION
    }

    public enum AplicacionPoder {
        PROPIA,
        RIVAL,
        AMBOS
    }

    private static Map<String, EventType<EventoPoder>> mapaEventos = new HashMap<>();
    static{
        mapaEventos.put(CaracteristicasPoderes.DOBLE_JUEGO, EventoPoder.DOBLE_JUEGO);
        mapaEventos.put(CaracteristicasPoderes.ESCUDO, EventoPoder.ESCUDO);
        mapaEventos.put(CaracteristicasPoderes.EVOLUCION, EventoPoder.EVOLUCION);
        mapaEventos.put(CaracteristicasPoderes.FREEZE, EventoPoder.FREEZE);
        mapaEventos.put(CaracteristicasPoderes.LIMPIEZA, EventoPoder.LIMPIEZA);
        mapaEventos.put(CaracteristicasPoderes.ROBAR, EventoPoder.ROBAR);
        mapaEventos.put(CaracteristicasPoderes.VUELO, EventoPoder.VUELO);
    }

    public static EventType<EventoPoder> getEventoPoder(String nombrePoder) {
        return mapaEventos.get(nombrePoder);
    }


    public class CaracteristicasPoderes{
        public static final int DURACION_ESCUDO = 3;
        public static final int DURACION_FREEZE = 2;
        public static final int ACCION_LIMPIEZA = 1;
        public static final int[] PRIMER_MOVIMIENTO_EXTRA = new int[]{-Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA}; // Diagonal derecha arriba
        public static final int[] SEGUNDO_MOVIMIENTO_EXTRA = new int[]{Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA}; //Abajo
        public static final String DOBLE_JUEGO = "Doble_juego";
        public static final String ESCUDO = "Escudo";
        public static final String EVOLUCION = "Evolucion";
        public static final String FREEZE = "Freeze";
        public static final String LIMPIEZA = "Limpieza";
        public static final String ROBAR = "Robar";
        public static final String VUELO = "Vuelo";
    }

    public static class TamanioVentana{
        public static final double ANCHO = 900;
        public static final double ALTO = 750;
        public static final Integer DIMENSION_TABLERO = 8;
    }

    public static class Jugadores {
        public static final int BLANCAS = 0;
        public static final int NEGRAS = 1;
    }

    public static class CaracterFenParaPiezas {
        public static final char TORRE_NEGRA = 'r';
        public static final char CABALLO_NEGRO = 'n';
        public static final char ALFIL_NEGRO = 'b';
        public static final char REINA_NEGRA = 'q';
        public static final char REY_NEGRO = 'k';
        public static final char PEON_NEGRO = 'p';

        public static final char TORRE_BLANCA = 'R';
        public static final char CABALLO_BLANCO = 'N';
        public static final char ALFIL_BLANCO = 'B';
        public static final char REINA_BLANCA = 'Q';
        public static final char REY_BLANCO = 'K';
        public static final char PEON_BLANCO = 'P';
    }

    public static class ValorPiezas{
        public static int VALOR_PEON = 1;
        public static int VALOR_ALFIL = 2;
        public static int VALOR_CABALLO = 2;
        public static int VALOR_TORRE = 5;
        public static int VALOR_REINA = 8;
        public static int VALOR_REY = 10;
        public static int VALOR_MINIMO_PIEZAS = 15;
        public static final int REY_MAS_PEON = 11;
    }

    private final static Map<Character, Supplier<Pieza>> mapaPiezas = new HashMap<>();
    static {
        mapaPiezas.put('p', () -> new PeonDescendente(NEGRO));
        mapaPiezas.put('r', () -> new Torre(NEGRO));
        mapaPiezas.put('n', () -> new Caballo(NEGRO));
        mapaPiezas.put('b', () -> new Alfil(NEGRO));
        mapaPiezas.put('q', () -> new Reina(NEGRO));
        mapaPiezas.put('k', () -> new Rey(NEGRO));

        mapaPiezas.put('P', () -> new PeonAscendente(BLANCO));
        mapaPiezas.put('R', () -> new Torre(BLANCO));
        mapaPiezas.put('N', () -> new Caballo(BLANCO));
        mapaPiezas.put('B', () -> new Alfil(BLANCO));
        mapaPiezas.put('Q', () -> new Reina(BLANCO));
        mapaPiezas.put('K', () -> new Rey(BLANCO));
    }

    public static Pieza getPieza(Character caracter) {
        Supplier<Pieza> constructor = mapaPiezas.get(caracter);
        return constructor != null ? constructor.get() : null;
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
