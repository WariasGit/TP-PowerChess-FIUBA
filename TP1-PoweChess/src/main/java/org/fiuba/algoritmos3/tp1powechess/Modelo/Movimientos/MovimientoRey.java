package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public class MovimientoRey implements EstrategiaDeMovimiento{
    public boolean esMovimientoValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        Coordenada2D diferenciasCoordenadas = coordenadaFinal.calcularDiferenciaCon(coordenadaInicial);

        Casillero casilleroInicial = tableroCuadrado.getCasillero(coordenadaInicial.getRow(),coordenadaInicial.getCol());
        Casillero casilleroFinal = tableroCuadrado.getCasillero(coordenadaFinal.getRow(),coordenadaFinal.getCol());

        Pieza piezaAMover = casilleroInicial.getPieza();
        Pieza piezaAComer = casilleroFinal.getPieza();

        if(casilleroFinal.estaAmenazadoPorColorDistinto(String.valueOf(piezaAMover.getColor()))){
            return false;
        }


        if(tableroCuadrado.caminoEstaDesocupado(coordenadaInicial.getRow(),coordenadaInicial.getCol(),coordenadaFinal.getRow(),coordenadaFinal.getCol()) && !tableroCuadrado.caminoEstaAmenazado(coordenadaInicial.getRow(),coordenadaInicial.getCol(),coordenadaFinal.getRow(),coordenadaFinal.getCol()) && !piezaAMover.seHaMovido()){
            if(diferenciasCoordenadas.esIgual(new Coordenada2D(0,2)) || diferenciasCoordenadas.esIgual(new Coordenada2D(coordenadaInicial.getRow(),3))){
                coordenadaFinal.setRow(coordenadaInicial.getRow());
                coordenadaFinal.setCol(6);
                Torre torre = (Torre) tableroCuadrado.getCasillero(coordenadaInicial.getRow(),7).getPieza();
                if(!torre.seHaMovido()){
                    torre.enrocarSegunEnroqueDerecho(tableroCuadrado,coordenadaInicial.getRow());
                    torre.marcarComoMovida();
                    piezaAMover.marcarComoMovida();
                    return true;
                }
            } else if(diferenciasCoordenadas.esIgual(new Coordenada2D(0,-2)) || diferenciasCoordenadas.esIgual(new Coordenada2D(coordenadaInicial.getRow(),-4))){
                coordenadaFinal.setRow(coordenadaInicial.getRow());
                coordenadaFinal.setCol(2);
                Torre torre = (Torre) tableroCuadrado.getCasillero(coordenadaInicial.getRow(),0).getPieza();
                if(!torre.seHaMovido()){
                    torre.enrocarSegunEnroqueIzquierdo(tableroCuadrado,coordenadaInicial.getRow());
                    torre.marcarComoMovida();
                    piezaAMover.marcarComoMovida();
                    return true;
                }
            }
        } else if (!casilleroFinal.estaOcupado() && tableroCuadrado.caminoEstaDesocupado(coordenadaInicial.getRow(),coordenadaInicial.getCol(),coordenadaFinal.getRow(),coordenadaFinal.getCol())){
            if(piezaAMover.esDireccionDeMovimientoValida(diferenciasCoordenadas.getRow(), diferenciasCoordenadas.getCol())){
                piezaAMover.marcarComoMovida();
                return true;
            };
        } else if (!piezaAComer.esDelMismoColorQue(piezaAMover) && tableroCuadrado.caminoEstaDesocupado(coordenadaInicial.getRow(),coordenadaInicial.getCol(),coordenadaFinal.getRow(),coordenadaFinal.getCol())) {
            if(piezaAMover.esCapturaValida(coordenadaInicial.getRow(),coordenadaInicial.getCol(),coordenadaFinal.getRow(),coordenadaFinal.getCol())) {
                piezaAMover.marcarComoMovida();
                return true;
            };
        }
        return false;
    };
}
