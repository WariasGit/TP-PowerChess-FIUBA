package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import java.util.ArrayList;
import java.util.List;

import org.fiuba.algoritmos3.tp1powechess.Modelo.General.GestorPoderes;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Rey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Turno {
    private List<Jugador> jugadores;
    private int turnoActual;
    private GestorPoderes gestorPoderes;

    public Turno(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = Configuracion.Jugadores.BLANCAS;
    }

    public void setReyes(ArrayList<Rey> reyes) {
        // Verificar que la cantidad de jugadores coincida con la cantidad de reyes
        if (jugadores.size() != reyes.size()) {
            throw new IllegalArgumentException("La cantidad de jugadores no coincide con la cantidad de reyes.");
        }
        // Asignar cada rey a su respectivo jugador, se espera que la lista de reyes venga en el mismo orden (por color).
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            Rey rey = reyes.get(i);
            jugador.setRey(rey);
        }
    }

    public Rey getReyJugadorActual() {return getTurno().getRey();}

    public void gestionarTurno() {
        gestorPoderes.verificarPoderesJugador(); // Delegación a GestorPoderes
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

    public Configuracion.ColoresJugadores getColorJugadorActual() {
        return getTurno().getColor();
    }

    public boolean estaPiezaEsDelJugadorActual(Pieza piezaAMover){return (piezaAMover.getColor() == getColorJugadorActual());}

    private Jugador getJugadorBlancas(){return jugadores.get(Configuracion.Jugadores.BLANCAS);}

    private Jugador getJugadorNegras(){return jugadores.get(Configuracion.Jugadores.NEGRAS);}

    public List<Jugador> getJugadores() {return this.jugadores;}

    public void setGestorPoderes(GestorPoderes gestorPoderes) {
        this.gestorPoderes = gestorPoderes;
    }

    public void quitarPiezaJugadorBlancas(Pieza piezaComida) {getJugadorBlancas().quitarPiezaEnJuego(piezaComida);}

    public void quitarPiezaJugaddorNegras(Pieza piezaComida) {getJugadorNegras().quitarPiezaEnJuego(piezaComida);}

    public void guardarPiezaJugadorBlancas(Pieza pieza) {getJugadorBlancas().setPiezasEnJuego(pieza);}

    public void guardarPiezaJugadorNegras(Pieza pieza) {getJugadorNegras().setPiezasEnJuego(pieza);}

    public void setNombreJugadorBlancas(String nombreJugador) {getJugadorBlancas().setNombre(nombreJugador);}

    public void setNOmbreJugadorNegras(String nombreJugador) {getJugadorNegras().setNombre(nombreJugador);}

    public String getNombreJugadorBlancas() {return getJugadorBlancas().getNombre();}

    public String getNombreJugadorNegras() {return getJugadorNegras().getNombre();}
}


