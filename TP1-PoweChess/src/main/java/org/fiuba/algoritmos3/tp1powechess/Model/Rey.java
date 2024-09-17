package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

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

    public ArrayList<Amenaza> getAmenazasGeneradas(String color) {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int[][] direcciones = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},   // Direcciones horizontales y verticales
                {1, 1}, {-1, 1}, {1, -1}, {-1, -1}  // Direcciones diagonales
        };

        int maxDistancia = getMaxDistanciaDeAmenaza();

        for (int[] direccion : direcciones) {
            amenazas.add(new Amenaza(color, direccion, maxDistancia));
        }

        return amenazas;
    }

    @Override
    public int getMaxDistanciaDeAmenaza() {
        return 1;
    }
}