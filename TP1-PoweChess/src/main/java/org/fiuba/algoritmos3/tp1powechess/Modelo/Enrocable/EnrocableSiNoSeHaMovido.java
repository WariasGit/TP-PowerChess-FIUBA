package org.fiuba.algoritmos3.tp1powechess.Model.Enrocable;

import org.fiuba.algoritmos3.tp1powechess.Model.Pieza.Pieza;

public class EnrocableSiNoSeHaMovido implements Enrocable {
    private Pieza pieza;

    public EnrocableSiNoSeHaMovido(Pieza pieza){
        this.pieza = pieza;
    }

    public boolean puedeEnrocar(){
        return !pieza.seHaMovido();
    }
}
