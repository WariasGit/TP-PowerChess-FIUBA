package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

public class Vuelo extends Poder {

    public Vuelo() {
            super("Vuelo", -1, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.VUELO, Configuracion.AplicacionPoder.PROPIA);
        }
        public void aplicarPoder(Pieza pieza) {
            pieza.setPuedeVolar(true);
            pieza.setPoder(this);
        }

    }
