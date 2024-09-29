package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Alfil implements TipoDePieza {

    public String getTipoDePieza() {
        return "Alfil";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = Math.abs(finX - inicioX);
        int difY = Math.abs(finY - inicioY);

        return difX == difY;
    }

    public ArrayList<Amenaza> getAmenazasGeneradas(String color) {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int[][] direcciones = {
                {1, 1}, {-1, 1}, {1, -1}, {-1, -1}  // Direcciones diagonales
        };

        int maxDistancia = getMaxDistanciaDeAmenaza();

        for (int[] direccion : direcciones) {
            amenazas.add(new Amenaza(color, direccion, maxDistancia));
        }

        return amenazas;
    }

    public int getMaxDistanciaDeAmenaza() {
        return Integer.MAX_VALUE;
    }
}
