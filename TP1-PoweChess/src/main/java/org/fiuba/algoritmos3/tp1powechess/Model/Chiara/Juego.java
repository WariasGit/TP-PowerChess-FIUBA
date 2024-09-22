package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Model.Turno;

public class Juego {
    private EstadoJuego estado;
    private Puntaje gestionarPuntaje;
    private Ganador ganador;


    public Juego() {
        this.estado = EstadoJuego.ACTIVO;
        this.gestionarPuntaje = Puntaje new();
        this.ganador = null;
    }

    public void establecerTablas() {
        this.estado = EstadoJuego.TABLAS
        this.ganador = ganador.TALAS;
        this.finalizarJuego();
    }

    public finalizarJuego() {
        this.estado = EstadoJuego.FINALIZADO;
        Jugador ganador = gestionarPuntaje.determinarGanador();
       //si es null, termina en empate
    }
}

