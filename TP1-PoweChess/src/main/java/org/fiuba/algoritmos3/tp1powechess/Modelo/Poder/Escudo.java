package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Escudo extends Poder {

    public Escudo(int duracion) {
        super(Configuracion.CaracteristicasPoderes.ESCUDO, 3, Configuracion.CategoriaPoder.DURACION, Configuracion.TipoPoder.ESCUDO);
    }

    @Override
    public Configuracion.TipoPoder getTipo() {
        return Configuracion.TipoPoder.ESCUDO;
    }

}