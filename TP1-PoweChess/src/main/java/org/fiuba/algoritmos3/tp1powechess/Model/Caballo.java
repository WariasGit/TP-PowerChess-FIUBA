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

    public ArrayList<Amenaza> getAmenazasGeneradas(String color,int inicioX, int inicioY) {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int[][] direcciones = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        // La Reina puede moverse a cualquier cantidad de casillas en línea recta
        int maxDistancia = getMaxDistanciaDeAmenaza();

        for (int[] direccion : direcciones) {
            amenazas.add(new Amenaza(color, direccion, maxDistancia));
        }

        return amenazas;
    }

    public int getMaxDistanciaDeAmenaza() {
        return 1;
    }
}
