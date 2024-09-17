package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

public class TableroCuadrado {
    private Casillero[][] tablero;
    static private Integer dimensiones = 8;

    public TableroCuadrado() {
        tablero = new Casillero[dimensiones][dimensiones];
        inicializarTableroAjedrez();
    }

    private void inicializarTableroAjedrez(){
        for (int row = 0; row < dimensiones; row++) {
            for (int col = 0; col < dimensiones; col++) {
                  String color = (row + col) % 2 == 0 ? "Blanco" : "Negro";
                  tablero[row][col] = new Casillero(color);
            }
        }
    }

    public Casillero getCasillero(int row, int col) {
        return tablero[row][col];
    }

    public void setPieza(int row, int col, Pieza pieza) {
        tablero[row][col].setPieza(pieza);
    }
}
