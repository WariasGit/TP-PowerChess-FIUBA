package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;
import java.util.Arrays;

public class GestorDeJaque {
    Rey reyNegro;
    Rey reyBlanco;
    TableroCuadrado tablero;


    public void setReyNegro(Rey reyNegro) {
        this.reyNegro = reyNegro;
    }

    public void setReyBlanco(Rey reyBlanco) {
        this.reyBlanco = reyBlanco;
    }

    public void setTablero(TableroCuadrado tablero) {
        this.tablero = tablero;
    }

    private Rey getReyDeJugadorActual(Configuracion.ColoresJugadores color) {
        return (color == Configuracion.ColoresJugadores.BLANCO) ? reyBlanco : reyNegro;
    }

    public boolean jugadorActualEnJaque(Configuracion.ColoresJugadores color){
        if(color == Configuracion.ColoresJugadores.BLANCO){
            return reyBlanco.estaEnJaque();
        }
        else{ return reyNegro.estaEnJaque(); }
    }

    public boolean mateJugadorActual(Jugador jugador) {
        Rey reyJugadorActual = getReyDeJugadorActual(jugador.getColor());
        return jugador.estaEnJaque() && !sePuedeSalvarElJaque(reyJugadorActual);
    }

    public void restarMovimientosPosiblesALosReyes(){
        quitarMovimientosPosiblesAlRey(reyNegro);
        quitarMovimientosPosiblesAlRey(reyBlanco);
    }

    private void quitarMovimientosPosiblesAlRey(Rey rey){
        System.out.println("Cantidad de movimientos posibles antes: " + rey.getMovimientosPosibles().size());
        //rey.limpiarListaMovimientosAmenazados();
        for(int[] movimiento: rey.getMovimientosPosibles()){
            System.out.println("Movimientos del rey: " + movimiento[0] + ", " + movimiento[1]);
            Casillero casilleroPosible = tablero.getCasillero(movimiento[0], movimiento[1]);
            //Si un casillero al que se podria mover el rey esta amenazado, entonces deja de ser un movimiento posible.
            if(casilleroPosible.estaAmenazado()){
                rey.quitarMovimientoPosible(movimiento);
                rey.setMovimientoAmenazadoJaque(movimiento);
            }
        }
        System.out.println("Cantidad de movimientos posibles despues: " + rey.getMovimientosPosibles().size());
    }

    private boolean sePuedeSalvarElJaque(Rey rey){
        //Estos son los movimientos que fueron quitados de la lista de movimientos posibles por estar en Jaque
        ArrayList<int[]> movimientosAmenazadosJaque = rey.getMovimientosAmenazadosJaque();
        System.out.println("Cantidad de movimientos bloqueados: " + movimientosAmenazadosJaque.size());
        Coordenada2D posicionActual = rey.getPosicionActual();
        System.out.println("Posicion actual: " + posicionActual.getRow() + ", " + posicionActual.getCol());
        int filaActual = posicionActual.getRow();
        int columnaActual = posicionActual.getCol();
        boolean sePuedeSalvarElJaque = false;
        //Recorremos cada uno de los movimientos que nos llevan a casilleros amenazados.
        for(int[] movimiento: movimientosAmenazadosJaque){
            System.out.println("Movimiento amenazado: " + movimiento[0] + ", " + movimiento[1]);
            Casillero casilleroAmenazado = tablero.getCasillero(movimiento[0], movimiento[1]);
            //Si una pieza aliada del rey puede atacar u ocupar ese casillero, entonces se puede salvar el jaque.
            //Lo separe en 2 if para debug solamente, luego los agrupo
            if(casilleroAmenazado.estaAmenazadoPorMismoColor(rey.getColor())){
                ArrayList<Amenaza> amenazas = casilleroAmenazado.getAmenazas();
                System.out.println("Se puede salvar el jaque por alguna pieza aliada y estas son las amenazas sobre el casillero.");
                for(Amenaza amenaza: amenazas){
                    System.out.println("Color: " + amenaza.getColor());
                    System.out.println("Direccion: " + Arrays.toString(amenaza.getDireccion()));
                    System.out.println("Casilleros: " + amenaza.getCantidadCasilleros());
                }
                sePuedeSalvarElJaque = true;
                break;
            }
            if(this.tablero.puedoMoverUnPeonAlCasillero(movimiento[0], movimiento[1])){
                sePuedeSalvarElJaque = true;
                break;
            }
            //Calculamos la direccion de la amenaza
            int[] direccion = new int[]{movimiento[0] - filaActual, movimiento[1] - columnaActual};
            System.out.println("Direccion: " + direccion[0] + ", " + direccion[1]);
            int filaNueva = filaActual;
            int columnaNueva = columnaActual;
            //Recorremos los casilleros en la direccion encontrada, en busca de uno que pueda ser defendido/atacado por una pieza aliada.
            while(!casilleroAmenazado.estaOcupado() && !sePuedeSalvarElJaque){
                filaNueva += direccion[0];
                columnaNueva += direccion[1];
                System.out.println("Fila y columna nuevas dentro del bucle: " + filaNueva + ", " + columnaNueva);
                casilleroAmenazado = tablero.getCasillero(filaNueva, columnaNueva);
                //Si una pieza aliada del rey puede atacar u ocupar ese casillero, entonces se puede salvar el jaque.
                if(casilleroAmenazado.estaAmenazadoPorMismoColor(rey.getColor()) || this.tablero.puedoMoverUnPeonAlCasillero(filaNueva, columnaNueva)){
                    sePuedeSalvarElJaque = true;
                }
            }
            //Si ya recorrimos todos los casilleros y no pudimos defender ninguno, llegamos hasta el casillero donde esta la amenaza.
            if(!sePuedeSalvarElJaque){
                //Este es el casillero donde esta la amenaza.
                System.out.println("Este casillero debe estar ocupado: " + filaNueva + ", " + columnaNueva);
                casilleroAmenazado = tablero.getCasillero(filaNueva, columnaNueva);
                //Si una pieza aliada del rey capturar a la amenaza, entonces se puede salvar el jaque.
                if(casilleroAmenazado.estaAmenazadoPorMismoColor(rey.getColor())){
                    ArrayList<Amenaza> amenazas = casilleroAmenazado.getAmenazas();
                    System.out.println("Se puede salvar el jaque por alguna pieza aliada que puede capturar a la amenza y estas son las amenazas sobre el casillero.");
                    for(Amenaza amenaza: amenazas) {
                        System.out.println("Color: " + amenaza.getColor());
                        System.out.println("Direccion: " + Arrays.toString(amenaza.getDireccion()));
                        System.out.println("Casilleros: " + amenaza.getCantidadCasilleros());
                    }
                    sePuedeSalvarElJaque = true;
                }
            }
        }
        return sePuedeSalvarElJaque;
    }
}
