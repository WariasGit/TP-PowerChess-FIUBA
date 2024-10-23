package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Vuelo extends Poder {

    public Vuelo() {
        super(Configuracion.CaracteristicasPoderes.VUELO, Configuracion.CaracteristicasPoderes.DURACION_VUELO, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.VUELO);
    }

    @Override
    public Configuracion.TipoPoder getTipo() {
        return Configuracion.TipoPoder.VUELO;
    }
}

