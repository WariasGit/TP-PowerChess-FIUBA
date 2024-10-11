package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable.Enrocable;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable.NoEnrocable;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return new ArrayList<int[]>(direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return tipoDePieza;
    }

    public void agregarMovimiento(int difX, int difY) {
        direccionesDeMovimiento.add(new int[]{difX, difY});
    }

    public void eliminarMovimiento(int difX, int difY) {
        direccionesDeMovimiento.remove(new int[]{difX, difY});
    }


    ///METODOS PODERES////////
    public boolean esRey() {
        if (Objects.equals(this.getTipoDePieza(), "Rey")) {
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

    public boolean puedeVolar() {
        for (Poder poder : poderesAplicados) {
            if (poder instanceof Vuelo) {
                return true;
            }
        }
        return false;
    }

    //verifica si tiene freeze aplicado
    public boolean tieneFreeze() {
        for (Poder poder : poderesAplicados) {
            if (poder instanceof Freeze) {
                return false;
            }
        }
        return true;
    }

    //verifica si tiene escudo aplicado
    public boolean tieneEscudo() {
        for (Poder poder : poderesAplicados) {
            if (poder instanceof Escudo) {
                return false;
            }
        }
        return true;
    }

    public boolean tieneDobleTurno() {
        for (Poder poder : poderesAplicados) {
            if (poder instanceof DobleJuego) {
                return true;
            }
        }
        return false;
    }

    public void setMovimientoDoble(boolean b) {
        //Lo puse para solucionar errores
    }
}

