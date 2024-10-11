package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Alfil extends Pieza {

    public Alfil(Configuracion.ColoresJugadores color) {
        super(color);
        this.tipoDePieza = Constantes.ALFIL;
        this.maxDistanciaDeAmenaza = Constantes.MAXIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();
        this.valor = Configuracion.ValorPiezas.VALOR_ALFIL;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.ALFIL_BLANCO, Configuracion.CaracterFenParaPiezas.ALFIL_NEGRO);
        // Inicializamos las direcciones de movimiento
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Diagonal derecha arriba
        direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Diagonal izquierda arriba
        direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});  // Diagonal derecha abajo
        direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA}); // Diagonal izquierda abajo

        // Para el Alfil, las direcciones de movimiento y de amenaza son las mismas
        direccionesDeAmenaza = new ArrayList<>(direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return "Alfil";
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
            if (direccion[0] != 0 && direccion[1] != 0 && difX * direccion[1] == difY * direccion[0]) {  // Movimiento diagonal proporcional
                return true;
            }
        }
        return false;
    }
}
