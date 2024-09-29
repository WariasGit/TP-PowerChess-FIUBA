package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import java.util.List;

public class Turno {
    private List<Jugador> jugadores;
    private int turnoActual;

    public Turno(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = 0;
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
