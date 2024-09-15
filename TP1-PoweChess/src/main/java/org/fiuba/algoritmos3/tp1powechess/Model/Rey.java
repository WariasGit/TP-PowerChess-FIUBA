package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Rey implements TipoDePieza {

    public String getTipoDePieza() {
        return "Rey";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = Math.abs(finX - inicioX);
        int difY = Math.abs(finY - inicioY);

        return (difX <= 1 && difY <= 1);
    }

    public ArrayList<int[]> getDireccionesDeAmenaza() {
        ArrayList<int[]> direcciones = new ArrayList<>();

        direcciones.add(new int[] {1, 0});
        direcciones.add(new int[] {-1, 0});
        direcciones.add(new int[] {0, 1});
        direcciones.add(new int[] {0, -1});
        direcciones.add(new int[] {1, 1});
        direcciones.add(new int[] {-1, 1});
        direcciones.add(new int[] {1, -1});
        direcciones.add(new int[] {-1, -1});

        return direcciones;
    }

    @Override
    public int getMaxDistanciaDeAmenaza() {
        return 1;
    }
}