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
        return amenazas.size();
    }

    public void agregarAmenaza(Amenaza amenaza) {
        this.amenazas.add(amenaza);
    }

    public void agregarAmenazas(ArrayList<Amenaza> amenazas) {
        this.amenazas.addAll(amenazas);
    }

    public void removerTodasLasAmenazas() {
        amenazas.clear();
    }

    public int getNumeroDeAmenazasDistintoColor(String color) {
        final int[] contadorAmenazasDistintoColor = {0};
        amenazas.forEach(amenaza -> {
            if (!amenaza.esDeColor(color)) {
                contadorAmenazasDistintoColor[0]++;
            }
        });
        return contadorAmenazasDistintoColor[0];
    }

    public int getNumeroDeAmenazasMismoColor(String color) {
        final int[] contadorAmenazasMismoColor = {0};
        amenazas.forEach(amenaza -> {
            if (amenaza.esDeColor(color)) {
                contadorAmenazasMismoColor[0]++;
            }
        });
        return contadorAmenazasMismoColor[0];
    }

    public ArrayList<Amenaza> getAmenazas() {
        return this.amenazas;
    }

    public ArrayList<Amenaza> getAmenazasDistintoColor(String colorPieza) {
        ArrayList<Amenaza> amenazasDistintoColor = new ArrayList<>();

        this.amenazas.forEach(amenaza -> {
            if (!amenaza.esDeColor(colorPieza)) {
                amenazasDistintoColor.add(amenaza);
            }
        });

        return amenazasDistintoColor;
    }

    public ArrayList<Amenaza> getAmenazasMismoColor(String color) {
        ArrayList<Amenaza> amenazasMismoColor = new ArrayList<>();

        this.amenazas.forEach(amenaza -> {
            if (amenaza.esDeColor(color)) {
                amenazasMismoColor.add(amenaza);
            }
        });

        return amenazasMismoColor;
    }
}