package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Limpieza extends Poder {

    public Limpieza() {
        super(Configuracion.CaracteristicasPoderes.LIMPIEZA, -1, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.LIMPIEZA, Configuracion.AplicacionPoder.AMBOS);
    }

    @Override
    public void aplicarPoder(Pieza pieza) {
        pieza.desactivarPoder();
    }

    public Configuracion.AplicacionPoder getTipoPiezaAplicable() {
        return this.aplicacion;
    }
}
