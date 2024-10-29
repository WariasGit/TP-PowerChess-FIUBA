package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Freeze extends Poder {
    public Freeze() {
        super("Freeze", 3, Configuracion.CategoriaPoder.DURACION, Configuracion.TipoPoder.FREEZE, Configuracion.AplicacionPoder.RIVAL);
    }

    public String  accionarPoder(Pieza pieza) {
        pieza.setPoder(this);
        return this.getNombre();
    }
}
