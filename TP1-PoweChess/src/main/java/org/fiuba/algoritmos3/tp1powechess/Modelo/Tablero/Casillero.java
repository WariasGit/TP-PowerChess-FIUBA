package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.*;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Estado.*;
import java.util.ArrayList;

public class Casillero {
    private Pieza pieza;
    private Configuracion.ColoresJugadores color;
    private EstadoDeOcupacionCasillero estadoDeOcupacionCasillero;
    private GestorDeAmenazas gestorDeAmenazas;

    public Casillero(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.pieza = null;
        this.estadoDeOcupacionCasillero = new EstadoDesocupado();
        this.gestorDeAmenazas = new GestorDeAmenazas();
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

    public boolean estaAmenazadoPorColorDistinto(String color) {
        ArrayList<Amenaza> amenazasDistintoColor = gestorDeAmenazas.obtenerAmenazasTotalesDistintoColor(color);
        return !amenazasDistintoColor.isEmpty();
    }

    public boolean estaActivamenteAmenazadoPorColorDistinto(String color) {
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

    public ArrayList<Amenaza> getAmenazasActivasDistintoColor(String colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasActivasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasBloqueadasDistintoColor(String colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasBloqueadasDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasDistintoColor(String colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasTotalesDistintoColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasActivasMismoColor(String colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasActivasPorColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasBloqueadasMismoColor(String colorAmenaza){
        return this.gestorDeAmenazas.obtenerAmenazasBloqueadasPorColor(colorAmenaza);
    }

    public ArrayList<Amenaza> getAmenazasMismoColor(String colorAmenaza){
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
