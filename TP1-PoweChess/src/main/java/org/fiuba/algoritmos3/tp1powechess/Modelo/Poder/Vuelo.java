package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Vuelo extends Poder {

    public Vuelo() {
        super(Configuracion.CaracteristicasPoderes.VUELO, 0, Configuracion.CategoriaPoder.ACCION); // Solo dura un turno
    }

    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);  // Elimina el poder Freeze de la pieza
    }
}
