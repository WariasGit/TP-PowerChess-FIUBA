package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public class MovimientoRey implements EstrategiaDeMovimiento{
    public Pieza ejecutarMovimientoSiEsValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Coordenada2D diferenciasCoordenadas = coordenadaFinal.calcularDiferenciaCon(coordenadaInicial);

        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getRow(),coordenadaInicial.getCol());
        Casillero casilleroFinal = tableroCuadrado.getCasillero(coordenadaFinal.getRow(),coordenadaFinal.getCol());

        Pieza piezaAMover = casilleroInicial.getPieza();

        if (esMovimientoValido(piezaAMover,coordenadaInicial, coordenadaFinal, casilleroFinal, diferenciasCoordenadas, tableroCuadrado)) {
            return moverPiezaYCapturarSiEsNecesario(piezaAMover, coordenadaInicial, coordenadaFinal, tableroCuadrado);
        }

        return null; // Movimiento no válido
    }

    private boolean esMovimientoValido(Pieza pieza,Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, Casillero casilleroFinal, Coordenada2D diferencias, TableroCuadrado tablero) {
        // Verificar si es un movimiento simple o una captura
        boolean esMovimientoNormal = !casilleroFinal.estaOcupado() && pieza.esDireccionDeMovimientoValida(diferencias.getRow(), diferencias.getCol());
        boolean esCaptura = casilleroFinal.estaOcupado() && !casilleroFinal.getPieza().esDelMismoColorQue(pieza) &&
                pieza.esCapturaValida(diferencias.getRow(), diferencias.getCol(), diferencias.getRow(), diferencias.getCol());

        return (esMovimientoNormal || esCaptura) && tablero.caminoEstaDesocupado(coordenadaInicial.getRow(),coordenadaInicial.getCol(),coordenadaFinal.getRow(),coordenadaFinal.getCol());
    }

    private Pieza moverPiezaYCapturarSiEsNecesario(Pieza piezaAMover, Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tablero) {
        piezaAMover.marcarComoMovida();

        // Remover la pieza del casillero inicial
        tablero.removePieza(coordenadaInicial);

        // Colocar la pieza en el casillero final
        return tablero.setPieza(coordenadaFinal, piezaAMover); // Devuelve la pieza capturada si la hubiera, sino null
    }
}
