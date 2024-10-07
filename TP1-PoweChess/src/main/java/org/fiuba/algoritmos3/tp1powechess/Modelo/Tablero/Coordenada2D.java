package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;

public class Coordenada2D {
    protected int[] coordenadas; // Array para almacenar x e y

    // Constructor
    public Coordenada2D(int row, int col) {
        this.coordenadas = new int[2];
        this.coordenadas[0] = row; // x es coordenadas[0]
        this.coordenadas[1] = col; // y es coordenadas[1]
    }

    // Getters
    public int getRow() {
        return coordenadas[0];
    }

    public int getCol() {
        return coordenadas[1];
    }

    // Setters
    public void setRow(int x) {
        this.coordenadas[0] = x;
    }

    public void setCol(int y) {
        this.coordenadas[1] = y;
    }

    // Método para calcular la diferencia entre dos coordenadas 2D
    public Coordenada2D calcularDiferenciaCon(Coordenada2D otra) {
        int diferenciaX = this.coordenadas[0] - otra.getRow();
        int diferenciaY = this.coordenadas[1] - otra.getCol();
        return new Coordenada2D(diferenciaX, diferenciaY);
    }

    // Método para comparar si dos coordenadas son iguales
    public boolean esIgual(Coordenada2D otra) {
        return this.coordenadas[0] == otra.getRow() && this.coordenadas[1] == otra.getCol();
    }
}
