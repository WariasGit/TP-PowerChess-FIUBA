package org.fiuba.algoritmos3.tp1powechess.Model.Tablero;

public abstract class Coordenada {
    protected int[] indices;

    public Coordenada(int... indices) {
        this.indices = indices;
    }

    public int[] getIndices() {
        return indices;
    }

    public boolean esIgual(Coordenada otra) {
        if (otra == null || this.indices.length != otra.indices.length) {
            return false;
        }

        for (int i = 0; i < indices.length; i++) {
            if (this.indices[i] != otra.indices[i]) {
                return false;
            }
        }
        return true;
    }
}
