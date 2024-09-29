package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private String nombre;
    private Configuracion.EstadoJugador estado

    public Jugador(Configuracion.ColoresJugadores color, String nombre) {
        this.color = color;
        this.nombre = nombre;
    }

    public void rendirse() {
        this.estado = RENDIDO;
    }
    public void getEstado() {
        return this.estado;
    }
    public void ofrecerTablas() {}

    public boolean aceptarTablas() {}

    public void setNombre(string nombre) {
        this.nombre = nombre;
    }

    public void setColor(Configuracion.ColoresJugadores color) {
        this.color = color;
    }

    public void setOponente(Jugador oponente) { this.oponente = oponente; }
}


