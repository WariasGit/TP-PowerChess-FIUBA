package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Torre extends Pieza {

    public Torre(String color) {

        super(color);

        this.maxDistanciaDeAmenaza = Integer.MAX_VALUE;

        // Inicializamos las direcciones de movimiento
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{1, 0});   // Derecha
        this.direccionesDeMovimiento.add(new int[]{-1, 0});  // Izquierda
        this.direccionesDeMovimiento.add(new int[]{0, 1});   // Arriba
        this.direccionesDeMovimiento.add(new int[]{0, -1});  // Abajo

        // Para la Torre, las direcciones de movimiento y de amenaza son las mismas
        this.direccionesDeAmenaza = new ArrayList<>(this.direccionesDeMovimiento);

        // Al crear una Torre le asignamos su estrategia de enroque
        this.tipoDeEnroque = new SiEnrocable();
    }

    public String getTipoDePieza() {
        return "Torre";
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
            if ((direccion[0] == 0 && difX == 0 && difY * direccion[1] > 0) ||  // Movimiento vertical
                    (direccion[1] == 0 && difY == 0 && difX * direccion[0] > 0)) {  // Movimiento horizontal
                return true;
            }
        }
        return false;
    }
}