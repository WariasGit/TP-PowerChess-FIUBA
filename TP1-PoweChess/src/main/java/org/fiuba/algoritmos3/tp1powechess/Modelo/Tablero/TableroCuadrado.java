package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import java.util.Optional;
import java.util.ArrayList;

public class TableroCuadrado {
    private final Casillero[][] tablero;
    static private final Integer dimensiones = Configuracion.TamanioVentana.DIMENSION_TABLERO;

    //Esto es momentaneo, para ver algo
    public Casillero[][] getTablero() {
        return tablero;
    }

    public TableroCuadrado() {
        tablero = new Casillero[dimensiones][dimensiones];
        inicializarTableroAjedrez();
    }

    private void inicializarTableroAjedrez(){
        for (int row = 0; row < dimensiones; row++) {
            for (int col = 0; col < dimensiones; col++) {
                Configuracion.ColoresJugadores color = (row + col) % 2 == 0 ? Configuracion.ColoresJugadores.BLANCO : Configuracion.ColoresJugadores.NEGRO;
                Coordenada2D posicion = new Coordenada2D(row, col);
                  tablero[row][col] = new Casillero(color, posicion);
            }
        }
    }

    public Casillero getCasillero(int row, int col) {
        return this.tablero[row][col];
    }

    public Optional<Pieza> getPieza(Integer i, Integer j) {
        return Optional.ofNullable(tablero[i][j].getPieza());
    }

