package org.fiuba.algoritmos3.tp1powechess.Model.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Model.Amenaza.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Model.Enrocable.Enrocable;
import org.fiuba.algoritmos3.tp1powechess.Model.Enrocable.NoEnrocable;
import org.fiuba.algoritmos3.tp1powechess.Model.Tablero.Coordenada;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

public abstract class Pieza {
    protected Configuracion.ColoresJugadores color;
    protected boolean seHaMovido;
    protected int maxDistanciaDeAmenaza;
    protected Enrocable tipoDeEnroque;
    protected ArrayList<int[]> direccionesDeMovimiento;
    protected ArrayList<int[]> direccionesDeAmenaza;
    protected String tipoDePieza;

    public Pieza(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.seHaMovido = false;
        this.tipoDeEnroque = new NoEnrocable();
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

    public boolean esDeColor(String color) {
        return this.color.equals(color);
    }

    public void setEstrategiaEnroque(Enrocable enroque) {
        this.tipoDeEnroque = enroque;
    }

    public boolean puedeEnrocar() {
        return tipoDeEnroque.puedeEnrocar();
    }

    public abstract boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY);

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

}
