package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Casillero {
    private Pieza pieza;
    private String color;
    private EstadoDeOcupacionCasillero estadoDeOcupacionCasillero;
    private GestorDeAmenazas gestorDeAmenazas;

    public Casillero(String color) {
        this.color = color;
        this.pieza = null;
        this.estadoDeOcupacionCasillero = new EstadoDesocupado();
        this.gestorDeAmenazas = new GestorDeAmenazas();
    }

    public String getColor() {
        return color;
    }

    public EstadoDeOcupacionCasillero getEstadoCasillero() {
        return estadoDeOcupacionCasillero;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setEstadoDeOcupacion(EstadoDeOcupacionCasillero estadoDeOcupacionCasillero) {
        this.estadoDeOcupacionCasillero = estadoDeOcupacionCasillero;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public void removePieza(Pieza pieza) {
        this.pieza = null;
    }

    public boolean estaOcupado(){
        return estadoDeOcupacionCasillero.estaOcupado();
    }

    public void agregarAmenazas(ArrayList<Amenaza> amenazas){
        this.gestorDeAmenazas.agregarAmenazas(amenazas);
    }

    public void removerTodasLasAmenazas(){
        this.gestorDeAmenazas.removerTodasLasAmenazas();
    }

    public boolean estaAmenazado(){
        return this.gestorDeAmenazas.estaAmenazado();
    }

    public int getNumeroDeAmenazas(){
        return this.gestorDeAmenazas.getNumeroDeAmenazas();
    }

    public int getNumeroDeAmenazasMismoColor(String colorAmenaza){
        return this.gestorDeAmenazas.getNumeroDeAmenazasMismoColor(colorAmenaza);
    }

    public int getNumeroDeAmenazasDistintoColor(String colorAmenaza){
        return this.gestorDeAmenazas.getNumeroDeAmenazasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazas(){
        return this.gestorDeAmenazas.getAmenazas();
    }

    public ArrayList<Amenaza> getAmenazasDistintoColor(String colorAmenaza){
        return this.gestorDeAmenazas.getAmenazasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasMismoColor(String colorAmenaza){
        return this.gestorDeAmenazas.getAmenazasMismoColor(colorAmenaza);
    }
}
