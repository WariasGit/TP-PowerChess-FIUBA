package org.fiuba.algoritmos3.tp1powechess.Model;

public class PeonDescendente extends PeonBase {
    public PeonDescendente(String color){
        super(color);
    }

    protected int getDireccion() {
        return -1;
    }
}
