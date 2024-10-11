package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class DobleJuego extends Poder {

    public DobleJuego() {
        super(Configuracion.CaracteristicasPoderes.DOBLE_JUEGO, 0, Configuracion.CategoriaPoder.ACCION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.setMovimientoDoble(true);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setMovimientoDoble(false);
    }
}
