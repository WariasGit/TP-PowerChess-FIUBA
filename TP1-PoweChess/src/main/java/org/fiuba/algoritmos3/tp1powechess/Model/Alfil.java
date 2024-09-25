package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Alfil extends Pieza {

    public Alfil(String color) {

        super(color);

        this.maxDistanciaDeAmenaza = Integer.MAX_VALUE;

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

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las permitidas
        return esDireccionDeMovimientoValida(difX, difY);
    }

    public boolean esCapturaValida(int inicioX, int inicioY, int finX, int finY) {
        for (int[] direccion : direccionesDeAmenaza) {
            Amenaza amenaza = new Amenaza("color", direccion, getMaxDistanciaDeAmenaza());
            if (amenaza.coordenadasEnDireccionAmenazada(inicioX, inicioY, finX, finY)) {
                return true;
            }
        }
        return false;
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
