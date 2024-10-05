package org.fiuba.algoritmos3.tp1powechess.Model.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Model.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Model.Enrocable.EnrocableSiNoSeHaMovido;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Rey extends Pieza {

    public Rey(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.REY;
        this.maxDistanciaDeAmenaza = 1;

        // Inicializamos las direcciones de movimiento del Rey (una casilla en cualquier dirección)
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{0, 1});   // Derecha
        this.direccionesDeMovimiento.add(new int[]{0, -1});  // Izquierda
        this.direccionesDeMovimiento.add(new int[]{-1, 0});   // Arriba
        this.direccionesDeMovimiento.add(new int[]{1, 0});  // Abajo
        direccionesDeMovimiento.add(new int[]{1, 1});  // Diagonal derecha arriba
        direccionesDeMovimiento.add(new int[]{-1, 1}); // Diagonal izquierda arriba
        direccionesDeMovimiento.add(new int[]{1, -1}); // Diagonal derecha abajo
        direccionesDeMovimiento.add(new int[]{-1, -1}); // Diagonal izquierda abajo

        // Para el Rey, las direcciones de movimiento y de amenaza son las mismas
        direccionesDeAmenaza = new ArrayList<>(direccionesDeMovimiento);

        this.tipoDeEnroque = new EnrocableSiNoSeHaMovido(this);
    }

    public String getTipoDePieza() {
        return "Rey";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las permitidas
        return esDireccionDeMovimientoValida(difX, difY);
    }

    public boolean esCapturaValida(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las permitidas para las amenazas
        for (int[] direccion : direccionesDeAmenaza) {
            Amenaza amenaza = new Amenaza(color, direccion, getMaxDistanciaDeAmenaza());

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

    // Metodo privado que verifica si la dirección del movimiento es válida
    private boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            if (direccion[0] == difX && direccion[1] == difY) {
                return true;
            }
        }
        return false;
    }
}