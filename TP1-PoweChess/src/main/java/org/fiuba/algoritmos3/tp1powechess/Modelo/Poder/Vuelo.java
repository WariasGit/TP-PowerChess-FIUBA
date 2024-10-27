package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

    public class Vuelo extends Poder {

    public Vuelo(int duracion) {
        super(Configuracion.CaracteristicasPoderes.VUELO, -1, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.VUELO, Configuracion.AplicacionPoder.PROPIA);
    }

}