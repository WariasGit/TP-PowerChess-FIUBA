package org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public interface EstrategiaDeMovimiento {
    public boolean esMovimientoValido(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado);
}
