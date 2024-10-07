package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable.Enrocable;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoRey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Rey extends Pieza implements Enrocable {

    public Rey(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.REY;
        this.maxDistanciaDeAmenaza = Constantes.MINIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();

        // Inicializamos las direcciones de movimiento
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Derecha
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Izquierda
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});   // Arriba
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});  // Abajo
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Diagonal derecha arriba
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Diagonal izquierda arriba
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});  // Diagonal derecha abajo
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA}); // Diagonal izquierda abajo

        // Para el Rey, las direcciones de movimiento y de amenaza son las mismas
        direccionesDeAmenaza = new ArrayList<>(direccionesDeMovimiento);

        this.estrategiaDeMovimiento = new MovimientoRey();
    }

    public String getTipoDePieza() {
        return "Rey";
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
    public boolean esDireccionDeMovimientoValida(int difX, int difY) {
        for (int[] direccion : direccionesDeMovimiento) {
            if (direccion[0] == difX && direccion[1] == difY) {
                return true;
            }
        }
        return false;
    }

    public void enrocarSegunEnroqueDerecho(TableroCuadrado tableroCuadrado,int row) {
        tableroCuadrado.setPieza(row,6,this);
    }

    public void enrocarSegunEnroqueIzquierdo(TableroCuadrado tableroCuadrado,int row) {
        tableroCuadrado.setPieza(row,2,this);
    }
}