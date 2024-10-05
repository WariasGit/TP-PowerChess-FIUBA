package org.fiuba.algoritmos3.tp1powechess.Model.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Model.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public abstract class PeonBase extends Pieza {

    public PeonBase(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.PEON;

        this.maxDistanciaDeAmenaza = 1;

        // Definimos las direcciones de movimiento del peón
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{getDireccion(), 0});   // Movimiento hacia adelante
        direccionesDeMovimiento.add(new int[]{2 * getDireccion(), 0}); // Movimiento inicial doble

        // Definimos las direcciones de amenaza
        direccionesDeAmenaza = new ArrayList<>();
        direccionesDeAmenaza.add(new int[]{1, getDireccion()});  // Captura diagonal derecha
        direccionesDeAmenaza.add(new int[]{-1, getDireccion()}); // Captura diagonal izquierda
    }

    public String getTipoDePieza() {
        return "Peon";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las direcciones de movimiento permitidas
        if (esDireccionDeMovimientoValida(difX, difY)) {
            this.seHaMovido = true;  // Si el movimiento es válido, marcamos que el peón se ha movido
            return true;
        }
        return false;
    }

    public boolean esCapturaValida(int inicioX, int inicioY, int finX, int finY) {
        for (int[] direccion : direccionesDeAmenaza) {
            Amenaza amenaza = new Amenaza(color, direccion, getMaxDistanciaDeAmenaza());
            if (amenaza.coordenadasEnDireccionAmenazada(inicioX, inicioY, finX, finY)) {
                return true;
            }
        }
        return false;
    }

    // Meetodo para verificar si una dirección está en las direcciones de movimiento permitidas
    protected boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            // Solo debe moverse hacia adelante (sin cambiar la X)
            if (direccion[0] == difX && direccion[1] == difY) {
                // El peón puede moverse 1 o 2 casillas adelante solo si no ha sido movido
                return (!this.seHaMovido || direccion[1] != 2 * getDireccion());
            }
        }
        return false;
    }

    // Metodo abstracto para obtener la dirección de movimiento del peón (positivo o negativo según el color)
    protected abstract int getDireccion();
}