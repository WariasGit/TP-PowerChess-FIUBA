package org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

/**
 * La clase GestorDeAmenazas administra y organiza amenazas en el tablero, separándolas en amenazas activas y bloqueadas.
 * Esta clase permite agregar amenazas, moverlas entre listas, y filtrar
 * amenazas basándose en su color y cantidad de casilleros a los que se extienden.
 */
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

    /**
     * Elimina amenazas iguales a las proporcionadas de las listas de amenazas activas y bloqueadas.
     * @param amenazasAEliminar Lista de amenazas que se desea eliminar.
     */
    public void quitarAmenazasIguales(ArrayList<Amenaza> amenazasAEliminar) {
        amenazasAEliminar.forEach(amenazaAEliminar -> {
            amenazasActivas.removeIf(amenaza -> amenaza.esIgualA(amenazaAEliminar));
            amenazasBloqueadas.removeIf(amenaza -> amenaza.esIgualA(amenazaAEliminar));
        });
    }

    /**
     * Obtiene una lista de amenazas activas que se extienden más allá de un número especificado de casilleros.
     * @param numeroDeCasilleros Número mínimo de casilleros que deben abarcar las amenazas.
     * @return Lista de amenazas activas que cumplen con el criterio.
     */
    public ArrayList<Amenaza> obtenerAmenazasActivasQueSeExtiendenMasQue(int numeroDeCasilleros) {
        return obtenerAmenazasDeListaQueSeExtiendenMasQue(amenazasActivas, numeroDeCasilleros);
    }

    /**
     * Obtiene una lista de amenazas de una lista específica que se extienden más allá de un número de casilleros.
     * @param listaAmenazas      Lista de amenazas a filtrar.
     * @param numeroDeCasilleros Número mínimo de casilleros que deben abarcar las amenazas.
     * @return Lista de amenazas que cumplen con el criterio.
     */
    private ArrayList<Amenaza> obtenerAmenazasDeListaQueSeExtiendenMasQue(ArrayList<Amenaza> listaAmenazas, int numeroDeCasilleros) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();
        listaAmenazas.forEach(amenaza -> {
            if (amenaza.seExtiendeMasAllaDe(numeroDeCasilleros)) {
                amenazasFiltradas.add(amenaza);
            }
        });
        return amenazasFiltradas;
    }

    /**
     * Obtiene todas las amenazas, tanto activas como bloqueadas, que son de un color distinto al especificado.
     * @param color Color de las amenazas que se desea excluir.
     * @return Lista de amenazas que son de un color diferente al especificado.
     */
    public ArrayList<Amenaza> obtenerAmenazasTotalesDistintoColor(Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasTotales = new ArrayList<>();
        amenazasTotales.addAll(obtenerAmenazasDeListaDistintoColor(amenazasActivas, color));
        amenazasTotales.addAll(obtenerAmenazasDeListaDistintoColor(amenazasBloqueadas, color));
        return amenazasTotales;
    }

    /**
     * Obtiene una lista de amenazas de un color distinto al especificado dentro de una lista dada.
     * @param listaAmenazas Lista de amenazas a filtrar.
     * @param color Color de las amenazas que se desea excluir.
     * @return Lista de amenazas que no coinciden con el color especificado.
     */
    private ArrayList<Amenaza> obtenerAmenazasDeListaDistintoColor(ArrayList<Amenaza> listaAmenazas, Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasFiltradas = new ArrayList<>();
        listaAmenazas.forEach(amenaza -> {
            if (!amenaza.esDeColor(color)) {
                amenazasFiltradas.add(amenaza);
            }
        });
        return amenazasFiltradas;
    }

    public ArrayList<Amenaza> getAmenazasBloqueadas() {
        return new ArrayList<>(amenazasBloqueadas);
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