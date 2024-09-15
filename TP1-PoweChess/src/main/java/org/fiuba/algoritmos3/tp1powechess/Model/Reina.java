package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Reina implements TipoDePieza {

    public String getTipoDePieza() {
        return "Reina";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = Math.abs(finX - inicioX);
        int difY = Math.abs(finY - inicioY);

        return (inicioX == finX || inicioY == finY || difX == difY);
    }

    public ArrayList<int[]> getDireccionesDeAmenaza() {
        ArrayList<int[]> direcciones = new ArrayList<>();

        //Direcciones verticales y horizontales
        direcciones.add(new int[] {1, 0});
        direcciones.add(new int[] {-1, 0});
        direcciones.add(new int[] {0, 1});
        direcciones.add(new int[] {0, -1});

        //Direcciones diagonales
        direcciones.add(new int[] {1, 1});
        direcciones.add(new int[] {-1, 1});
        direcciones.add(new int[] {1, -1});
        direcciones.add(new int[] {-1, -1});

        return direcciones;
    }

    public int getMaxDistanciaDeAmenaza() {
        return Integer.MAX_VALUE;
    }
}