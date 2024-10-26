package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public class MovimientoNormal implements EstrategiaDeMovimiento {
    public Pieza ejecutarMovimientoSiEsValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getRow(),coordenadaInicial.getCol());
        Pieza piezaAMover = casilleroInicial.getPieza();
        return moverPiezaYCapturarSiEsNecesario(piezaAMover, coordenadaInicial, coordenadaFinal, tableroCuadrado);
    }

    private Pieza moverPiezaYCapturarSiEsNecesario(Pieza piezaAMover, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        // Remover la pieza del casillero inicial
        tablero.removePieza(coordenadaInicial);
        // Colocar la pieza en el casillero final
        return tablero.setPieza(coordenadaFinal, piezaAMover); // Devuelve la pieza capturada si la hubiera, sino null
    }
}
