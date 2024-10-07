package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;

public class Coordenada2D {
    protected int[] coordenadas; // Array para almacenar x e y

    // Constructor
    public Coordenada2D(int x, int y) {
        this.coordenadas = new int[2];
        this.coordenadas[0] = x; // x es coordenadas[0]
        this.coordenadas[1] = y; // y es coordenadas[1]
    }

    // Getters
    public int getX() {
        return coordenadas[0];
    }

    public int getY() {
        return coordenadas[1];
    }

    // Setters
    public void setX(int x) {
        this.coordenadas[0] = x;
    }

    public void setY(int y) {
        this.coordenadas[1] = y;
    }

    // Método para calcular la diferencia entre dos coordenadas 2D
    public Coordenada2D calcularDiferenciaCon(Coordenada2D otra) {
        int diferenciaX = this.coordenadas[0] - otra.getX();
        int diferenciaY = this.coordenadas[1] - otra.getY();
        return new Coordenada2D(diferenciaX, diferenciaY);
    }

    // Método para comparar si dos coordenadas son iguales
    public boolean esIgual(Coordenada2D otra) {
        return this.coordenadas[0] == otra.getX() && this.coordenadas[1] == otra.getY();
    }
}
