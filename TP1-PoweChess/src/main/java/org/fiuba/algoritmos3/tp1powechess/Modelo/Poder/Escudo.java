package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Escudo extends Poder {

    public Escudo() {
        super("Escudo",3,Configuracion.CategoriaPoder.DURACION, Configuracion.TipoPoder.ESCUDO, Configuracion.AplicacionPoder.PROPIA);

    }
    public void aplicarPoder(Pieza pieza) {
        pieza.setSePuedeCapturar(false);
        pieza.setPoder(this);
    }

}