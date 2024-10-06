package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import java.util.List;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Turno {
    private List<Jugador> jugadores;
    private int turnoActual;

    public Turno(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = Configuracion.Jugadores.BLANCAS;
    }

    public void gestionarTurno() {
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
}