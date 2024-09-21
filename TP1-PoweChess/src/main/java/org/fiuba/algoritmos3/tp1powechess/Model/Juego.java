package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Model.Turno;

public class Juego {
    private Configuracion.EstadoJuego estado;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    //private Puntaje gestionarPuntaje;
    private Turno turno;
    private Jugador ganador;
    private TableroCuadrado tablero;


    public Juego(Jugador jugadorBlanco, Jugador jugadorNegro) {
        this.estado = Configuracion.EstadoJuego.ACTIVO;
        turno = new Turno();
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
    }

    public void establecerTablas() {
        this.estado = Configuracion.EstadoJuego.TABLAS;
        this.ganador = null;
    }

    /*
    public void  finalizarJuego() {
        if (this.estado == Configuracion.EstadoJuego.TABLAS) {
            //empate
        }
        this.estado = Configuracion.EstadoJuego.FINALIZADO;
        Jugador ganador = gestionarPuntaje.determinarGanador();
       //si es null, termina en empate
    }
    */

    public Jugador getJugadorBlancas() {
        return jugadorBlanco;
    }

    public Jugador getJugadorNegras() {
        return jugadorNegro;
    }

    //Esto hay que cambiarlo
    public Jugador getJugadorActual() {
        if(turno.getTurno() == "blanco"){
            return jugadorBlanco;
        }else {
            return jugadorNegro;
        }
    }

    public TableroCuadrado getTablero() {
        return tablero;
    }

    //Esto es provisorio, para probar algunas cosas
    public Boolean mover(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        Pieza pieza = tablero.getCasillero(origenFila, origenColumna).getPieza();
        tablero.setPieza(destinoFila, destinoColumna, pieza);
        tablero.removerPieza(origenFila, origenColumna);
        return true;
    }

    //Provisorio
    public void cambiarTurno() {
        turno.gestionarTurno();
    }
}

