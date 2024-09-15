package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Torre implements TipoDePieza {

    @Override
    public String getTipoDePieza() {
        return "Torre";
    }

    @Override
    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        return (inicioX == finX || inicioY == finY);
    }

    @Override
    public ArrayList<int[]> getDireccionesDeAmenaza() {
        ArrayList<int[]> direcciones = new ArrayList<>();

        //Direcciones verticales y horizontales
        direcciones.add(new int[] {1, 0});
        direcciones.add(new int[] {-1, 0});
        direcciones.add(new int[] {0, 1});
        direcciones.add(new int[] {0, -1});

        return direcciones;
    }

    @Override
    public int getMaxDistanciaDeAmenaza() {
        return Integer.MAX_VALUE;
    }
}