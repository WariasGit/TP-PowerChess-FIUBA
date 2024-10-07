package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Movible.Movible;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.EstrategiaDeMovimiento;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoNormal;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Pieza implements Movible {
    protected Configuracion.ColoresJugadores color;
    protected boolean seHaMovido;
    protected int maxDistanciaDeAmenaza;
    protected EstrategiaDeMovimiento estrategiaDeMovimiento;
    protected ArrayList<int[]> direccionesDeMovimiento;
    protected ArrayList<int[]> direccionesDeAmenaza;
    protected ArrayList<int[]> movimientosPosibles;

    protected String tipoDePieza;

    public Pieza(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.seHaMovido = false;
        this.estrategiaDeMovimiento = new MovimientoNormal();
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public boolean seHaMovido() {
        return seHaMovido;
    }

    public void marcarComoMovida() {
        this.seHaMovido = true;
    }

    public boolean esDelMismoColorQue(Pieza otraPieza){
        return this.color.equals(otraPieza.color);
    }

    public boolean esMovimientoValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        return  this.estrategiaDeMovimiento.esMovimientoValido(coordenadaInicial, coordenadaFinal, tableroCuadrado);
    };

    public abstract boolean esDireccionDeMovimientoValida(int difX, int difY);

    public abstract boolean esCapturaValida(int inicioX, int inicioY, int finX, int finY);

    public ArrayList<Amenaza> getAmenazasGeneradas() {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int maxDistancia = getMaxDistanciaDeAmenaza();

        for (int[] direccion : direccionesDeAmenaza) {
            amenazas.add(new Amenaza(this.color, direccion, maxDistancia));
        }

        return amenazas;
    }

    public int getMaxDistanciaDeAmenaza() {
        return maxDistanciaDeAmenaza;
    }

    public ArrayList<int[]> getDireccionesDeMovimiento() {
        return new ArrayList<int[]> (direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return tipoDePieza;
    }

    public void actualizarMovimientosPosibles(int fila, int columna){
        for(Amenaza amenaza: getAmenazasGeneradas()){
            int[] direccion = amenaza.getDireccion();
            int cantidadCasilleros = amenaza.getCantidadCasilleros();
            for(int i = 1; i <= cantidadCasilleros; i++){
                int[] nuevaPosicion = {fila + direccion[0] * i, columna + direccion[1] * i};
                movimientosPosibles.add(nuevaPosicion);
            }
        }
        if (Objects.equals(tipoDePieza, Constantes.PEON)) {
            for (int[] direccion : direccionesDeMovimiento) {
                int[] nuevaPosicion = {fila + direccion[Constantes.COORDENADA_FILA], columna + direccion[Constantes.COORDENADA_COLUMNA]};
                movimientosPosibles.add(nuevaPosicion);
            }
        }
    }

    public ArrayList<int[]> getMovimientosPosibles(){
        return new ArrayList<int[]>(movimientosPosibles);
    }


    public void limpiarListaMovimientosPosibles() {
        this.movimientosPosibles.clear();
    }
}
