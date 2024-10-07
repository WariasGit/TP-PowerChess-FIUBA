package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public class MovimientoTorre implements EstrategiaDeMovimiento {
    public boolean esMovimientoValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Coordenada2D diferenciasCoordenadas = coordenadaFinal.calcularDiferenciaCon(coordenadaInicial);

        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getX(),coordenadaInicial.getY());
        Casillero casilleroFinal = tableroCuadrado.getCasillero(coordenadaFinal.getX(),coordenadaFinal.getY());

        Pieza piezaAMover = casilleroInicial.getPieza();
        Pieza piezaAComer = casilleroFinal.getPieza();

        if (!casilleroFinal.estaOcupado() && tableroCuadrado.caminoEstaDesocupado(coordenadaInicial.getX(),coordenadaInicial.getY(),coordenadaFinal.getX(),coordenadaFinal.getY())){
            if(piezaAMover.esDireccionDeMovimientoValida(diferenciasCoordenadas.getX(), diferenciasCoordenadas.getY())){
                piezaAMover.marcarComoMovida();
                return true;
            };
        } else if (!piezaAComer.esDelMismoColorQue(piezaAMover) && tableroCuadrado.caminoEstaDesocupado(coordenadaInicial.getX(),coordenadaInicial.getY(),coordenadaFinal.getX(),coordenadaFinal.getY())) {
            if(piezaAMover.esCapturaValida(coordenadaInicial.getX(),coordenadaInicial.getY(),coordenadaFinal.getX(),coordenadaFinal.getY())) {
                piezaAMover.marcarComoMovida();
                return true;
            };
        }
        return false;
    };
}
