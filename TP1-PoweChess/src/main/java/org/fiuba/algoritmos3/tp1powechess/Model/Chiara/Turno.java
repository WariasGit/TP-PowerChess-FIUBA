package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;


public class Turno {
    private List<Jugador> jugadores;
    private int turnoActual;

    public Turno(List<Jugadores> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = ;
    }

    public void gestionarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public Jugador getTurno() {
        return jugadores.get(turnoActual);
    }

    public Jugador getOponente() { return jugadores.get(turnoActual-1)}
}