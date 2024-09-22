package org.fiuba.algoritmos3.tp1powechess.Model;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

public class Pieza {
    private Configuracion.ColoresJugadores color;
    private TipoDePieza tipoDePieza;

    public Pieza(Configuracion.ColoresJugadores color, TipoDePieza tipoDePieza) {
        this.color = color;
        this.tipoDePieza = tipoDePieza;
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public String getPieceType() {
        return tipoDePieza.getTipoDePieza();
    }

    public boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY) {
        return tipoDePieza.esMovimientoValido(inicioX, inicioY, finX, finY);
    }

    /*
    public ArrayList<Amenaza> getAmenazasGeneradas(){
        return tipoDePieza.getAmenazasGeneradas(this.color);
    }

     */

}
