package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public interface TipoDePieza {
    String getTipoDePieza();
    boolean esMovimientoValido(int inicioX, int inicioY, int finX, int finY);
    public ArrayList<Amenaza> getAmenazasGeneradas(String color,int inicioX, int inicioY);
    int getMaxDistanciaDeAmenaza();
}
