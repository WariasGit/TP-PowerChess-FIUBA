package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;
import java.util.Objects;

public class Coordenada2D {
    protected int[] coordenadas;

    public Coordenada2D(int row, int col) {
        this.coordenadas = new int[2];
        this.coordenadas[0] = row;
        this.coordenadas[1] = col;
    }

    public int getRow() {
        return coordenadas[0];
    }

    public int getCol() {
        return coordenadas[1];
    }
}
