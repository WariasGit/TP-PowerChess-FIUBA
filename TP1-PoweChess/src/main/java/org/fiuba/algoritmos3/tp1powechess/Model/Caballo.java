package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Caballo implements TipoDePieza {

    public String getTipoDePieza() {
        return "Caballo";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = Math.abs(finX - inicioX);
        int difY = Math.abs(finY - inicioY);

        return (difX == 2 && difY == 1) || (difX == 1 && difY == 2);
    }

    public ArrayList<int[]> getDireccionesDeAmenaza() {
        ArrayList<int[]> direcciones = new ArrayList<>();

        // 8 movimientos posibles del caballo
        direcciones.add(new int[] {2, 1});
        direcciones.add(new int[] {2, -1});
        direcciones.add(new int[] {-2, 1});
        direcciones.add(new int[] {-2, -1});
        direcciones.add(new int[] {1, 2});
        direcciones.add(new int[] {1, -2});
        direcciones.add(new int[] {-1, 2});
        direcciones.add(new int[] {-1, -2});

        return direcciones;
    }

    public int getMaxDistanciaDeAmenaza() {
        return 1;
    }
}
