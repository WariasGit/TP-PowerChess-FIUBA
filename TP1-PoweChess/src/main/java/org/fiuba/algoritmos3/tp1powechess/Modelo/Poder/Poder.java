package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public abstract class Poder {
    protected String nombre;
    protected int duracion; 
    protected Configuracion.CategoriaPoder categoria;
    protected Configuracion.TipoPoder tipo;
    protected Configuracion.AplicacionPoder aplicacion;

    public Poder(String nombre, int duracion, Configuracion.CategoriaPoder categoria, Configuracion.TipoPoder tipo, Configuracion.AplicacionPoder aplicacion) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.categoria = categoria;
        this.tipo = null;
        this.aplicacion = aplicacion;
    }

    //no se si es necesario verificar la categoria
    public void reducirDuracionoDesactivar(Pieza pieza) {
        if (this.categoria == Configuracion.CategoriaPoder.DURACION && this.duracion > 0) {
            this.duracion--;
        }
        else {
            pieza.desactivarPoder();
        }
    }

    public void aplicarPoder(Pieza pieza) {
        pieza.setPoder(this);
    }

    public abstract Configuracion.AplicacionPoder getTipoPiezaAplicable();

    public Configuracion.TipoPoder getTipo()  {
        return this.tipo;
    };




    public boolean esDeDuracion() {
        return this.categoria == Configuracion.CategoriaPoder.DURACION;
    }

    public boolean esDeAccion() {
        return this.categoria == Configuracion.CategoriaPoder.ACCION;
    }

    public boolean esDeEvolucion() {
        return this.categoria == Configuracion.CategoriaPoder.EVOLUCION;
    }


    public boolean estaActivo() {
        return this.duracion > 0 || this.categoria == Configuracion.CategoriaPoder.ACCION;
    }

    public Configuracion.CategoriaPoder getCategoria() {
        return this.categoria;
    }

    public String getNombre() {
        return this.nombre;
    }
}
