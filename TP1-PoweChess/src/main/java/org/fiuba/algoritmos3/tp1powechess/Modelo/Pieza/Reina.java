package org.fiuba.algoritmos3.tp1powechess.Model.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Model.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import java.util.ArrayList;

public class Reina extends Pieza {

    public Reina(Configuracion.ColoresJugadores color) {
        super(color);
        this.tipoDePieza = Constantes.REINA;
        this.maxDistanciaDeAmenaza = Integer.MAX_VALUE;

        // Inicializamos las direcciones de movimiento
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{0, 1});   // Derecha
        this.direccionesDeMovimiento.add(new int[]{0, -1});  // Izquierda
        this.direccionesDeMovimiento.add(new int[]{-1, 0});   // Arriba
        this.direccionesDeMovimiento.add(new int[]{1, 0});  // Abajo
        this.direccionesDeMovimiento.add(new int[]{-1, 1});   // Diagonal derecha arriba
        this.direccionesDeMovimiento.add(new int[]{-1, -1});  // Diagonal izquierda arriba
        this.direccionesDeMovimiento.add(new int[]{1, 1});  // Diagonal derecha abajo
        this.direccionesDeMovimiento.add(new int[]{1, -1}); // Diagonal izquierda abajo

        // Para la Reina, las direcciones de movimiento y de amenaza son las mismas
        this.direccionesDeAmenaza = new ArrayList<>(this.direccionesDeMovimiento);
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificamos si la dirección está entre las permitidas
        return esDireccionDeMovimientoValida(difX, difY);
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

    // Nuevo metodo para verificar si una dirección está en las direcciones de movimiento permitidas
    private boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            if ((direccion[0] == 0 && difX == 0 && difY * direccion[1] > 0) ||  // Movimiento vertical
                    (direccion[1] == 0 && difY == 0 && difX * direccion[0] > 0) ||  // Movimiento horizontal
                    (direccion[0] != 0 && direccion[1] != 0 && difX * direccion[1] == difY * direccion[0])) {  // Movimiento diagonal proporcional
                return true;
            }
        }
        return false;
    }
}