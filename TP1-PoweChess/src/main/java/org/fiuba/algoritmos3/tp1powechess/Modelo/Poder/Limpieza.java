package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Poder;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Limpieza extends Poder {

    public Limpieza() {
        super("Limpieza", 1, Configuracion.CategoriaPoder.ACCION);
    }
    public void aplicar(Pieza pieza) {
        pieza.desactivarPoder(this);
    }
}
