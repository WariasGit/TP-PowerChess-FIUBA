package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class Pieza {
    private String color;
    private TipoDePieza tipoDePieza;

    public Pieza(String color, TipoDePieza tipoDePieza) {
        this.color = color;
        this.tipoDePieza = tipoDePieza;
    }

    public String getColor() {
        return color;
    }

    public boolean esDeColor(String color) {
        return this.color.equals(color);
    }

    public String getPieceType() {
        return tipoDePieza.getTipoDePieza();
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        return tipoDePieza.esMovimientoValido(inicioX, inicioY, finX, finY);
    }

    public ArrayList<Amenaza> getAmenazasGeneradas(){
        return tipoDePieza.getAmenazasGeneradas(this.color);
    }

}
