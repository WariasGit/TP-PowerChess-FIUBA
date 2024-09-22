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
        }
    }

    public Casillero getCasillero(int row, int col) {
        return tablero[row][col];
    }

    public void setPieza(int row, int col, Pieza pieza) {
        if (!esCoordenadaValida(row, col)) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
        Casillero casillero = getCasillero(row, col);
        casillero.setPieza(pieza);

        ArrayList<Amenaza> amenazasBloqueadas = casillero.getAmenazasBloqueadas();

        for (Amenaza amenaza : amenazasBloqueadas) {
            int[] direccion = amenaza.getDireccion();

            // Calcular hasta dónde se extiende la amenaza
            for (int i = 0; i < amenaza.getCantidadCasilleros(); i++) {
                // Calcular nuevas coordenadas
                int nuevaRow = row + (i + 1) * direccion[0]; // Se suma 1 para no afectar el casillero donde se colocó la pieza
                int nuevaCol = col + (i + 1) * direccion[1];

                // Verificar límites del tablero
                if (esCoordenadaValida(nuevaRow, nuevaCol)) {
                    Casillero casilleroAmenazado = getCasillero(nuevaRow, nuevaCol);
                    casilleroAmenazado.bloquearAmenazasQueSeExtiendenMasDeUnCasillero();
                    if(casilleroAmenazado.estaOcupado()){
                        break;
                    }
                } else {
                    break; // Fuera de límites, detener el proceso
                }
            }
        }
    }

    private boolean esCoordenadaValida(int row, int col) {
        return row >= 0 && row < dimensiones && col >= 0 && col < dimensiones;
    }
}
