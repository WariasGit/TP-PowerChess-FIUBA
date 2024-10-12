package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Vuelo extends Poder {

    public Vuelo() {
        super(Configuracion.CaracteristicasPoderes.VUELO, Configuracion.CaracteristicasPoderes.DURACION_VUELO, Configuracion.CategoriaPoder.ACCION); // Solo dura un turno
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
