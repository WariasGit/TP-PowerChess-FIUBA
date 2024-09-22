package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class GestorDeAmenazas {
    private ArrayList<Amenaza> amenazasActivas;
    private ArrayList<Amenaza> amenazasBloqueadas;

    public GestorDeAmenazas() {
        this.amenazasActivas = new ArrayList<>();
        this.amenazasBloqueadas = new ArrayList<>();
    }

    public void agregarAmenazasActivas(ArrayList<Amenaza> amenazas) {
        amenazasActivas.addAll(amenazas);
    }

    public void agregarAmenazasBloqueadas(ArrayList<Amenaza> amenazas) {
        amenazasBloqueadas.addAll(amenazas);
    }

    public void removerTodasLasAmenazasActivas(ArrayList<Amenaza> amenazas) {
        amenazasActivas.removeAll(amenazas);
    }

    public void removerTodasLasAmenazasBloqueadas(ArrayList<Amenaza> amenazas) {
        amenazasBloqueadas.removeAll(amenazas);
    }

    public ArrayList<Amenaza> getAmenazasBloqueadas() {
        return new ArrayList<>(amenazasBloqueadas);
    }

    public ArrayList<Amenaza> getAmenazasActivas() {
        return new ArrayList<>(amenazasActivas);
    }

    public boolean tieneAlMenosUnaAmenaza() {
        return !amenazasActivas.isEmpty() || !amenazasBloqueadas.isEmpty();
    }

    public void quitarAmenazasIguales(ArrayList<Amenaza> amenazasAEliminar) {
        amenazasAEliminar.forEach(amenazaAEliminar -> {
            amenazasActivas.removeIf(amenaza -> amenaza.esIgualA(amenazaAEliminar));
            amenazasBloqueadas.removeIf(amenaza -> amenaza.esIgualA(amenazaAEliminar));
        });
    }

    public ArrayList<Amenaza> obtenerAmenazasPorColor(String color) {
        ArrayList<Amenaza> amenazasPorColor = new ArrayList<>();

        amenazasActivas.forEach(amenaza -> {
            if (amenaza.esDeColor(color)) {
                amenazasPorColor.add(amenaza);
            }
        });

        amenazasBloqueadas.forEach(amenaza -> {
            if (amenaza.esDeColor(color)) {
                amenazasPorColor.add(amenaza);
            }
        });

        return amenazasPorColor;
    }

}