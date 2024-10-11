package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Escudo extends Poder {

    public Escudo(int duracion) {
        super("Escudo", duracion, Configuracion.CategoriaPoder.DURACION);
    }

    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);
    }

    @Override
    public void aplicar(Pieza pieza) {
        //
    }
}