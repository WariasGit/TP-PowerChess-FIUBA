package org.fiuba.algoritmos3.tp1powechess.Model;

public class PeonAscendente extends PeonBase {

    public PeonAscendente(String color){
        super(color);
    }

    protected int getDireccion() {
        return 1;
    }
}
