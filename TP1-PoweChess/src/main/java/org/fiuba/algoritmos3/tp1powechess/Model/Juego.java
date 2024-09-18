package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Model.Turno;

public class Juego {
    private EstadoJuego estado;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Puntaje gestionarPuntaje;


    public Juego() {
        this.estado = EstadoJuego.ACTIVO;
    }

    public void establecerTablas() {
        this.estado = EstadoJuego.TABLAS;
        this.ganador = null;
    }

    public finalizarJuego() {
        if (this.estado = EstadoJuego.TABLAS) {
            //empate
        }
        this.estado = EstadoJuego.FINALIZADO;
        Jugador ganador = gestionarPuntaje.determinarGanador();
       //si es null, termina en empate
    }
}

