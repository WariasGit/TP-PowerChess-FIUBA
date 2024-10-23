package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.Objects;

public abstract class Poder {
    protected String nombre;
    protected int duracion; 
    protected Configuracion.CategoriaPoder categoria;
    protected Configuracion.TipoPoder tipo;

    public Poder(String nombre, int duracion, Configuracion.CategoriaPoder categoria, Configuracion.TipoPoder tipo) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.categoria = categoria;
        this.tipo = null;
    }

    public void aplicar(Pieza pieza) {
        pieza.aplicarPoder(this);
    }

    public void desactivar(Pieza pieza)  {
        pieza.desactivarPoder(this);
    }

    public abstract Configuracion.TipoPoder getTipo();

    public boolean esDeDuracion() {
        return this.categoria == Configuracion.CategoriaPoder.DURACION;
    }

    public boolean esDeAccion() {
        return this.categoria == Configuracion.CategoriaPoder.ACCION;
    }

    public boolean esDeEvolucion() {
        return this.categoria == Configuracion.CategoriaPoder.EVOLUCION;
    }

    public boolean reducirDuracion() {
        if (this.categoria == Configuracion.CategoriaPoder.DURACION && this.duracion > 0) {
            this.duracion--;
            return true;
        }
        return false;
    }

    public boolean estaActivo() {
        return this.duracion > 0 || this.categoria == Configuracion.CategoriaPoder.ACCION;
    }

    public Configuracion.CategoriaPoder getCategoria() {
        return this.categoria;
    }

    public boolean esRey(Pieza pieza) {
        return Objects.equals(pieza.getTipoDePieza(), Constantes.REY);
    }

    public String getNombre() {
        return this.nombre;
    }
}
