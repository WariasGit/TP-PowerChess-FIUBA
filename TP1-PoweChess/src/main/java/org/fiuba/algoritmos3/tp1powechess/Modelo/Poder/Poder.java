package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.General.GestorPoderes;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public abstract class Poder {
    protected String nombre;
    protected int duracion; 
    protected Configuracion.CategoriaPoder categoria;
    protected Configuracion.TipoPoder tipo;
    protected Configuracion.AplicacionPoder aplicacion;
    protected GestorPoderes gestorPoderes;

    public Poder(String nombre, int duracion, Configuracion.CategoriaPoder categoria, Configuracion.TipoPoder tipo, Configuracion.AplicacionPoder aplicacion) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.categoria = categoria;
        this.tipo = tipo;
        this.aplicacion = aplicacion;
    }
    public abstract String accionarPoder(Pieza pieza);

    public Configuracion.AplicacionPoder getTipoPiezaAplicable() {
        return this.aplicacion;
    }

    public Configuracion.TipoPoder getTipo()  {
        return this.tipo;
    };

    public String getNombre() {
        return this.nombre;
    }

    public String reducirDuracionoDesactivar(Pieza pieza) {
        if (this.duracion > 0) {
            this.duracion--;
        }

        // Desactivar si la duración es 0
        if (this.duracion == 0) {
            return pieza.desactivarPoder(); // Llama a desactivarPoder
        }

        return null; // El poder sigue activo
    }

}
