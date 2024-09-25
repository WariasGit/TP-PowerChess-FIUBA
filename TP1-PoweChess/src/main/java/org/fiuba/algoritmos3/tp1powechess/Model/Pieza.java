package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public abstract class Pieza {
    protected String color;
    protected boolean seHaMovido;
    protected int maxDistanciaDeAmenaza;
    protected Enrocable tipoDeEnroque;
    protected ArrayList<int[]> direccionesDeMovimiento;
    protected ArrayList<int[]> direccionesDeAmenaza;


    public Pieza(String color) {
        this.color = color;
        this.seHaMovido = false;
        this.tipoDeEnroque = new NoEnrocable();
    }

    public String getColor() {
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
        return tipoDeEnroque.puedeEnrocar() && !seHaMovido;
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

}
