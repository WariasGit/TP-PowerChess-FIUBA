package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Turno {
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean turnoDuplicado;


    public Turno(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = Configuracion.Jugadores.BLANCAS;
        this.turnoDuplicado = false;

    }

    public void gestionarTurno() {
        if (turnoDuplicado) { //DUPLICAR TURNO
            turnoDuplicado = false;
        } else {
            turnoActual = (turnoActual + 1) % jugadores.size();
        }

    public Jugador getTurno() {
        return jugadores.get(turnoActual);
    }

    public Jugador getOponente() {
        int oponenteIndex = (turnoActual - 1 + jugadores.size()) % jugadores.size();
        return jugadores.get(oponenteIndex);
    }

    public String getNombreOponente() {
        return getOponente().getNombre();
    }

    public String getNombreTurno() {
        return getTurno().getNombre();
    }

    public ArrayList<Pieza> getPiezasJugadorActual() {
        return getTurno().getPiezasEnJuego();
    }
    public Boolean estaEnJaqueJugadorActual() {
        return getTurno().estaEnJaque();
    }
    public void cambiarEstadoJugadorActual() {
        getTurno().cambiarEstadoJaque();
    }

    public Boolean tieneMovimientosJugadorActual() {
        return getTurno().tieneMovimientosPosibles();
    }
}
    public void duplicarTurno() {
        turnoDuplicado = true;
    }
}
