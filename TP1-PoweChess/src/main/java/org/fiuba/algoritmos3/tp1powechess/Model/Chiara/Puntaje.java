package org.fiuba.algoritmos3.tp1powechess.Model;

public class Puntaje {
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Ganador ganador

    /*
    public GestorDePuntaje(Jugador jugadorBlanco, Jugador jugadorNegro) {
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
    }

     */

    public Jugador determinarGanador() {
        if (jugadorBlanco.getPuntaje() > jugadorNegro.getPuntaje()) {
            return jugadorBlanco;
        } else if (jugadorNegro.getPuntaje() > jugadorBlanco.getPuntaje()) {
            return jugadorNegro;
        } else {
            return null; // Empate
        }
    }
    public void actualizarPuntaje(Jugador jugador, int puntaje) {
        jugador.setPuntaje(puntaje);
    }
}
