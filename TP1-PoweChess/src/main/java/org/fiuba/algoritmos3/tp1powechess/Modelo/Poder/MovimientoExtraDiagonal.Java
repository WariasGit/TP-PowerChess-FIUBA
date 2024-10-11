package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Alfil;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Caballo;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class MovimientoExtraDiagonal extends Poder {

    public MovimientoExtraDiagonal() {
        super("Movimiento Extra Diagonal", -1, Configuracion.CategoriaPoder.EVOLUCION);  // Poder permanente
    }

    @Override
    public void aplicar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            ((Alfil) pieza).agregarMovimiento(1, 1);   // Diagonal hacia arriba a la derecha
            ((Alfil) pieza).agregarMovimiento(1, -1);  // Diagonal hacia abajo a la derecha
            ((Alfil) pieza).agregarMovimiento(-1, 1);  // Diagonal hacia arriba a la izquierda
            ((Alfil) pieza).agregarMovimiento(-1, -1); // Diagonal hacia abajo a la izquierda
        }
    }

    public void desactivar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            ((Alfil) pieza).eliminarMovimiento(1, 1);
            ((Alfil) pieza).eliminarMovimiento(1, -1);
            ((Alfil) pieza).eliminarMovimiento(-1, 1);
            ((Alfil) pieza).eliminarMovimiento(-1, -1);
        }
    }
}
