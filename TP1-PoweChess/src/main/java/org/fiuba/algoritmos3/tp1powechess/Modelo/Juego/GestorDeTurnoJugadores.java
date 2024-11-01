package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;
import java.util.List;

public class GestorDeTurnoJugadores {
    private final Turno turno;

    public GestorDeTurnoJugadores(List<Jugador> jugadores) {
        this.turno = new Turno(jugadores);
    }

    public void quitarPiezaComidaDelJugador(Pieza piezaComida) {
        if(piezaComida.getColor() == Configuracion.ColoresJugadores.BLANCO) {
            turno.quitarPiezaJugadorBlancas(piezaComida);
        } else {
            turno.quitarPiezaJugaddorNegras(piezaComida);
        }
    }

    public void guardarPiezaJugador(Pieza pieza) {
        if(pieza.getColor() == Configuracion.ColoresJugadores.BLANCO) {
            turno.guardarPiezaJugadorBlancas(pieza);
        } else {
            turno.guardarPiezaJugadorNegras(pieza);
        }
    }

    public List<Jugador> obtenerJugadores() {return turno.getJugadores();}

    public Jugador obtenerJugadorTurnoActual() {return turno.getTurno();}

    public void setNombreJugadorBlancas(String nombreJugador) {turno.setNombreJugadorBlancas(nombreJugador);}

    public void setNombreJugadorNegras(String nombreJugador){turno.setNOmbreJugadorNegras(nombreJugador);}

    public String obtenerNombreJugadorBlancas(){return turno.getNombreJugadorBlancas();}

    public String obtenerNombreJugadorNegras(){return turno.getNombreJugadorNegras();}

    public Boolean jaqueJugadorActual(){return obtenerJugadorTurnoActual().estaEnJaque();}

    public Boolean jugadorActualPuedeMoverEstaPieza(Pieza piezaActual){return turno.estaPiezaEsDelJugadorActual(piezaActual);}

    public void guardarReyes(ArrayList<Rey> reyes){turno.setReyes(reyes);}

    public Turno obtenerTurno() {return this.turno;}

    public String obtenerNombreJugadorActual(){return obtenerJugadorTurnoActual().getNombre();}

    public String obtenerNombreOponente(){return turno.getNombreOponente();}

    public void cambiarTurno(){turno.gestionarTurno();}

    public Rey obtenerReyJugadorActual(){return turno.getReyJugadorActual();}

    public Configuracion.ColoresJugadores obtenerColorJugadorActual(){return obtenerJugadorTurnoActual().getColor();}

    public Coordenada2D obtenerCoordenadasReyAmenazado(){return obtenerJugadorTurnoActual().getPosicionActualRey();}
}
