package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
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
        return jugador.estaEnJaque() && !jugador.elReyTieneMovimientosPosibles() && !sePuedeSalvarElJaque(jugador);
    }

    public void restarMovimientosPosiblesALosReyes(){
        quitarMovimientosPosiblesAlRey(reyNegro);
        quitarMovimientosPosiblesAlRey(reyBlanco);
    }

    private void quitarMovimientosPosiblesAlRey(Rey rey){
        for(int[] movimiento: rey.getMovimientosPosibles()){
            Casillero casilleroPosible = tablero.getCasillero(movimiento[0], movimiento[1]);
            //Si un casillero al que se podria mover el rey esta amenazado, entonces deja de ser un movimiento posible.
            if(casilleroPosible.estaAmenazadoPorColorDistinto(rey.getColor())){
                rey.quitarMovimientoPosible(movimiento);
            }
        }
    }

    private boolean sePuedeSalvarElJaque(Jugador jugadorActual){
        Rey rey = getReyDeJugadorActual(jugadorActual.getColor());
        Coordenada2D posicionActual = rey.getPosicionActual();
        int filaActual = posicionActual.getRow();
        int columnaActual = posicionActual.getCol();
        System.out.println("Posicion actual: " + posicionActual.getRow() + ", " + posicionActual.getCol());
        boolean sePuedeSalvarElJaque = false;
        ArrayList<Amenaza> amenazas = rey.getAmenazasRecibidas();
        System.out.println("Amenazas recibidas por el rey: " + amenazas.size());
        for(Amenaza amenaza: amenazas){
            int[] direccion = amenaza.getDireccion();
            int casilleros = amenaza.getCantidadCasilleros();
            Coordenada2D posicionAmenaza = amenaza.getPosicion();
            System.out.println("Posicion de la amenaza: " + posicionAmenaza.getRow() + ", " + posicionAmenaza.getCol());
            if(jugadorActual.puedeOcuparEsteCasillero(posicionAmenaza.getRow(), posicionAmenaza.getCol())){
                sePuedeSalvarElJaque = true;
            }
            else{
                //La direccion de una amenaza esta dada desde su origen, por lo que tenemos que recorrer en el sentido contnrario
                int[] direccionOpuesta = new int[]{-direccion[0], -direccion[1]};
                for(int i = 1; i < casilleros; i++){
                    //Recorremos los casilleros que son atravesados por la amenaza y verificamos si el jugador actual puede atacar u ocupar esos casilleros.
                    //Esto incluye la posibilidad de capturar a la pieza que esta amenazando.
                    int filaNueva = filaActual + direccionOpuesta[0] * i;
                    int columnaNueva = columnaActual + direccionOpuesta[1] * i;
                    System.out.println("Chequeando las posiciones: " + filaNueva + ", " + columnaNueva);
                    if(tablero.esCoordenadaValida(filaNueva, columnaNueva)){
                        if(jugadorActual.puedeOcuparEsteCasillero(filaNueva, columnaNueva)){
                            sePuedeSalvarElJaque = true;
                            break;
                        }
                    }
                }
            }
        }
        return sePuedeSalvarElJaque;
    }
}
