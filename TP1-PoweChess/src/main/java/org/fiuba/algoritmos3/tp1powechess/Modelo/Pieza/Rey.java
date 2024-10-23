package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable.Enrocable;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoRey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Rey extends Pieza implements Enrocable {
    protected Casillero casilleroActual;
    private ArrayList<int[]> movimientosAmenazadosJaque;

    public Rey(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.REY;
        this.maxDistanciaDeAmenaza = Constantes.MINIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();
        this.valor = Configuracion.ValorPiezas.VALOR_REY;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.REY_BLANCO, Configuracion.CaracterFenParaPiezas.REY_NEGRO);
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
        this.movimientosAmenazadosJaque = new ArrayList<>();
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
        tableroCuadrado.setPieza(new Coordenada2D(row,6),this);
    }

    public void enrocarSegunEnroqueIzquierdo(TableroCuadrado tableroCuadrado,int row) {
        tableroCuadrado.setPieza(new Coordenada2D(row,2),this);
    }

    public void actualizarCasilleroActual(Casillero casillero) {
        this.casilleroActual = casillero;
    }

    public boolean estaEnJaque(){return this.casilleroActual.estaAmenazadoPorColorDistinto(color);}

    public void quitarMovimientoPosible(int[] movimientoPosible) {
        this.movimientosPosibles.removeIf(movimiento ->
                movimiento[0] == movimientoPosible[0] && movimiento[1] == movimientoPosible[1]);
    }

    public void setMovimientoAmenazadoJaque(int[] movimiento) {this.movimientosAmenazadosJaque.add(movimiento);}

    public void limpiarListaMovimientosAmenazados(){this.movimientosAmenazadosJaque.clear();}

    public ArrayList<int[]> getMovimientosAmenazadosJaque() {return new ArrayList<>(this.movimientosAmenazadosJaque);}

    public Coordenada2D getPosicionActual() {return this.casilleroActual.getPosicion();}

}