    public Pieza moverPieza(int filaInicial, int columnaInicial, int filaFinal, int columnaFinal) {
        Casillero casilleroInicial = getCasillero(filaInicial, columnaInicial);
        Pieza piezaAMover = casilleroInicial.getPieza();
        //Verificamos que la posicion de destino este dentro de los movimientos posibles.
//        if(!piezaAMover.puedeMoverseA(filaFinal, columnaFinal)){
//            throw new IllegalArgumentException("La pieza no puede moverse a esa posicion");
//        }
        if(!piezaAMover.puedeMoverseA(filaFinal, columnaFinal)){
            throw new IllegalArgumentException("La pieza no puede moverse a esa posicion");
        }
        if(piezaAMover.tieneFreeze()) {
            throw new IllegalArgumentException("La pieza se encuentra congelada por el Poder de Freeze.");
        }
        Coordenada2D coordenadaInicial = new Coordenada2D(filaInicial, columnaInicial);
        Coordenada2D coordenadaFinal = new Coordenada2D(filaFinal, columnaFinal);
        piezaAMover.actualizarPosicion(coordenadaFinal);


        //ESTO HAY QUE CAMBIARLO !!!!
        Casillero casilleroFinal = getCasillero(filaFinal,columnaFinal);
        if (casilleroFinal.estaOcupado()) {
            Pieza piezaAComer = casilleroFinal.getPieza();
            if (piezaAComer.tieneEscudo()) {
                throw new IllegalArgumentException("La pieza esta protegida por escudo");
            }
        }
        piezaAMover.setPosicion(coordenadaFinal);
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
            System.out.println("El casillero no esta ocupado.");
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

    public boolean esCoordenadaValida(int row, int col) {
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

    public void setPiezaInicial(int row, int col, Pieza pieza) {
        tablero[row][col].setPieza(pieza);
    }

    public Integer getDimension() {
        return dimensiones;
    }

    public boolean casilleroLibre(Integer i, Integer j) {
        return getPieza(i,j).isEmpty();
    }

    public void calcularMovimientosPosiblesIniciales(){
        for(int fila = 0; fila < dimensiones; fila++){
            for(int columna = 0; columna < dimensiones; columna++){
                actualizarMovimientosPieza(fila, columna);
            }
        }
    }

    public void actualizarMovimientosPieza(int fila, int columna) {
        Optional<Pieza> pieza = getPieza(fila, columna);
        pieza.ifPresent(value -> filtrarAmenazasYPosiciones(fila, columna, value));
    }

    private void filtrarAmenazasYPosiciones(int fila, int columna, Pieza piezaActual) {
        if (piezaActual.getTipoDePieza().equals(Constantes.PEON)) {
            filtrarAmenazasYPosicionesPeon(fila, columna, piezaActual);
        } else {
            filtrarAmenazasYPosicionesGenerales(fila, columna, piezaActual);
        }
    }

    private void filtrarAmenazasYPosicionesPeon(int fila, int columna, Pieza peon){
        PeonBase peonBase = (PeonBase) peon;
        peonBase.quitarMovimientoDoblePeon();
        ArrayList<Amenaza> amenazas = peon.getAmenazasGeneradas();
        ArrayList<int[]> movimientos = peon.getDireccionesDeMovimiento();
        ArrayList<int[]> posicionesValidas = new ArrayList<>();
        for (Amenaza amenaza : amenazas) {
            int[] direccion = amenaza.getDireccion();
            int maxCasilleros = amenaza.getCantidadCasilleros();
            int nuevaFila = fila + direccion[0] * peon.getMaxDistanciaDeAmenaza();
            int nuevaColumna = columna + direccion[1] * peon.getMaxDistanciaDeAmenaza();
            if (esCoordenadaValida(nuevaFila, nuevaColumna) && !casilleroLibre(nuevaFila, nuevaColumna)) {
                Optional<Pieza> piezaEnCamino = getPieza(nuevaFila, nuevaColumna);
                if (piezaEnCamino.isPresent()) {
                    Pieza piezaTablero = piezaEnCamino.get();
                    if (piezaTablero.getColor() != peon.getColor()) {
                        posicionesValidas.add(new int[]{nuevaFila, nuevaColumna});
                    }
                }
            }
        }
        for (int[] direccionPeon : movimientos) {
            int filaNueva = fila + direccionPeon[Constantes.COORDENADA_FILA];
            int columnaNueva = columna + direccionPeon[Constantes.COORDENADA_COLUMNA];
            if (esCoordenadaValida(filaNueva, columnaNueva) && casilleroLibre(filaNueva, columnaNueva)) {
                posicionesValidas.add(new int[]{filaNueva, columnaNueva});
            }
        }
        peon.setMovimientosPosibles(posicionesValidas);
    }

    private void filtrarAmenazasYPosicionesGenerales(int fila, int columna, Pieza piezaActual) {
        ArrayList<Amenaza> amenazas = piezaActual.getAmenazasGeneradas();
        ArrayList<int[]> posicionesValidas = new ArrayList<>();
        for (Amenaza amenaza : amenazas) {
            int[] direccion = amenaza.getDireccion();
            int maxCasilleros = amenaza.getCantidadCasilleros();
            for (int i = 1; i <= maxCasilleros; i++) {
                int nuevaFila = fila + direccion[0] * i;
                int nuevaColumna = columna + direccion[1] * i;
                if (esCoordenadaValida(nuevaFila, nuevaColumna)) {
                    Optional<Pieza> piezaEnCamino = getPieza(nuevaFila, nuevaColumna);
                    if (piezaEnCamino.isPresent()) {
                        Pieza piezaTablero = piezaEnCamino.get();
                        if (piezaTablero.getColor() != piezaActual.getColor()) {
                            posicionesValidas.add(new int[]{nuevaFila, nuevaColumna});
                        }
                        break;
                    } else {
                        posicionesValidas.add(new int[]{nuevaFila, nuevaColumna});
                    }
                }
            }
        }
        piezaActual.setMovimientosPosibles(posicionesValidas);
    }

    public String estadoActualTablero(){
        StringBuilder estado = new StringBuilder();
        int casillerosVacios = Constantes.CERO;
        for (int fila = 0; fila < dimensiones; fila++) {
            for (int columna = 0; columna < dimensiones; columna++) {
                Optional<Pieza> pieza = getPieza(fila, columna);
                if (pieza.isPresent()) {
                    if(casillerosVacios > Constantes.CERO){
                        estado.append(casillerosVacios);
                        casillerosVacios = Constantes.CERO;
                    }
                    estado.append(pieza.get().getCaracterFEN());
                }else{
                    casillerosVacios++;
                }
            }
            if(casillerosVacios > Constantes.CERO){
                estado.append(casillerosVacios);
                casillerosVacios = Constantes.CERO;
            }
            estado.append("/");
        }
        return estado.toString();
    }

    public Optional<Pieza> getReyNegroPosicionInicial() {
        return getPieza(Configuracion.PosicionInicialReyes.FILA_REY_NEGRO, Configuracion.PosicionInicialReyes.COLUMNA_REY_NEGRO);
    }

    public Optional<Pieza> getReyBlancoPosicionInicial() {
        return getPieza(Configuracion.PosicionInicialReyes.FILA_REY_BLANCO, Configuracion.PosicionInicialReyes.COLUMNA_REY_BLANCO);
    }

    public void setearCasillerosReyes(){
        Optional<Pieza> reyNegroOpcional = getReyNegroPosicionInicial();
        Optional<Pieza> reyBlancoOpcional = getReyBlancoPosicionInicial();
        if (reyNegroOpcional.isPresent() && reyBlancoOpcional.isPresent()) {
            Rey reyNegro = (Rey) reyNegroOpcional.get();
            Rey reyBlanco = (Rey) reyBlancoOpcional.get();
            Casillero casilleroReyNegro = getCasillero(Configuracion.PosicionInicialReyes.FILA_REY_NEGRO, Configuracion.PosicionInicialReyes.COLUMNA_REY_NEGRO);
            Casillero casilleroReyBlanco = getCasillero(Configuracion.PosicionInicialReyes.FILA_REY_BLANCO, Configuracion.PosicionInicialReyes.COLUMNA_REY_BLANCO);
            reyNegro.actualizarCasilleroActual(casilleroReyNegro);
            reyBlanco.actualizarCasilleroActual(casilleroReyBlanco);
        }
    }
}





