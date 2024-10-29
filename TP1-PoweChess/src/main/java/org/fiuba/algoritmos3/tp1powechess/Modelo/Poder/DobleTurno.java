package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class DobleTurno extends Poder {

    public DobleTurno() {
        super("Doble", -1, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.DOBLETURNO, Configuracion.AplicacionPoder.PROPIA);
    }

    public void aplicarPoder(Pieza pieza) {
      pieza.activarDobleMovimiento();
      pieza.setPoder(this);
    }

}


