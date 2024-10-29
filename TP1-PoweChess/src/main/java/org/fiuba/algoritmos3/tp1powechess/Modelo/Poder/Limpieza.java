package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;


public class Limpieza extends Poder {

    public Limpieza() {
            super("Limpieza", -1, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.LIMPIEZA, Configuracion.AplicacionPoder.AMBOS);
    }

    public String accionarPoder(Pieza pieza) {
        String nombrePoderDesactivado = pieza.desactivarPoder();
        return nombrePoderDesactivado;
    }
}
