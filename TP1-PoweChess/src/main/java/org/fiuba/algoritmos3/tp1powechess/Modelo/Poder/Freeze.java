package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Freeze extends Poder {
    public Freeze(int duracion) {
        super(Configuracion.CaracteristicasPoderes.FREEZE, 3, Configuracion.CategoriaPoder.DURACION, Configuracion.TipoPoder.FREEZE, Configuracion.AplicacionPoder.RIVAL);
    }


}