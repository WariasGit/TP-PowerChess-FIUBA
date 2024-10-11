package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class PeonDescendente extends PeonBase {
    public PeonDescendente(Configuracion.ColoresJugadores color){
        super(color);
    }

    protected int getDireccion() {
        return 1;
    }
}
