package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public interface TipoDePieza {
    String getTipoDePieza();

    // Metodo para verificar si el movimiento está en una dirección de movimiento permitida
    boolean movimientoEnDireccionDeMovimiento(int inicioX, int inicioY, int finX, int finY);

    // Metodo para verificar si el movimiento está en una dirección de amenaza permitida
    boolean movimientoEnDireccionDeAmenaza(int inicioX, int inicioY, int finX, int finY);

    // Lista de amenazas generadas por la pieza
    ArrayList<Amenaza> getAmenazasGeneradas(String color);

    // Obtener la distancia máxima de amenaza que la pieza puede ejercer
    int getMaxDistanciaDeAmenaza();

    // Métodos para obtener las direcciones de movimiento y de amenaza
    ArrayList<int[]> getDireccionesDeMovimiento();
}
