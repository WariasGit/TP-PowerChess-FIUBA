package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.*;
import java.util.Optional;
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
                Configuracion.ColoresJugadores color = (row + col) % 2 == 0 ? Configuracion.ColoresJugadores.BLANCO : Configuracion.ColoresJugadores.NEGRO;
                  tablero[row][col] = new Casillero(color);
            }
        }
    }

    public Casillero getCasillero(int row, int col) {
        return this.tablero[row][col];
    }

    public Optional<Pieza> getPieza(Integer i, Integer j) {
        return Optional.ofNullable(tablero[i][j].getPieza());
    }

    public Pieza moverPieza(int rowInicial, int colInicial, int rowFinal, int colFinal) {
        // Verificamos si las coordenadas son válidas
        if (!esCoordenadaValida(rowInicial, colInicial)) {
            throw new IllegalArgumentException("Coordenadas iniciales fuera de los límites del tablero.");
        }

        if (!esCoordenadaValida(rowFinal, colFinal)) {
            throw new IllegalArgumentException("Coordenadas finales fuera de los límites del tablero.");
        }

        // Verificamos que el casillero inicial tenga una pieza
        Casillero casilleroInicial = getCasillero(rowInicial, colInicial);

        if (!casilleroInicial.estaOcupado()) {
            throw new IllegalArgumentException("No hay ninguna pieza en el casillero inicial.");
        }

        Pieza piezaAMover = casilleroInicial.getPieza();

        Coordenada2D coordenadaInicial = new Coordenada2D(rowInicial, colInicial);
        Coordenada2D coordenadaFinal = new Coordenada2D(rowFinal, colFinal);

        // Verificamos si el movimiento es válido para la pieza

        return piezaAMover.ejecutarMovimientoSegunEstrategia(coordenadaInicial, coordenadaFinal, this);
    }

    public Pieza setPieza(Coordenada2D coordenada, Pieza piezaAColocar) {
        int row = coordenada.getRow();
        int col = coordenada.getCol();
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

    public Pieza removePieza(Coordenada2D coordenada) {
        int row = coordenada.getRow();
        int col = coordenada.getCol();
        if (!esCoordenadaValida(row, col)) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }

        Casillero casillero = getCasillero(row, col);

        if(!casillero.estaOcupado()){
            return null;
        }

        // Remover la pieza del casillero. El metodo del casillero devuelve la pieza que se elimina
        Pieza piezaARemover = casillero.removerPieza();

        // Actualizar las amenazas de la pieza a remover (solo las activas)
        quitarAmenazasDesdeCoordenadasHastaLimiteUOcupado(piezaARemover.getAmenazasGeneradas(), row, col);

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

    public boolean caminoEstaDesocupado(int rowInicial, int colInicial, int rowFinal, int colFinal) {
        int incrementoFila = Integer.compare(rowFinal, rowInicial);  // -1, 0, 1 según la dirección
        int incrementoColumna = Integer.compare(colFinal, colInicial);  // -1, 0, 1 según la dirección

        int filaActual = rowInicial + incrementoFila;
        int colActual = colInicial + incrementoColumna;

        // Recorremos el camino hasta la posición final, sin incluir las posiciones inicial y final
        while (filaActual != rowFinal || colActual != colFinal) {
            if (getCasillero(filaActual, colActual).estaOcupado()) {
                return false;  // El camino está bloqueado
            }

            filaActual += incrementoFila;
            colActual += incrementoColumna;
        }

        return true;  // El camino está libre
    }

    public boolean caminoEstaAmenazado(int rowInicial, int colInicial, int rowFinal, int colFinal) {
        int incrementoFila = Integer.compare(rowFinal, rowInicial);  // -1, 0, 1 según la dirección
        int incrementoColumna = Integer.compare(colFinal, colInicial);  // -1, 0, 1 según la dirección

        int filaActual = rowInicial + incrementoFila;
        int colActual = colInicial + incrementoColumna;

        // Recorremos el camino hasta la posición final, sin incluir las posiciones inicial y final
        while (filaActual != rowFinal || colActual != colFinal) {
            if (getCasillero(filaActual, colActual).estaAmenazado()) {
                return true;  // El camino está amenazado
            }

            filaActual += incrementoFila;
            colActual += incrementoColumna;
        }

        return false;  // El camino no tiene amenazas
    }

    public void setPiezaInicial(int row, int col, Pieza pieza) {
        tablero[row][col].setPieza(pieza);
    }

    public Integer getDimension() {
        return dimensiones;
    }

    public boolean casilleroLibre(Integer i, Integer j) {
        return getPieza(i,j).isEmpty();
    }
}





