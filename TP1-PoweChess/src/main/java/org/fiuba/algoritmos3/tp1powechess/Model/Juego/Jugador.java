package org.fiuba.algoritmos3.tp1powechess.Model.Juego;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza.*;
import java.util.List;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private String nombre;
    private Configuracion.EstadoJugador estado;
    private List<Pieza> piezasEnJuego;
    private List<Pieza> piezasPerdidas;

    public Jugador(Configuracion.ColoresJugadores color, String nombre) {
        this.color = color;
        this.nombre = nombre;
        this.estado = null;
        this.piezasEnJuego = null;
        this.piezasPerdidas = null;
    }

    public void rendirse() {
        this.estado = Configuracion.EstadoJugador.RENDIDO;
    }

    public boolean estaRendido() {
        return this.estado == Configuracion.EstadoJugador.RENDIDO;
    }

    /*
    public boolean OfrecioTablas() {
        return this.estado == Configuracion.EstadoJuego.TABLAS;
    }

    public void ofrecerTablas() {
        this.estado = Configuracion.EstadoJuego.TABLAS;
    }

     */

    public void setPiezasEnJuego(Pieza pieza) {
        if (!this.piezasEnJuego.contains(pieza)) {
            piezasEnJuego.add(pieza);
        }
    }

    public void setPiezasPerdidas(Pieza pieza) {
             piezasPerdidas.add(pieza);
             piezasEnJuego.remove(pieza);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setColor(Configuracion.ColoresJugadores color) {
        this.color = color;
    }

    public void setEstado(Configuracion.EstadoJugador estado) { this.estado = estado; }

    public Configuracion.EstadoJugador getEstado() { return this.estado; }

    public int getPiezasEnJuego() { return this.piezasEnJuego.size(); }

    public String getNombre() { return this.nombre; }
    public Configuracion.ColoresJugadores getColor() { return this.color; }
}


