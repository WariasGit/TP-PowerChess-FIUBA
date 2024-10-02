package org.fiuba.algoritmos3.tp1powechess.Model;

public class EnrocableSiNoSeHaMovido implements Enrocable {
    private Pieza pieza;

    public EnrocableSiNoSeHaMovido(Pieza pieza){
        this.pieza = pieza;
    }

    public boolean puedeEnrocar(){
        return !pieza.seHaMovido();
    }
}
