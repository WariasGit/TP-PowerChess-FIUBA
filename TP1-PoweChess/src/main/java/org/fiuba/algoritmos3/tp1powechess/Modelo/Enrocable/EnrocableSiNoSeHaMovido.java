package org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;

public class EnrocableSiNoSeHaMovido implements Enrocable {
    private Pieza pieza;

    public EnrocableSiNoSeHaMovido(Pieza pieza){
        this.pieza = pieza;
    }

    public boolean puedeEnrocar(){
        return !pieza.seHaMovido();
    }
}
