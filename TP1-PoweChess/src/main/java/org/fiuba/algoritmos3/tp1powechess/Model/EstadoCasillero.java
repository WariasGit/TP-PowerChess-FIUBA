package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public interface EstadoCasillero {
    boolean estaOcupado();
    boolean estaAmenazado();
    int getNumeroDeAmenazasDistintoColor(String ColorPieza);
    int getNumeroDeAmenazasMismoColor(String ColorPieza);
    ArrayList<int[]> getDireccionesDeAmenazaDistintoColor(String ColorPieza);
    ArrayList<int[]> getDireccionesDeAmenazaMismoColor(String ColorPieza);
}