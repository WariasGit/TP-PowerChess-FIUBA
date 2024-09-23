package org.fiuba.algoritmos3.tp1powechess.Model;

import java.util.ArrayList;

public class TableroCuadrado {
    private Casillero[][] tablero;
    static private Integer dimensiones = 8;

    public TableroCuadrado() {
        tablero = new Casillero[dimensiones][dimensiones];
        inicializarTableroAjedrez();
    }

    private void inicializarTableroAjedrez(){
        for (int row = 0; row < dimensiones; row++) {
            for (int col = 0; col < dimensiones; col++) {
                  String color = (row + col) % 2 == 0 ? "Blanco" : "Negro";
                  tablero[row][col] = new Casillero(color);
            }
            colocarPiezasIniciales();
        }
    }

    public Casillero getCasillero(int row, int col) {
        return this.tablero[row][col];
    }

    public Pieza setPieza(int row, int col, Pieza piezaAColocar) {
        if (!esCoordenadaValida(row, col)) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
        Casillero casillero = getCasillero(row, col);
        Pieza piezaComida = casillero.getPieza();

        // Si el casillero ya está ocupado, eliminamos la pieza existente y sus amenazas
        if(casillero.estaOcupado()){
            // Actualizamos las amenazas de la pieza existente antes de reemplazarla
            quitarAmenazasDesdeCoordenadasHastaLimiteUOcupado(casillero.getAmenzasDePiezaActual(), row, col);
            casillero.setPieza(piezaAColocar);

        }
        else {
            casillero.setPieza(piezaAColocar);
            // Quitamos las amenazas qu ahora pasaron a estar bloqueadas
            quitarAmenazasDesdeCoordenadasHastaLimiteUOcupado(casillero.getAmenazasBloqueadas(), row, col);
        }

        // Actualizamos las amenazas generadas por la nueva pieza
        agregarAmenazasDesdeCoordenadasHastaLimiteUOcupado(casillero.getAmenzasDePiezaActual(),row, col);

        return piezaComida;
    }

    public Pieza removePieza(int row, int col) {
        if (!esCoordenadaValida(row, col)) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }

        Casillero casillero = getCasillero(row, col);

        if(!casillero.estaOcupado()){
            return null;
        }

        Pieza piezaARemover = casillero.getPieza();

        // Actualizar las amenazas de la pieza a remover (solo las activas)
        quitarAmenazasDesdeCoordenadasHastaLimiteUOcupado(piezaARemover.getAmenazasGeneradas(), row, col);

        // Remover la pieza del casillero
        casillero.removePieza();

        //Actualizamos las amenazas que anteriormente estaban bloqueadas
        ArrayList<Amenaza> amenazasAExtender = casillero.obtenerAmenazasActivasQueSeExtiendenMasQueUnCasillero();
        agregarAmenazasDesdeCoordenadasHastaLimiteUOcupado(amenazasAExtender,row, col);

        return piezaARemover;
    }

    private boolean esCoordenadaValida(int row, int col) {
        return row >= 0 && row < dimensiones && col >= 0 && col < dimensiones;
    }

    private void quitarAmenazasDesdeCoordenadasHastaLimiteUOcupado(ArrayList<Amenaza> amenazasAQuitar, int row, int col) {
        for (Amenaza amenaza : amenazasAQuitar) {
            int[] direccion = amenaza.getDireccion();
            ArrayList<Amenaza> amenazaAQuitar = new ArrayList<Amenaza>();
            amenazaAQuitar.add(amenaza);
            // Calcular hasta dónde se extiende la amenaza
            for (int i = 0; i < amenaza.getCantidadCasilleros(); i++) {
                // Calcular nuevas coordenadas
                int nuevaRow = row + (i + 1) * direccion[0]; // Se suma 1 para no afectar el casillero donde se colocó la pieza
                int nuevaCol = col + (i + 1) * direccion[1];

                // Verificar límites del tablero
                if (esCoordenadaValida(nuevaRow, nuevaCol)) {
                    Casillero casilleroAmenazado = this.getCasillero(nuevaRow, nuevaCol);
                    casilleroAmenazado.removerLasSiguientesAmenazas(amenazaAQuitar);
                    if(casilleroAmenazado.estaOcupado()){
                        break;
                    }
                } else {
                    break; // Fuera de límites, detener el proceso
                }
            }
        }
    }

    private void agregarAmenazasDesdeCoordenadasHastaLimiteUOcupado(ArrayList<Amenaza> amenazasAAgregar, int row, int col) {
        for (Amenaza amenaza : amenazasAAgregar) {
            int[] direccion = amenaza.getDireccion();
            ArrayList<Amenaza> amenazaAAgregar = new ArrayList<Amenaza>();
            amenazaAAgregar.add(amenaza);

            // Calcular hasta dónde se extiende la amenaza
            for (int i = 0; i < amenaza.getCantidadCasilleros(); i++) {
                // Calcular nuevas coordenadas
                int nuevaRow = row + (i + 1) * direccion[0]; // Se suma 1 para no afectar el casillero donde se colocó la pieza
                int nuevaCol = col + (i + 1) * direccion[1];

                // Verificar límites del tablero
                if (esCoordenadaValida(nuevaRow, nuevaCol)) {
                    Casillero casilleroAmenazado = this.getCasillero(nuevaRow, nuevaCol);
                    casilleroAmenazado.agregarAmenazas(amenazaAAgregar);

                    // Si el casillero está ocupado, detener el proceso
                    if (casilleroAmenazado.estaOcupado()) {
                        break;
                    }
                } else {
                    break; // Fuera de los límites del tablero, detener el proceso
                }
            }
        }
    }

    private void colocarPiezasBlancas() {
        // Colocación de peones blancos
        String color = "blanco";
        for (int col = 0; col < dimensiones; col++) {
            setPieza(6, col, new Pieza(color, new PeonBlanco()));
        }

        // Colocación de piezas mayores blancas
        setPieza(7, 0, new Pieza(color, new Torre()));
        setPieza(7, 1, new Pieza(color, new Caballo()));
        setPieza(7, 2, new Pieza(color, new Alfil()));
        setPieza(7, 3, new Pieza(color, new Reina()));
        setPieza(7, 4, new Pieza(color, new Rey()));
        setPieza(7, 5, new Pieza(color, new Alfil()));
        setPieza(7, 6, new Pieza(color, new Caballo()));
        setPieza(7, 7, new Pieza(color, new Torre()));
    }

    private void colocarPiezasNegras() {
        // Colocación de peones blancos
        String color = "negro";
        for (int col = 0; col < dimensiones; col++) {
            setPieza(1, col, new Pieza(color, new PeonNegro()));
        }

        // Colocación de piezas mayores blancas
        setPieza(0, 0, new Pieza(color, new Torre()));
        setPieza(0, 1, new Pieza(color, new Caballo()));
        setPieza(0, 2, new Pieza(color, new Alfil()));
        setPieza(0, 3, new Pieza(color, new Reina()));
        setPieza(0, 4, new Pieza(color, new Rey()));
        setPieza(0, 5, new Pieza(color, new Alfil()));
        setPieza(0, 6, new Pieza(color, new Caballo()));
        setPieza(0, 7, new Pieza(color, new Torre()));
    }

    private void colocarPiezasIniciales() {
        colocarPiezasBlancas();
        colocarPiezasNegras();
    }
}
