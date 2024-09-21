package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.Optional;

public class TableroCuadrado {
    private Casillero[][] tablero;
    static private Integer dimensiones = 8;

    public TableroCuadrado() {
        tablero = new Casillero[dimensiones][dimensiones];
        inicializarTableroAjedrez();
    }

    private void inicializarTableroAjedrez(){
        for (int fila = 0; fila < dimensiones; fila++) {
            for (int columna = 0; columna < dimensiones; columna++) {
                Configuracion.ColoresJugadores color = (fila + columna) % 2 == 0 ? Configuracion.ColoresJugadores.BLANCO : Configuracion.ColoresJugadores.NEGRO;
                  tablero[fila][columna] = new Casillero(color);
            }
        }
    }

    public Casillero getCasillero(int row, int col) {
        return tablero[row][col];
    }

    public void setPieza(int row, int col, Pieza pieza) {
        tablero[row][col].setPieza(pieza);
    }

    public void removerPieza(int fila, int columna) {tablero[fila][columna].removerPieza();}

    public Integer getDimension() {
        return dimensiones;
    }

    public Optional<Pieza> getPieza(Integer i, Integer j) {
        return Optional.ofNullable(tablero[i][j].getPieza());
    }


    public boolean casilleroLibre(Integer i, Integer j) {
        return getPieza(i,j).isEmpty();
    }
}
