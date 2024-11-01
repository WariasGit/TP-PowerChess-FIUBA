package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

/**
 * La clase MovimientoRey implementa la interfaz EstrategiaDeMovimiento y define la estrategia de movimiento
 * específica para la pieza Rey en el tablero.
 * Esta estrategia permite mover el rey, considerando la posibilidad de realizar un enroque, que involucra al rey y una torre.
 */
public class MovimientoRey implements EstrategiaDeMovimiento{

    /**
     * Ejecuta un movimiento para el rey si es válido. El movimiento puede ser un enroque o un movimiento normal.
     * @param coordenadaInicial La coordenada donde se encuentra el rey.
     * @param coordenadaFinal La coordenada a la que se desea mover el rey.
     * @param tableroCuadrado El tablero en el que se realiza el movimiento.
     * @return La torre involucrada en el enroque si el movimiento fue un enroque; de lo contrario,
     * devuelve la pieza capturada si el movimiento resultó en una captura; de lo contrario, devuelve null.
     */
    public Pieza ejecutarMovimientoSiEsValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getFila(),coordenadaInicial.getColumna());
        Rey rey = (Rey) casilleroInicial.getPieza();
        // Verificar si es un enroque
        if (esEnroque(rey, coordenadaInicial, coordenadaFinal, tableroCuadrado)) {
            return realizarEnroque(rey, coordenadaInicial, coordenadaFinal, tableroCuadrado);
        }
        return moverPiezaYCapturarSiEsNecesario(rey, coordenadaInicial, coordenadaFinal, tableroCuadrado);
    }

    /**
     * Verifica si el movimiento del rey es un enroque, ya sea corto o largo.
     * @param rey El rey que se está moviendo.
     * @param coordenadaInicial La coordenada inicial del rey.
     * @param coordenadaFinal La coordenada final donde se desea mover el rey.
     * @param tablero El tablero en el que se verifica el movimiento.
     * @return true si el movimiento es un enroque; de lo contrario, false.
     */
    private boolean esEnroque(Rey rey, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        int fila = coordenadaInicial.getFila();
        int columnaInicial = coordenadaInicial.getColumna();
        int columnaFinal = coordenadaFinal.getColumna();
        // El enroque corto (hacia la derecha)
        if (!rey.seHaMovido() && columnaFinal == Constantes.COLUMNA_SEIS && tablero.caminoEstaDesocupado(fila, columnaInicial, fila, Constantes.COLUMNA_SEIS)) {
            Torre torre = (Torre) tablero.getCasillero(fila, Constantes.COLUMNA_SIETE).getPieza();
            return torre != null && !torre.seHaMovido();
        }
        // El enroque largo (hacia la izquierda)
        if (!rey.seHaMovido() && columnaFinal == Constantes.COLUMNA_DOS && tablero.caminoEstaDesocupado(fila, columnaInicial, fila, Constantes.COLUMNA_UNO)) {
            Torre torre = (Torre) tablero.getCasillero(fila, Constantes.COLUMNA_CERO).getPieza();
            return torre != null && !torre.seHaMovido();
        }
        return false;
    }

    /**
     * Realiza el enroque del rey y la torre, moviéndolos a sus nuevas posiciones.
     * @param rey El rey que se está moviendo.
     * @param coordenadaInicial La coordenada inicial del rey.
     * @param coordenadaFinal La coordenada final a la que se desea mover el rey.
     * @param tablero El tablero en el que se realiza el enroque.
     * @return La torre que se movió durante el enroque; si algo sale mal, devuelve null.
     */
    private Pieza realizarEnroque(Rey rey, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        int fila = coordenadaInicial.getFila();
        int columnaFinal = coordenadaFinal.getColumna();
        // Enroque corto (derecho)
        if (columnaFinal == Constantes.COLUMNA_SEIS) {
            rey.enrocarSegunEnroqueDerecho(tablero, fila); // Mover el rey
            Torre torre = (Torre) tablero.getCasillero(fila, Constantes.COLUMNA_SIETE).getPieza();
            torre.enrocarSegunEnroqueDerecho(tablero, fila);  // Mover la torre
            return torre;
        }
        // Enroque largo (izquierdo)
        if (columnaFinal == Constantes.COLUMNA_DOS) {
            rey.enrocarSegunEnroqueIzquierdo(tablero, fila); // Mover el rey
            Torre torre = (Torre) tablero.getCasillero(fila, Constantes.COLUMNA_CERO).getPieza();
            torre.enrocarSegunEnroqueIzquierdo(tablero, fila);  // Mover la torre
            return torre;
        }
        System.out.println("Algo salio mal en el enroque");
        return null; // Si algo sale mal
    }

    /**
     * Mueve el rey a su nueva posición y captura una pieza enemiga si está presente en el casillero final.
     * @param piezaAMover El rey que se desea mover.
     * @param coordenadaInicial La coordenada inicial del rey.
     * @param coordenadaFinal La coordenada final a la que se desea mover el rey.
     * @param tablero El tablero en el que se realiza el movimiento.
     * @return La pieza capturada si había una en la coordenada final; de lo contrario, devuelve null.
     */
    private Pieza moverPiezaYCapturarSiEsNecesario(Rey piezaAMover, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        // Remover la pieza del casillero inicial
        tablero.removerPieza(coordenadaInicial);
        Casillero casilleroNuevo = tablero.getCasillero(coordenadaFinal.getFila(),coordenadaFinal.getColumna());
        piezaAMover.actualizarCasilleroActual(casilleroNuevo);
        // Colocar la pieza en el casillero final
        return tablero.setPieza(coordenadaFinal, piezaAMover); // Devuelve la pieza capturada si la hubiera, sino null
    }
}
