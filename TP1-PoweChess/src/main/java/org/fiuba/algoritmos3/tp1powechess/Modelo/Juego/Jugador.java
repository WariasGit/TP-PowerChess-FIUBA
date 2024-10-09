package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;

import java.util.HashMap;
import java.util.Map;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private String nombre;
    Map<Coordenada2D, Pieza> piezasEnJuego;

    public Jugador(Configuracion.ColoresJugadores color, String nombre) {
        this.color = color;
        this.nombre = nombre;
        piezasEnJuego = new HashMap<>();
    }

    public void setPiezasEnJuego(Coordenada2D posicion , Pieza pieza) { piezasEnJuego.put(posicion, pieza); }

    public Map<Coordenada2D, Pieza> getPiezasEnJuego() {return piezasEnJuego;}

    public String getNombre() { return this.nombre; }
    public Configuracion.ColoresJugadores getColor() { return this.color; }
}


