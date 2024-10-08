package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public class MovimientoNormal implements EstrategiaDeMovimiento {
    public Pieza ejecutarMovimientoSiEsValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Coordenada2D diferenciasCoordenadas = coordenadaFinal.calcularDiferenciaCon(coordenadaInicial);

        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getRow(),coordenadaInicial.getCol());
        Casillero casilleroFinal = tableroCuadrado.getCasillero(coordenadaFinal.getRow(),coordenadaFinal.getCol());

        Pieza piezaAMover = casilleroInicial.getPieza();
        Pieza piezaAComer = casilleroFinal.getPieza();

        if (esMovimientoValido(piezaAMover, casilleroFinal, diferenciasCoordenadas, tableroCuadrado)) {
            return moverPiezaYCapturarSiEsNecesario(piezaAMover, piezaAComer, casilleroInicial, casilleroFinal, tableroCuadrado);
        }

        return null; // Movimiento no válido
    }

    private boolean esMovimientoValido(Pieza pieza, Casillero casilleroFinal, Coordenada2D diferencias, TableroCuadrado tablero) {
        // Verificar si es un movimiento simple o una captura
        boolean esMovimientoNormal = !casilleroFinal.estaOcupado() && pieza.esDireccionDeMovimientoValida(diferencias.getRow(), diferencias.getCol());
        boolean esCaptura = casilleroFinal.estaOcupado() && !casilleroFinal.getPieza().esDelMismoColorQue(pieza) &&
                pieza.esCapturaValida(diferencias.getRow(), diferencias.getCol(), diferencias.getRow(), diferencias.getCol());

        return (esMovimientoNormal || esCaptura) && tablero.caminoEstaDesocupado(pieza.getCoordenadaActual(), casilleroFinal.getCoordenada());
    }

    private Pieza moverPiezaYCapturarSiEsNecesario(Pieza piezaAMover, Pieza piezaAComer, Casillero casilleroInicial, Casillero casilleroFinal, TableroCuadrado tablero) {
        piezaAMover.marcarComoMovida();

        // Remover la pieza del casillero inicial
        tablero.removePieza(casilleroInicial.getCoordenada().getRow(), casilleroInicial.getCoordenada().getCol());

        // Colocar la pieza en el casillero final
        tablero.setPieza(casilleroFinal.getCoordenada().getRow(), casilleroFinal.getCoordenada().getCol(), piezaAMover);

        return piezaAComer; // Devuelve la pieza capturada si la hubiera, sino null
    }
}
