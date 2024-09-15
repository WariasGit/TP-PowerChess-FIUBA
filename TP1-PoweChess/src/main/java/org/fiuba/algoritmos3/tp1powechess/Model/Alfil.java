package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Alfil implements TipoDePieza {

    @Override
    public String getTipoDePieza() {
        return "Alfil";
    }

    @Override
    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = Math.abs(finX - inicioX);
        int difY = Math.abs(finY - inicioY);

        return difX == difY;
    }

    @Override
    public ArrayList<int[]> getDireccionesDeAmenaza() {
        ArrayList<int[]> direcciones = new ArrayList<>();

        // Direcciones diagonales
        direcciones.add(new int[] {1, 1});
        direcciones.add(new int[] {-1, 1});
        direcciones.add(new int[] {1, -1});
        direcciones.add(new int[] {-1, -1});

        return direcciones;
    }

    @Override
    public int getMaxDistanciaDeAmenaza() {
        return Integer.MAX_VALUE;
    }
}
