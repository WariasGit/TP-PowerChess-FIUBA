package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import java.util.ArrayList;

public class Reina extends Pieza {

    public Reina(Configuracion.ColoresJugadores color) {
        super(color);
        this.tipoDePieza = Constantes.REINA;
        this.maxDistanciaDeAmenaza = Constantes.MAXIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();
        this.valor = Configuracion.ValorPiezas.VALOR_REINA;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.REINA_BLANCA, Configuracion.CaracterFenParaPiezas.REINA_NEGRA);
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

        // Para la Reina, las direcciones de movimiento y de amenaza son las mismas
        this.direccionesDeAmenaza = new ArrayList<>(this.direccionesDeMovimiento);
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
    public boolean esDireccionDeMovimientoValida(int difX, int difY) {
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