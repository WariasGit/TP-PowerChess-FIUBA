package org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

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

    public ArrayList<Amenaza> getAmenazasBloqueadas() {
        return new ArrayList<>(amenazasBloqueadas);
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

    private ArrayList<Amenaza> obtenerAmenazasDeListaQueSeExtiendenMasQue(ArrayList<Amenaza> listaAmenazas, int numeroDeCasilleros) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();

        listaAmenazas.forEach(amenaza -> {
            if (amenaza.seExtiendeMasAllaDe(numeroDeCasilleros)) {
                amenazasFiltradas.add(amenaza);
            }
        });

        return amenazasFiltradas;
    }

    private ArrayList<Amenaza> obtenerAmenazasDeListaPorColor(ArrayList<Amenaza> listaAmenazas, Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();

        listaAmenazas.forEach(amenaza -> {
            if (amenaza.esDeColor(color)) {
                amenazasFiltradas.add(amenaza);
            }
        });

        return amenazasFiltradas;
    }

    public ArrayList<Amenaza> obtenerAmenazasTotalesDistintoColor(Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasTotales = new ArrayList<>();
        amenazasTotales.addAll(obtenerAmenazasDeListaDistintoColor(amenazasActivas, color));
        amenazasTotales.addAll(obtenerAmenazasDeListaDistintoColor(amenazasBloqueadas, color));
        return amenazasTotales;
    }

    private ArrayList<Amenaza> obtenerAmenazasDeListaDistintoColor(ArrayList<Amenaza> listaAmenazas, Configuracion.ColoresJugadores color) {
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