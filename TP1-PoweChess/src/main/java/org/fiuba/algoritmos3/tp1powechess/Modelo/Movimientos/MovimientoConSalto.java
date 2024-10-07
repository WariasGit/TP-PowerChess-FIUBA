package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public class MovimientoConSalto implements EstrategiaDeMovimiento {
    public boolean esMovimientoValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Coordenada2D diferenciasCoordenadas = coordenadaFinal.calcularDiferenciaCon(coordenadaInicial);

        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getRow(),coordenadaInicial.getCol());
        Casillero casilleroFinal = tableroCuadrado.getCasillero(coordenadaFinal.getRow(),coordenadaFinal.getCol());

        Pieza piezaAMover = casilleroInicial.getPieza();
        Pieza piezaAComer = casilleroFinal.getPieza();

        if (!casilleroFinal.estaOcupado()){
            if(piezaAMover.esDireccionDeMovimientoValida(diferenciasCoordenadas.getRow(), diferenciasCoordenadas.getCol())){
                piezaAMover.marcarComoMovida();
                return true;
            };
        } else if (!piezaAComer.esDelMismoColorQue(piezaAMover)) {
            if(piezaAMover.esCapturaValida(coordenadaInicial.getRow(),coordenadaInicial.getCol(),coordenadaFinal.getRow(),coordenadaFinal.getCol())){
                piezaAMover.marcarComoMovida();
                return true;
            };
        }
        return false;
    };
}
