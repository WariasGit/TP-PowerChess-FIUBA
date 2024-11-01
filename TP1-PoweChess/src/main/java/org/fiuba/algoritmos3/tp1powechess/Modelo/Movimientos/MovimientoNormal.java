package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

/**
 * La clase MovimientoNormal implementa la interfaz EstrategiaDeMovimiento y define la estrategia de movimiento
 * normal para las piezas en el tablero de ajedrez.
 * Permite mover una pieza, verificando si el movimiento es válido y capturando una pieza enemiga si es necesario.
 */
public class MovimientoNormal implements EstrategiaDeMovimiento {

    /**
     * Ejecuta un movimiento, si es válido, moviéndola de una coordenada inicial a una coordenada final el tablero.
     * @param coordenadaInicial La coordenada donde se encuentra la pieza a mover.
     * @param coordenadaFinal La coordenada a la que se desea mover la pieza.
     * @param tableroCuadrado El tablero en el que se realiza el movimiento.
     * @return La pieza capturada si el movimiento resultó en una captura; de lo contrario, devuelve null.
     */
    public Pieza ejecutarMovimientoSiEsValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getFila(),coordenadaInicial.getColumna());
        Pieza piezaAMover = casilleroInicial.getPieza();
        return moverPiezaYCapturarSiEsNecesario(piezaAMover, coordenadaInicial, coordenadaFinal, tableroCuadrado);
    }

    /**
     * Mueve una pieza de su casillero inicial a su casillero final y captura una pieza enemiga si está presente.
     * @param piezaAMover La pieza que se desea mover.
     * @param coordenadaInicial La coordenada inicial de la pieza.
     * @param coordenadaFinal La coordenada final a la que se desea mover la pieza.
     * @param tablero El tablero en el que se realiza el movimiento.
     * @return La pieza capturada si había una en la coordenada final; de lo contrario, devuelve null.
     */
    private Pieza moverPiezaYCapturarSiEsNecesario(Pieza piezaAMover, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        // Remover la pieza del casillero inicial
        tablero.removerPieza(coordenadaInicial);
        // Colocar la pieza en el casillero final
        return tablero.setPieza(coordenadaFinal, piezaAMover); // Devuelve la pieza capturada si la hubiera, sino null
    }
}
