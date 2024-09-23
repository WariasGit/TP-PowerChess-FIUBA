package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public abstract class PeonBase implements TipoDePieza {
    private boolean fueMovido;  // Indica si el peón se ha movido

    private final ArrayList<int[]> direccionesDeMovimiento;
    private final ArrayList<int[]> direccionesDeAmenaza;

    public PeonBase() {
        this.fueMovido = false;  // Inicialmente, el peón no se ha movido

        // Definimos las direcciones de movimiento del peón
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{0, getDireccion()});   // Movimiento hacia adelante
        direccionesDeMovimiento.add(new int[]{0, 2 * getDireccion()}); // Movimiento inicial doble

        // Definimos las direcciones de amenaza
        direccionesDeAmenaza = new ArrayList<>();
        direccionesDeAmenaza.add(new int[]{1, getDireccion()});  // Captura diagonal derecha
        direccionesDeAmenaza.add(new int[]{-1, getDireccion()}); // Captura diagonal izquierda
    }

    public String getTipoDePieza() {
        return "Peon";
    }

    public boolean movimientoEnDireccionDeMovimiento(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las direcciones de movimiento permitidas
        if (esDireccionDeMovimientoValida(difX, difY)) {
            this.fueMovido = true;  // Si el movimiento es válido, marcamos que el peón se ha movido
            return true;
        }
        return false;
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
        return 1;  // El peón solo puede amenazar en una casilla diagonal
    }

    public ArrayList<int[]> getDireccionesDeMovimiento() {
        return direccionesDeMovimiento;
    }

    // Meetodo para verificar si una dirección está en las direcciones de movimiento permitidas
    private boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            // Solo debe moverse hacia adelante (sin cambiar la X)
            if (direccion[0] == difX && direccion[1] == difY) {
                // El peón puede moverse 1 o 2 casillas adelante solo si no ha sido movido
                return (!fueMovido || direccion[1] != 2 * getDireccion());
            }
        }
        return false;
    }

    // Metodo abstracto para obtener la dirección de movimiento del peón (positivo o negativo según el color)
    protected abstract int getDireccion();
}