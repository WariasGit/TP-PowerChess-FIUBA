package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Alfil implements TipoDePieza {

    private final ArrayList<int[]> direccionesDeMovimiento;
    private final ArrayList<int[]> direccionesDeAmenaza;

    public Alfil() {
        // Inicializamos las direcciones de movimiento
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{1, 1});   // Diagonal derecha arriba
        direccionesDeMovimiento.add(new int[]{-1, 1});  // Diagonal izquierda arriba
        direccionesDeMovimiento.add(new int[]{1, -1});  // Diagonal derecha abajo
        direccionesDeMovimiento.add(new int[]{-1, -1}); // Diagonal izquierda abajo

        // Para el Alfil, las direcciones de movimiento y de amenaza son las mismas
        direccionesDeAmenaza = new ArrayList<>(direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return "Alfil";
    }

    public boolean movimientoEnDireccionDeMovimiento(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las permitidas
        return esDireccionDeMovimientoValida(difX, difY);
    }

    public boolean movimientoEnDireccionDeAmenaza(int inicioX, int inicioY, int finX, int finY) {
        for (int[] direccion : direccionesDeAmenaza) {
            Amenaza amenaza = new Amenaza("color", direccion, getMaxDistanciaDeAmenaza());
            if (amenaza.coordenadasEnDireccionAmenazada(inicioX, inicioY, finX, finY)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Amenaza> getAmenazasGeneradas(String color) {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int maxDistancia = getMaxDistanciaDeAmenaza();

        for (int[] direccion : direccionesDeAmenaza) {
            amenazas.add(new Amenaza(color, direccion, maxDistancia));
        }

        return amenazas;
    }

    public int getMaxDistanciaDeAmenaza() {
        return Integer.MAX_VALUE;  // La Reina no tiene límite de casilleros
    }

    public ArrayList<int[]> getDireccionesDeMovimiento() {
        return new ArrayList<int[]> (direccionesDeMovimiento);
    }

    // Nuevo metodo para verificar si una dirección está en las direcciones de movimiento permitidas
    private boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            if (direccion[0] != 0 && direccion[1] != 0 && difX * direccion[1] == difY * direccion[0]) {  // Movimiento diagonal proporcional
                return true;
            }
        }
        return false;
    }
}
