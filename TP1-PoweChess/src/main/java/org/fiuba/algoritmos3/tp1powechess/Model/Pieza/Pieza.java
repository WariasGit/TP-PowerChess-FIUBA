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
    private List<Poder> poderesAplicados;


    public Pieza(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.seHaMovido = false;
        this.tipoDeEnroque = new NoEnrocable();
        this.poderesAplicados = new ArrayList<>();

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

    ///METODOS PODERES////////
    public boolean esRey(Pieza pieza) {
        if (pieza.getTipoDePieza() == 'Rey') {
            return true;
        }
        return false;
    }

    public void aplicarPoder(Poder poder) {
        poderesAplicados.add(poder);
    }

    public void desactivarPoder(Poder poder) {
        poderesAplicados.remove(poder);
    }
    public boolean tienePoder(Poder poder) {
        return poderesAplicados.contains(poder);
    }

    //verifica si tiene freeze aplicado
    public boolean sePuedeMover() {
        for (Poder poder : poderesAplicados) {
            if (poder instanceof Freeze) {
                return false;
            }
        }
        return true;
    }

    //verifica si tiene escudo aplicado
    public boolean sePuedeComer() {
        for (Poder poder : poderesAplicados) {
            if (poder instanceof Escudo) {
                return false;
            }
        }
        return true;
    }


}
