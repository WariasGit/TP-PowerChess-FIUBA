package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public abstract class Poder {
    protected String nombre;
    protected int duracion; 
    protected Configuracion.CategoriaPoder categoria;

    public Poder(String nombre, int duracion, Configuracion.CategoriaPoder categoria) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.categoria = categoria;
    }

    public boolean esDeDuracion() {
        return this.categoria == Configuracion.CategoriaPoder.DURACION;
    }

    public boolean esDeAccion() {
        return this.categoria == Configuracion.CategoriaPoder.ACCION;
    }

    public boolean esDeEvolucion() {
        return this.categoria == Configuracion.CategoriaPoder.EVOLUCION;
    }

    public void reducirDuracion() {
        if (this.categoria == Configuracion.CategoriaPoder.DURACION && this.duracion > 0) {
            this.duracion--;
        }
    }

    public boolean estaActivo() {
        return this.duracion > 0 || this.categoria == Configuracion.CategoriaPoder.ACCION;
    }

    public Configuracion.CategoriaPoder getCategoria() {
        return this.categoria;
    }

    public abstract void aplicar(Jugador jugador, Jugador oponente);

    public abstract void aplicar(Pieza pieza);
}
