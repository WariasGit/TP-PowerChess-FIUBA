package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.*;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Estado.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;
import java.util.Objects;

public class Casillero {
    private Pieza pieza;
    private Configuracion.ColoresJugadores color;
    private EstadoDeOcupacionCasillero estadoDeOcupacionCasillero;
    private GestorDeAmenazas gestorDeAmenazas;
    private Coordenada2D posicion;

    public Casillero(Configuracion.ColoresJugadores color, Coordenada2D posicion) {
        this.color = color;
        this.posicion = posicion;
        this.pieza = null;
        this.estadoDeOcupacionCasillero = new EstadoDesocupado();
        this.gestorDeAmenazas = new GestorDeAmenazas();
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public Coordenada2D getPosicion() {return posicion;}

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
        if(!this.estaOcupado()) {
            this.setEstadoDeOcupacion(new EstadoOcupado());
        }
        this.bloquearAmenazasQueSeExtiendenMasDeUnCasillero();
    }

    public Pieza removerPieza() {
        Pieza piezaAux = this.pieza;
        this.pieza = null;
        this.setEstadoDeOcupacion(new EstadoDesocupado());
        this.desbloquearAmenazasBloqueadas();
        return piezaAux;
    }

    public boolean estaOcupado(){
        return estadoDeOcupacionCasillero.estaOcupado();
    }

    public ArrayList<Amenaza> getAmenzasDePiezaActual(){
        if(this.estaOcupado()){
            return this.pieza.getAmenazasGeneradas();
        }
        return new ArrayList<Amenaza>();
    }

    public void agregarAmenazas(ArrayList<Amenaza> amenazas) {
        this.gestorDeAmenazas.agregarAmenazasActivas(amenazas);
        if (this.estaOcupado()) {
            bloquearAmenazasQueSeExtiendenMasDeUnCasillero();
        };
    }

    public void removerLasSiguientesAmenazas(ArrayList<Amenaza> amenazasAEliminar){
        this.gestorDeAmenazas.quitarAmenazasIguales(amenazasAEliminar);
    }

    public boolean estaAmenazado(){
        return this.gestorDeAmenazas.tieneAlMenosUnaAmenaza();
    }

    public ArrayList<Amenaza> getAmenazasJaque(Configuracion.ColoresJugadores color){
        return this.gestorDeAmenazas.obtenerAmenazasTotalesDistintoColor(color);
    }

    public boolean estaAmenazadoPorColorDistinto(Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasDistintoColor = gestorDeAmenazas.obtenerAmenazasTotalesDistintoColor(color);
        return !amenazasDistintoColor.isEmpty();
    }

    public boolean estaAmenazadoPorMismoColor(Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasMiscoColor = gestorDeAmenazas.obtenerAmenazasTotalesMismoColor(color);
        return !amenazasMiscoColor.isEmpty();
    }

    public boolean estaActivamenteAmenazadoPorColorDistinto(Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasActivasDistintoColor = gestorDeAmenazas.obtenerAmenazasActivasDistintoColor(color);
        return !amenazasActivasDistintoColor.isEmpty();
    }

    public ArrayList<Amenaza> getAmenazasActivas(){
        return this.gestorDeAmenazas.getAmenazasActivas();
    }

    public ArrayList<Amenaza> getAmenazasBloqueadas(){
        return this.gestorDeAmenazas.getAmenazasBloqueadas();
    }

    public ArrayList<Amenaza> getAmenazas(){
        return this.gestorDeAmenazas.getAmenazas();
    }

    public ArrayList<Amenaza> getAmenazasActivasDistintoColor(Configuracion.ColoresJugadores colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasActivasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasBloqueadasDistintoColor(Configuracion.ColoresJugadores colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasBloqueadasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasDistintoColor(Configuracion.ColoresJugadores colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasTotalesDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasActivasMismoColor(Configuracion.ColoresJugadores colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasActivasPorColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasBloqueadasMismoColor(Configuracion.ColoresJugadores colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasBloqueadasPorColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasMismoColor(Configuracion.ColoresJugadores colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasTotalesPorColor(colorAmenaza);
    }

    public ArrayList<Amenaza> obtenerAmenazasActivasQueSeExtiendenMasQueUnCasillero(){
        return this.gestorDeAmenazas.obtenerAmenazasActivasQueSeExtiendenMasQue(1);
    }

    public void bloquearAmenazasQueSeExtiendenMasDeUnCasillero() {
        gestorDeAmenazas.moverAmenazasActivasAmasDeUnCasilleroABloqueadas();
    }

    public void desbloquearAmenazasBloqueadas() {
        gestorDeAmenazas.moverTodasAmenazasBloqueadasAActivas();
    }
}
