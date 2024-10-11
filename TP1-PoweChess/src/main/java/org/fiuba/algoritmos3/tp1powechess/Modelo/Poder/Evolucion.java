package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Evolucion extends Poder {
    private int[] movimientoExtra;

    public Evolucion(int[] movimientoExtra) {
        super("Evolucion", 0, Configuracion.CategoriaPoder.EVOLUCION); // Dura por el resto de la partida
        this.movimientoExtra = movimientoExtra;
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.aplicarPoder(this);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);  // Elimina el poder Freeze de la pieza
    }
}
