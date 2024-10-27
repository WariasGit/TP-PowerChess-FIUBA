package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;
import java.util.Optional;

public class GestorDeEnroque {
    private TableroCuadrado tablero;
    private Rey rey;

    public void setTablero(TableroCuadrado tablero) {
        this.tablero = tablero;
    }

    private void setRey(Rey rey) {this.rey = rey;}

    // Determina la fila de las piezas en función del color
    private int obtenerFilaInicial() {
        return this.rey.getColor() == Configuracion.ColoresJugadores.BLANCO ? Configuracion.PosicionInicialTorres.FILA_BLANCA : Configuracion.PosicionInicialTorres.FILA_NEGRA;
    }

    private boolean torreIzquirdaNoSeMovio(){
        int filaInicial = obtenerFilaInicial();
        Optional<Pieza> pieza = tablero.getPieza(filaInicial, Configuracion.PosicionInicialTorres.COLUMNA_IZQUIERDA);
        if(pieza.isPresent()){
            Pieza torre = pieza.get();
            return !torre.seHaMovido();
        }
        return false;
    }

    private boolean torreDerechaNoSeMovio(){
        int filaInicial = obtenerFilaInicial();
        Optional<Pieza> pieza = tablero.getPieza(filaInicial, Configuracion.PosicionInicialTorres.COLUMNA_DERECHA);
        if(pieza.isPresent()){
            Pieza torre = pieza.get();
            return !torre.seHaMovido();
        }
        return false;
    }

    private boolean casillerosAIzquierdaLibresYSinAmenazas(){
        ArrayList<int[]> movimientosEnroque = this.rey.getMovimientosDeEnroqueIzquierda();
        for(int[] movimiento : movimientosEnroque){
            Casillero casilleroActual = tablero.getCasillero(movimiento[0], movimiento[1]);
            if(casilleroActual.estaOcupado() ||  casilleroActual.estaAmenazadoPorColorDistinto(this.rey.getColor())){
                return false;
            }
        }
        return true;
    }

    private boolean casillerosAIDerechaLibresYSinAmenazas(){
        ArrayList<int[]> movimientosEnroque = this.rey.getMovimientosDeEnroqueDerecha();
        for(int[] movimiento : movimientosEnroque){
            Casillero casilleroActual = tablero.getCasillero(movimiento[0], movimiento[1]);
            if(casilleroActual.estaOcupado() ||  casilleroActual.estaAmenazadoPorColorDistinto(this.rey.getColor())){
                return false;
            }
        }
        return true;
    }

    private void enrocarDerecha(){
        if(torreDerechaNoSeMovio() && casillerosAIDerechaLibresYSinAmenazas()){
            System.out.println("Se puede enrocar a derecha");
            ArrayList<int[]> movimientosEnroque = this.rey.getMovimientosDeEnroqueDerecha();
            for(int[] movimiento : movimientosEnroque) {
                System.out.println(movimiento[0] + " " + movimiento[1]);
            }
            this.rey.agregarMovimientosPosiblesParaEnrocar(movimientosEnroque);
        }
    }

    private void enrocarIzquierda(){
        if(torreIzquirdaNoSeMovio() && casillerosAIzquierdaLibresYSinAmenazas()){
            System.out.println("Se puede enrocar a izquierda");
            ArrayList<int[]> movimientosEnroque = this.rey.getMovimientosDeEnroqueIzquierda();
            for(int[] movimiento : movimientosEnroque) {
                System.out.println(movimiento[0] + " " + movimiento[1]);
            }
            this.rey.agregarMovimientosPosiblesParaEnrocar(movimientosEnroque);
        }
    }

    public void gestionarEnroque(Rey rey) {
        setRey(rey);
        rey.cargarMovimientosDeEnroque();
        enrocarDerecha();
        enrocarIzquierda();
    }
}
