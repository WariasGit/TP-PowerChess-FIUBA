package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;
import java.util.Optional;

public class GestorDeEnroqueBlancas {
    Rey reyBlanco;
    TableroCuadrado tablero;

    public void setReyBlanco(Rey reyBlanco) {
        this.reyBlanco = reyBlanco;
    }

    public void setTablero(TableroCuadrado tablero) {
        this.tablero = tablero;
    }

    private boolean torreIzquirdaNoSeMovio(){
        Optional<Pieza> pieza = tablero.getPieza(Configuracion.PosicionInicialTorres.FILA_BLANCA, Configuracion.PosicionInicialTorres.COLUMNA_IZQUIERDA);
        if(pieza.isPresent()){
            Pieza torre = pieza.get();
            return !torre.seHaMovido();
        }
        return false;
    }

    private boolean torreDerechaNoSeMovio(){
        Optional<Pieza> pieza = tablero.getPieza(Configuracion.PosicionInicialTorres.FILA_BLANCA, Configuracion.PosicionInicialTorres.COLUMNA_DERECHA);
        if(pieza.isPresent()){
            Pieza torre = pieza.get();
            return !torre.seHaMovido();
        }
        return false;
    }

    private boolean casillerosAIzquierdaLibresYSinAmenazas(){
        ArrayList<int[]> movimientosEnroque = reyBlanco.getMovimientosDeEnroqueIzquierda();
        for(int[] movimiento : movimientosEnroque){
            Casillero casilleroActual = tablero.getCasillero(movimiento[0], movimiento[1]);
            if(casilleroActual.estaOcupado() ||  casilleroActual.estaAmenazadoPorColorDistinto(reyBlanco.getColor())){
                return false;
            }
        }
        return true;
    }

    private boolean casillerosAIDerechaLibresYSinAmenazas(){
        ArrayList<int[]> movimientosEnroque = reyBlanco.getMovimientosDeEnroqueDerecha();
        for(int[] movimiento : movimientosEnroque){
            Casillero casilleroActual = tablero.getCasillero(movimiento[0], movimiento[1]);
            if(casilleroActual.estaOcupado() ||  casilleroActual.estaAmenazadoPorColorDistinto(reyBlanco.getColor())){
                return false;
            }
        }
        return true;
    }

    private void enrocarDerecha(){
        if(torreDerechaNoSeMovio() && casillerosAIDerechaLibresYSinAmenazas()){
            System.out.println("Se puede enrocar a derecha");
            ArrayList<int[]> movimientosEnroque = reyBlanco.getMovimientosDeEnroqueDerecha();
            for(int[] movimiento : movimientosEnroque) {
                System.out.println(movimiento[0] + " " + movimiento[1]);
            }
            reyBlanco.agregarMovimientosPosiblesParaEnrocar(movimientosEnroque);
        }
    }

    private void enrocarIzquierda(){
        if(torreIzquirdaNoSeMovio() && casillerosAIzquierdaLibresYSinAmenazas()){
            System.out.println("Se puede enrocar a izquierda");
            ArrayList<int[]> movimientosEnroque = reyBlanco.getMovimientosDeEnroqueIzquierda();
            for(int[] movimiento : movimientosEnroque) {
                System.out.println(movimiento[0] + " " + movimiento[1]);
            }
            reyBlanco.agregarMovimientosPosiblesParaEnrocar(movimientosEnroque);
        }
    }

    public void gestionarEnroque() {
        reyBlanco.cargarMovimientosDeEnroque();
        enrocarDerecha();
        enrocarIzquierda();
//        ArrayList<int[]> movimientos = reyBlanco.getMovimientosPosibles();
//        for(int[] movimiento : movimientos){
//            System.out.println(movimiento[0] + " " + movimiento[1]);
//        }
    }
}
