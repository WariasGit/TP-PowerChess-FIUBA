package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Casillero {
    private Pieza pieza;
    private Configuracion.ColoresJugadores color;
    private EstadoDeOcupacionCasillero estadoDeOcupacionCasillero;
    private EstadoDeAmenaza estadoDeAmenaza;

    public Casillero(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.pieza = null;
        this.estadoDeOcupacionCasillero = new EstadoDesocupado();
        this.estadoDeAmenaza = new EstadoDeAmenaza();
    }

    public Configuracion.ColoresJugadores getColor() {
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

    public void removerPieza() {
        this.pieza = null;
    }

    public boolean estaOcupado(){
        return estadoDeOcupacionCasillero.estaOcupado();
    }

    public void agregarAmenazas(ArrayList<Amenaza> amenazas){
        this.estadoDeAmenaza.agregarAmenazas(amenazas);
    }

    public void removerTodasLasAmenazas(){
        this.estadoDeAmenaza.removerTodasLasAmenazas();
    }

    public boolean estaAmenazado(){
        return this.estadoDeAmenaza.estaAmenazado();
    }

    public int getNumeroDeAmenazas(){
        return this.estadoDeAmenaza.getNumeroDeAmenazas();
    }

    public int getNumeroDeAmenazasMismoColor(String colorAmenaza){
        return this.estadoDeAmenaza.getNumeroDeAmenazasMismoColor(colorAmenaza);
    }

    public int getNumeroDeAmenazasDistintoColor(String colorAmenaza){
        return this.estadoDeAmenaza.getNumeroDeAmenazasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazas(){
        return this.estadoDeAmenaza.getAmenazas();
    }

    public ArrayList<Amenaza> getAmenazasDistintoColor(String colorAmenaza){
        return this.estadoDeAmenaza.getAmenazasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasMismoColor(String colorAmenaza){
        return this.estadoDeAmenaza.getAmenazasMismoColor(colorAmenaza);
    }
}
