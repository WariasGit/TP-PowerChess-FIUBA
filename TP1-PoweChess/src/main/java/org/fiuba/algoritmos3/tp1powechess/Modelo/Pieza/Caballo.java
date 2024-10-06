package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Caballo extends Pieza {

    public Caballo(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.CABALLO;
        this.maxDistanciaDeAmenaza = Constantes.MINIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();

        // Inicializamos las direcciones de movimiento (movimiento en "L")
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{Constantes.DOS_EN_FILA, Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{Constantes.DOS_EN_FILA, -Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.DOS_EN_FILA, Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.DOS_EN_FILA, -Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.DOS_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, -Constantes.DOS_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.DOS_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, -Constantes.DOS_EN_COLUMNA});

        // Inicializamos las direcciones de amenaza (puede ser distinto en el futuro)
        this.direccionesDeAmenaza = new ArrayList<>(this.direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return "Caballo";
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        int difX = Math.abs(finX - inicioX);
        int difY = Math.abs(finY - inicioY);

        // Verificamos si la dirección está entre las permitidas
        return esDireccionDeMovimientoValida(difX, difY);
    }

    public boolean esCapturaValida(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        // Verificar si alguna dirección de amenaza es válida para el movimiento actual
        return esAmenazaValida(inicioX, inicioY, finX, finY);
    }

    // Metodo privado que verifica si la dirección del movimiento es válida
    private boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : this.direccionesDeMovimiento) {
            if (direccion[0] == difX && direccion[1] == difY) {
                return true;
            }
        }
        return false;
    }

    private boolean esAmenazaValida(int inicioX, int inicioY, int finX, int finY) {
        int difX = finX - inicioX;
        int difY = finY - inicioY;

        for (int[] direccion : this.direccionesDeAmenaza) {
            // Crear la amenaza en la dirección dada
            Amenaza amenaza = new Amenaza(color, direccion, getMaxDistanciaDeAmenaza());

            // Verificar si las coordenadas objetivo están dentro de la dirección y rango de amenaza
            if (amenaza.coordenadasEnDireccionAmenazada(inicioX, inicioY, finX, finY)) {
                // Verificar si la dirección de amenaza es válida para este movimiento
                if (Math.abs(difX) == Math.abs(direccion[0]) && Math.abs(difY) == Math.abs(direccion[1])) {
                        return true;
                }
            }
        }
        return false;
    }
}
