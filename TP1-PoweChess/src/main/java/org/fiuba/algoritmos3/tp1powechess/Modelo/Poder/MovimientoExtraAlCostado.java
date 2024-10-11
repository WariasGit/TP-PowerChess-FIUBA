package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Alfil;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Caballo;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class MovimientoExtraAlCostado extends Poder {

    public MovimientoExtraAlCostado() {
        super("Movimiento Extra Lateral", -1, Configuracion.CategoriaPoder.EVOLUCION);  // Poder permanente
    }

    @Override
    public void aplicar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            pieza.agregarMovimiento(0,1);   // Movimiento hacia la derecha
            pieza.agregarMovimiento(0,-1);  // Movimiento hacia la izquierda
            pieza.agregarMovimiento(1,0);   // Movimiento hacia abajo
            pieza.agregarMovimiento(-1,0);  // Movimiento hacia arriba
        }
    }

    public void desactivar(Pieza pieza) {
        if (pieza instanceof Alfil || pieza instanceof Caballo || pieza instanceof Torre) {
            pieza.eliminarMovimiento(0, 1);
            pieza.eliminarMovimiento(0, -1);
            pieza.eliminarMovimiento(1, 0);
            pieza.eliminarMovimiento(-1, 0);
        }
    }
}
