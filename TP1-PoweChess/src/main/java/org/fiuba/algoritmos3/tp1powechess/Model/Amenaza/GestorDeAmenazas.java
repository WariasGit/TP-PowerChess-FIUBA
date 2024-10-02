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

    public ArrayList<Amenaza> getAmenazasBloqueadas() {
        return new ArrayList<>(amenazasBloqueadas);
    }

    public ArrayList<Amenaza> getAmenazasActivas() {
        return new ArrayList<>(amenazasActivas);
    }

    public ArrayList<Amenaza> getAmenazas() {
        ArrayList<Amenaza> todasLasAmenazas = new ArrayList<>();
        todasLasAmenazas.addAll(this.amenazasActivas);
        todasLasAmenazas.addAll(this.amenazasBloqueadas);
        return amenazasActivas;
    }

    public boolean tieneAlMenosUnaAmenaza() {
        return !amenazasActivas.isEmpty() || !amenazasBloqueadas.isEmpty();
    }

    public boolean tieneAlMenosUnaAmenazaActiva() {
        return !amenazasActivas.isEmpty();
    }

    public void quitarAmenazasIguales(ArrayList<Amenaza> amenazasAEliminar) {
        amenazasAEliminar.forEach(amenazaAEliminar -> {
            amenazasActivas.removeIf(amenaza -> amenaza.esIgualA(amenazaAEliminar));
            amenazasBloqueadas.removeIf(amenaza -> amenaza.esIgualA(amenazaAEliminar));
        });
    }

    public ArrayList<Amenaza> obtenerAmenazasActivasQueSeExtiendenMasQue(int numeroDeCasilleros) {
        return obtenerAmenazasDeListaQueSeExtiendenMasQue(amenazasActivas, numeroDeCasilleros);
    }

    public ArrayList<Amenaza> obtenerAmenazasBloqueadasQueSeExtiendenMasQue(int numeroDeCasilleros) {
        return obtenerAmenazasDeListaQueSeExtiendenMasQue(amenazasBloqueadas, numeroDeCasilleros);
    }

    public ArrayList<Amenaza> obtenerAmenazasTotalesQueSeExtiendenMasQue(int numeroDeCasilleros) {
        ArrayList<Amenaza> amenazasTotales = new ArrayList<>();

        amenazasTotales.addAll(obtenerAmenazasDeListaQueSeExtiendenMasQue(amenazasActivas, numeroDeCasilleros));
        amenazasTotales.addAll(obtenerAmenazasDeListaQueSeExtiendenMasQue(amenazasBloqueadas, numeroDeCasilleros));

        return amenazasTotales;
    }

    private ArrayList<Amenaza> obtenerAmenazasDeListaQueSeExtiendenMasQue(ArrayList<Amenaza> listaAmenazas, int numeroDeCasilleros) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();

        listaAmenazas.forEach(amenaza -> {
            if (amenaza.seExtiendeMasAllaDe(numeroDeCasilleros)) {
                amenazasFiltradas.add(amenaza);
            }
        });

        return amenazasFiltradas;
    }

    public ArrayList<Amenaza> obtenerAmenazasActivasQueSeExtiendenMenosQue(int numeroDeCasilleros) {
        return obtenerAmenazasDeListaQueSeExtiendenMenosQue(amenazasActivas, numeroDeCasilleros);
    }

    public ArrayList<Amenaza> obtenerAmenazasBloqueadasQueSeExtiendenMenosQue(int numeroDeCasilleros) {
        return obtenerAmenazasDeListaQueSeExtiendenMenosQue(amenazasBloqueadas, numeroDeCasilleros);
    }

    public ArrayList<Amenaza> obtenerAmenazasTotalesQueSeExtiendenMenosQue(int numeroDeCasilleros) {
        ArrayList<Amenaza> amenazasTotales = new ArrayList<>();

        amenazasTotales.addAll(obtenerAmenazasDeListaQueSeExtiendenMenosQue(amenazasActivas, numeroDeCasilleros));
        amenazasTotales.addAll(obtenerAmenazasDeListaQueSeExtiendenMenosQue(amenazasBloqueadas, numeroDeCasilleros));

        return amenazasTotales;
    }

    private ArrayList<Amenaza> obtenerAmenazasDeListaQueSeExtiendenMenosQue(ArrayList<Amenaza> listaAmenazas, int numeroDeCasilleros) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();

        listaAmenazas.forEach(amenaza -> {
            if (!amenaza.seExtiendeMasAllaDe(numeroDeCasilleros)) {
                amenazasFiltradas.add(amenaza);
            }
        });

        return amenazasFiltradas;
    }

    public ArrayList<Amenaza> obtenerAmenazasActivasPorColor(String color) {
        return obtenerAmenazasDeListaPorColor(amenazasActivas, color);
    }

    public ArrayList<Amenaza> obtenerAmenazasBloqueadasPorColor(String color) {
        return obtenerAmenazasDeListaPorColor(amenazasBloqueadas, color);
    }

    public ArrayList<Amenaza> obtenerAmenazasTotalesPorColor(String color) {
        ArrayList<Amenaza> amenazasTotales = new ArrayList<>();

        amenazasTotales.addAll(obtenerAmenazasDeListaPorColor(amenazasActivas, color));
        amenazasTotales.addAll(obtenerAmenazasDeListaPorColor(amenazasBloqueadas, color));

        return amenazasTotales;
    }

    private ArrayList<Amenaza> obtenerAmenazasDeListaPorColor(ArrayList<Amenaza> listaAmenazas, String color) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();

        listaAmenazas.forEach(amenaza -> {
            if (amenaza.esDeColor(color)) {
                amenazasFiltradas.add(amenaza);
            }
        });

        return amenazasFiltradas;
    }

    public ArrayList<Amenaza> obtenerAmenazasActivasDistintoColor(String color) {
        return obtenerAmenazasDeListaDistintoColor(amenazasActivas, color);
    }

    public ArrayList<Amenaza> obtenerAmenazasBloqueadasDistintoColor(String color) {
        return obtenerAmenazasDeListaDistintoColor(amenazasBloqueadas, color);
    }

    public ArrayList<Amenaza> obtenerAmenazasTotalesDistintoColor(String color) {
        ArrayList<Amenaza> amenazasTotales = new ArrayList<>();

        amenazasTotales.addAll(obtenerAmenazasDeListaDistintoColor(amenazasActivas, color));
        amenazasTotales.addAll(obtenerAmenazasDeListaDistintoColor(amenazasBloqueadas, color));

        return amenazasTotales;
    }

    private ArrayList<Amenaza> obtenerAmenazasDeListaDistintoColor(ArrayList<Amenaza> listaAmenazas, String color) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();

        listaAmenazas.forEach(amenaza -> {
            if (!amenaza.esDeColor(color)) {
                amenazasFiltradas.add(amenaza);
            }
        });

        return amenazasFiltradas;
    }

    public void moverTodasAmenazasBloqueadasAActivas() {
        ArrayList<Amenaza> todasAmenazasBloqueadas = new ArrayList<>(amenazasBloqueadas);
        moverAmenazas(amenazasBloqueadas, amenazasActivas, todasAmenazasBloqueadas);
    }

    public void moverAmenazasActivasAmasDeUnCasilleroABloqueadas() {
        ArrayList<Amenaza> amenazasParaMover = new ArrayList<>();

        amenazasActivas.forEach(amenaza -> {
            if (amenaza.seExtiendeMasAllaDe(1)) {
                amenazasParaMover.add(amenaza);
            }
        });

        moverAmenazas(amenazasActivas, amenazasBloqueadas, amenazasParaMover);
    }

    private void moverAmenazas(ArrayList<Amenaza> origen, ArrayList<Amenaza> destino, ArrayList<Amenaza> amenazasParaMover) {
        amenazasParaMover.forEach(amenaza -> {
            if (origen.remove(amenaza)) {
                destino.add(amenaza);
            }
        });
    }
}