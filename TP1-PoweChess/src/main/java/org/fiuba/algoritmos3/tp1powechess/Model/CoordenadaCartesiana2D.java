package org.fiuba.algoritmos3.tp1powechess.Model;

public class CoordenadaCartesiana2D extends Coordenada {

    // Constructor que recibe las coordenadas X e Y
    public CoordenadaCartesiana2D(int x, int y) {
        super(x, y);
    }

    public int getX() {
        return indices[0]; // El índice 0 representa la coordenada X
    }

    public int getY() {
        return indices[1]; // El índice 1 representa la coordenada Y
    }

    public int[] calcularDiferencia(CoordenadaCartesiana2D otra) {

        // Calculamos la diferencia en X y en Y
        int diferenciaX = this.getX() - otra.getX();
        int diferenciaY = this.getY() - otra.getY();

        // Devolvemos la diferencia como un array
        return new int[]{diferenciaX, diferenciaY};
    }
}
