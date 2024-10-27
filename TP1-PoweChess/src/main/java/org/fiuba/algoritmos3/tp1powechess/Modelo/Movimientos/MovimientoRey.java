package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public class MovimientoRey implements EstrategiaDeMovimiento{
    public Pieza ejecutarMovimientoSiEsValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getRow(),coordenadaInicial.getCol());
        Rey rey = (Rey) casilleroInicial.getPieza();
        // Verificar si es un enroque
        if (esEnroque(rey, coordenadaInicial, coordenadaFinal, tableroCuadrado)) {
            return realizarEnroque(rey, coordenadaInicial, coordenadaFinal, tableroCuadrado);
        }
        return moverPiezaYCapturarSiEsNecesario(rey, coordenadaInicial, coordenadaFinal, tableroCuadrado);
    }

    private boolean esEnroque(Rey rey, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        int fila = coordenadaInicial.getRow();
        int columnaInicial = coordenadaInicial.getCol();
        int columnaFinal = coordenadaFinal.getCol();
        // El enroque corto (hacia la derecha)
        if (!rey.seHaMovido() && columnaFinal == 6 && tablero.caminoEstaDesocupado(fila, columnaInicial, fila, 6)) {
            Torre torre = (Torre) tablero.getCasillero(fila, 7).getPieza();
            return torre != null && !torre.seHaMovido();
        }
        // El enroque largo (hacia la izquierda)
        if (!rey.seHaMovido() && columnaFinal == 2 && tablero.caminoEstaDesocupado(fila, columnaInicial, fila, 1)) {
            Torre torre = (Torre) tablero.getCasillero(fila, 0).getPieza();
            return torre != null && !torre.seHaMovido();
        }
        return false;
    }

    private Pieza realizarEnroque(Rey rey, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        int fila = coordenadaInicial.getRow();
        int columnaFinal = coordenadaFinal.getCol();
        // Enroque corto (derecho)
        if (columnaFinal == 6) {
            rey.enrocarSegunEnroqueDerecho(tablero, fila); // Mover el rey
            Torre torre = (Torre) tablero.getCasillero(fila, 7).getPieza();
            torre.enrocarSegunEnroqueDerecho(tablero, fila);  // Mover la torre
            return torre;
        }
        // Enroque largo (izquierdo)
        if (columnaFinal == 2) {
            rey.enrocarSegunEnroqueIzquierdo(tablero, fila); // Mover el rey
            Torre torre = (Torre) tablero.getCasillero(fila, 0).getPieza();
            torre.enrocarSegunEnroqueIzquierdo(tablero, fila);  // Mover la torre
            return torre;
        }
        System.out.println("Algo salio mal en el enroque");
        return null; // Si algo sale mal
    }

    private Pieza moverPiezaYCapturarSiEsNecesario(Rey piezaAMover, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        // Remover la pieza del casillero inicial
        tablero.removePieza(coordenadaInicial);
        Casillero casilleroNuevo = tablero.getCasillero(coordenadaInicial.getRow(),coordenadaInicial.getCol());
        piezaAMover.actualizarCasilleroActual(casilleroNuevo);
        // Colocar la pieza en el casillero final
        return tablero.setPieza(coordenadaFinal, piezaAMover); // Devuelve la pieza capturada si la hubiera, sino null
    }
}
