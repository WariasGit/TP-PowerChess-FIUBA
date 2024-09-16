package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Torre implements TipoDePieza {

    public String getTipoDePieza() {
        return "Torre";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        return (inicioX == finX || inicioY == finY);
    }

    public ArrayList<Amenaza> getAmenazasGeneradas(String color,int inicioX, int inicioY) {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int[][] direcciones = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},   // Direcciones horizontales y verticales
        };

        // La Reina puede moverse a cualquier cantidad de casillas en línea recta
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