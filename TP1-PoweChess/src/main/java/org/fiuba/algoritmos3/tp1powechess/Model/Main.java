package org.fiuba.algoritmos3.tp1powechess.Model;
import java.util.List;

public class Main {

    //Pongo el blanco primero en la lista
    List<Jugador> jugadores = Arrays.asList(jugadorBlanco, jugadorNegro);
    jugadores.sort((j1, j2) -> j1.getColor().equals("blanco") ? -1 : 1);
    //Turno turno = new Turno(jugadores);
}

