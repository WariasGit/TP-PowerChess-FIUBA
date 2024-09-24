package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Rey implements TipoDePieza {

    private final ArrayList<int[]> direccionesDeMovimiento;
    private final ArrayList<int[]> direccionesDeAmenaza;

    public Rey() {
        // Inicializamos las direcciones de movimiento del Rey (una casilla en cualquier dirección)
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{1, 0});  // Derecha
        direccionesDeMovimiento.add(new int[]{-1, 0}); // Izquierda
        direccionesDeMovimiento.add(new int[]{0, 1});  // Arriba
        direccionesDeMovimiento.add(new int[]{0, -1}); // Abajo
        direccionesDeMovimiento.add(new int[]{1, 1});  // Diagonal derecha arriba
        direccionesDeMovimiento.add(new int[]{-1, 1}); // Diagonal izquierda arriba
        direccionesDeMovimiento.add(new int[]{1, -1}); // Diagonal derecha abajo
        direccionesDeMovimiento.add(new int[]{-1, -1}); // Diagonal izquierda abajo

        // Para el Rey, las direcciones de movimiento y de amenaza son las mismas
        direccionesDeAmenaza = new ArrayList<>(direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return "Rey";
    }

    public boolean movimientoEnDireccionDeMovimiento(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las permitidas
        return esDireccionDeMovimientoValida(difX, difY);
    }

    public boolean movimientoEnDireccionDeAmenaza(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las permitidas para las amenazas
        for (int[] direccion : direccionesDeAmenaza) {
            Amenaza amenaza = new Amenaza("color", direccion, getMaxDistanciaDeAmenaza());

            // Verificar si las coordenadas objetivo están dentro de la dirección y rango de amenaza
            if (amenaza.coordenadasEnDireccionAmenazada(inicioX, inicioY, finX, finY)) {
                // Verificar que la distancia sea válida (<= 1 casilla para el Rey)
                if (Math.abs(difX) == Math.abs(direccion[0]) && Math.abs(difY) == Math.abs(direccion[1])) {
                    return true;
                }
            }
        }
        return false;
    }


    public ArrayList<Amenaza> getAmenazasGeneradas(String color) {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int maxDistancia = getMaxDistanciaDeAmenaza();

        // Generar amenazas en todas las direcciones de amenaza
        for (int[] direccion : direccionesDeAmenaza) {
            amenazas.add(new Amenaza(color, direccion, maxDistancia));
        }

        return amenazas;
    }


    public int getMaxDistanciaDeAmenaza() {
        return 1;  // El Rey solo puede amenazar en casillas adyacentes
    }


    public ArrayList<int[]> getDireccionesDeMovimiento() {
        return new ArrayList<>(direccionesDeMovimiento);
    }

    // Método privado que verifica si la dirección del movimiento es válida
    private boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            if (direccion[0] == difX && direccion[1] == difY) {
                return true;
            }
        }
        return false;
    }
}