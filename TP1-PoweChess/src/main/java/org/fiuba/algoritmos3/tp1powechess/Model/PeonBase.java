package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public abstract class PeonBase implements TipoDePieza {
    private boolean fueMovido;  // Tracks whether the pawn has moved

    public PeonBase() {
        this.fueMovido = false;  // Initially, the pawn hasn't moved
    }

    public String getTipoDePieza() {
        return "Peon";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int dir = getDireccion();  // Definido en la subclase

        if (finX == inicioX && finY == inicioY + dir) {
            this.fueMovido = true;
            return true;
        }

        if (!fueMovido && finX == inicioX && finY == inicioY + 2 * dir) {
            this.fueMovido = true;
            return true;
        }

        if (Math.abs(finX - inicioX) == 1 && finY == inicioY + dir) {
            this.fueMovido = true;
            return true;
        }

        return false;
    }

    protected abstract int getDireccion();

    public ArrayList<Amenaza> getAmenazasGeneradas(String color) {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int dir = getDireccion();
        int[][] direcciones = {
                {1, dir}, {-1, dir}
        };

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