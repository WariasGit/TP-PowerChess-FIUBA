package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable.EnrocableSiNoSeHaMovido;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoTorre;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;


import java.util.ArrayList;

public class Torre extends Pieza {

    public Torre(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.TORRE;
        this.maxDistanciaDeAmenaza = Constantes.MAXIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();

        // Inicializamos las direcciones de movimiento
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Derecha
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Izquierda
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});   // Arriba
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});  // Abajo

        // Para la Torre, las direcciones de movimiento y de amenaza son las mismas
        this.direccionesDeAmenaza = new ArrayList<>(this.direccionesDeMovimiento);

        // Al crear una Torre le asignamos su estrategia de enroque
        this.estrategiaDeMovimiento = new MovimientoTorre();
    }

    public String getTipoDePieza() {
        return "Torre";
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
                    (direccion[1] == 0 && difY == 0 && difX * direccion[0] > 0)) {  // Movimiento horizontal
                return true;
            }
        }
        return false;
    }
}