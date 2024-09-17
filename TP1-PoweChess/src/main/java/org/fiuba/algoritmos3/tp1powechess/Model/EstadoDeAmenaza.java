package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class EstadoDeAmenaza{
    private ArrayList<Amenaza> amenazas;

    public EstadoDeAmenaza() {
        this.amenazas = new ArrayList<>();
    }

    public EstadoDeAmenaza(ArrayList<Amenaza> amenazas) {
        this.amenazas = amenazas;
    }

    public boolean estaAmenazado(){
        return !amenazas.isEmpty();
    }

    public int getNumeroDeAmenazas() {
        return amenazas.size(); // No hay amenazas en un casillero no amenazado
    }

    public void agregarAmenaza(Amenaza amenaza) {
        this.amenazas.add(amenaza);
    }


    public int getNumeroDeAmenazasDistintoColor(String colorPieza) {
        return 0; // No hay amenazas en un casillero no amenazado
    }

    public int getNumeroDeAmenazasMismoColor(String colorPieza) {
        return 0; // No hay amenazas en un casillero no amenazado
    }

    public ArrayList<int[]> getDireccionesDeAmenaza() {
        return new ArrayList<>(); // No hay direcciones de amenaza
    }

    public ArrayList<int[]> getDireccionesDeAmenazaDistintoColor(String colorPieza) {
        return new ArrayList<>(); // No hay direcciones de amenaza
    }

    public ArrayList<int[]> getDireccionesDeAmenazaMismoColor(String colorPieza) {
        return new ArrayList<>(); // No hay direcciones de amenaza
    }
}
