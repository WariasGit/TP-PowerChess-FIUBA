package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import java.util.Optional;
import java.util.ArrayList;

/**
 * La clase Tablero representa un tablero de ajedrez con sus piezas y funciones para gestionar movimientos, amenazas y posiciones válidas.
 * Esta clase incluye métodos para:
 * - Filtrar amenazas y movimientos válidos de cada pieza según su tipo y posición.
 * - Determinar posiciones válidas de movimiento y captura para piezas específicas, incluyendo reglas especiales para peones y otras piezas.
 * - Generar una representación en formato FEN del estado actual del tablero.
 * - Obtener piezas y casilleros en posiciones específicas del tablero.
 * - Identificar y establecer las posiciones iniciales de los reyes negro y blanco.
 * La clase también considera el color de cada pieza para definir posiciones de captura,
 * lo que permite evaluar el estado de amenaza y los movimientos posibles de las piezas en función de las reglas de ajedrez.
 */
public class TableroCuadrado {
    private final Casillero[][] tablero;
    static private final Integer dimensiones = Configuracion.TamanioVentana.DIMENSION_TABLERO;

    public TableroCuadrado() {
        tablero = new Casillero[dimensiones][dimensiones];
        inicializarTableroAjedrez();
    }

    /**
     * Inicializa el tablero en un patrón de ajedrez, alternando colores en cada casillero.
     */
    private void inicializarTableroAjedrez(){
        for (int fila = Configuracion.TamanioVentana.CERO; fila < dimensiones; fila++) {
            for (int columna = Configuracion.TamanioVentana.CERO; columna < dimensiones; columna++) {
                Configuracion.ColoresJugadores color = (fila + columna) % Configuracion.TamanioVentana.DOS == Configuracion.TamanioVentana.CERO ? Configuracion.ColoresJugadores.BLANCO : Configuracion.ColoresJugadores.NEGRO;
                  tablero[fila][columna] = new Casillero(color);
            }
        }
    }

    /**
     * Mueve una pieza de una posición inicial a una posición final en el tablero.
     * Valida que la posición de destino sea accesible y que no esté protegida por un escudo.
     * @return piezaMovida tras aplicar la estrategia de movimiento.
     * @throws IllegalArgumentException si la pieza no puede moverse o la posición está protegida.
     */
    public Pieza moverPieza(int filaInicial, int columnaInicial, int filaFinal, int columnaFinal) {
        Casillero casilleroInicial = getCasillero(filaInicial, columnaInicial);
        Pieza piezaAMover = casilleroInicial.getPieza();
        //Verificamos que la posicion de destino este dentro de los movimientos posibles.
        if(!piezaAMover.puedeMoverseA(filaFinal, columnaFinal)){
            throw new IllegalArgumentException("La pieza no puede moverse a esa posicion");
        }
        Coordenada2D coordenadaInicial = new Coordenada2D(filaInicial, columnaInicial);
        Coordenada2D coordenadaFinal = new Coordenada2D(filaFinal, columnaFinal);

        //ESTO HAY QUE CAMBIARLO !!!!
        Casillero casilleroFinal = getCasillero(filaFinal,columnaFinal);
        if (casilleroFinal.estaOcupado()) {
            Pieza piezaAComer = casilleroFinal.getPieza();
            if (piezaAComer.tieneEscudo()) {
                throw new IllegalArgumentException("La pieza esta protegida por escudo");
            }
        }
        piezaAMover.actualizarPosicion(coordenadaFinal);
        return piezaAMover.ejecutarMovimientoSegunEstrategia(coordenadaInicial, coordenadaFinal, this);
    }

    /**
     * Coloca una pieza en el tablero en la coordenada especificada.
     * Actualiza las amenazas generadas y bloqueadas en el tablero.
     * @param coordenada Coordenada de destino para la pieza.
     * @param piezaAColocar Pieza que se colocará en la posición dada.
     * @return Pieza que ocupaba previamente el casillero (si se da una captura).
     * @throws IllegalArgumentException si la coordenada es inválida.
     */
    public Pieza setPieza(Coordenada2D coordenada, Pieza piezaAColocar) {
        int row = coordenada.getFila();
        int col = coordenada.getColumna();
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
        piezaAColocar.marcarComoMovida();
        piezaAColocar.actualizarPosicion(coordenada);
        // Actualizamos las amenazas generadas por la nueva pieza
        agregarAmenazasDesdeCoordenadasHastaLimiteUOcupado(casillero.getAmenzasDePiezaActual(),row, col);
        return piezaComida;
    }

    /**
     * Elimina una pieza de una coordenada específica en el tablero.
     * Actualiza las amenazas en el tablero según corresponda.
     * @param coordenada Coordenada de la pieza que se desea remover.
     * @return Pieza removida del tablero o null si el casillero está vacío.
     * @throws IllegalArgumentException si la coordenada es inválida.
     */
    public Pieza removerPieza(Coordenada2D coordenada) {
        int row = coordenada.getFila();
        int col = coordenada.getColumna();
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

    public boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < dimensiones && columna >= 0 && columna < dimensiones;
    }

    /**
     * Elimina las amenazas en una dirección específica desde una coordenada dada.
     * Elimina la amenaza hasta el límite del tablero o hasta que se encuentre un casillero ocupado.
     * @param amenazasAQuitar Lista de amenazas a eliminar.
     * @param fila Fila de inicio de la amenaza.
     * @param columna Columna de inicio de la amenaza.
     */
    private void quitarAmenazasDesdeCoordenadasHastaLimiteUOcupado(ArrayList<Amenaza> amenazasAQuitar, int fila, int columna) {
        for (Amenaza amenaza : amenazasAQuitar) {
            int[] direccion = amenaza.getDireccion();
            ArrayList<Amenaza> amenazaAQuitar = new ArrayList<Amenaza>();
            amenazaAQuitar.add(amenaza);
            // Calcular hasta dónde se extiende la amenaza
            for (int i = 0; i < amenaza.getCantidadCasilleros(); i++) {
                // Calcular nuevas coordenadas
                int nuevaFila = fila + (i + 1) * direccion[0]; // Se suma 1 para no afectar el casillero donde se colocó la pieza
                int nuevaColumna = columna + (i + 1) * direccion[1];
                // Verificar límites del tablero
                if (esCoordenadaValida(nuevaFila, nuevaColumna)) {
                    Casillero casilleroAmenazado = this.getCasillero(nuevaFila, nuevaColumna);
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

    /**
     * Agrega amenazas en una dirección específica desde una coordenada dada.
     * Agrega la amenaza hasta el límite del tablero o hasta que se encuentre un casillero ocupado.
     * @param amenazasAAgregar Lista de amenazas a agregar.
     * @param fila Fila de inicio de la amenaza.
     * @param columna Columna de inicio de la amenaza.
     */
    private void agregarAmenazasDesdeCoordenadasHastaLimiteUOcupado(ArrayList<Amenaza> amenazasAAgregar, int fila, int columna) {
        for (Amenaza amenaza : amenazasAAgregar) {
            int[] direccion = amenaza.getDireccion();
            ArrayList<Amenaza> amenazaAAgregar = new ArrayList<Amenaza>();
            amenazaAAgregar.add(amenaza);
            // Calcular hasta dónde se extiende la amenaza
            for (int i = 0; i < amenaza.getCantidadCasilleros(); i++) {
                // Calcular nuevas coordenadas
                int nuevaFila = fila + (i + 1) * direccion[0]; // Se suma 1 para no afectar el casillero donde se colocó la pieza
                int nuevaColumna = columna + (i + 1) * direccion[1];
                // Verificar límites del tablero
                if (esCoordenadaValida(nuevaFila, nuevaColumna)) {
                    Casillero casilleroAmenazado = this.getCasillero(nuevaFila, nuevaColumna);
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

    /**
     * Verifica si el camino entre dos casilleros está desocupado, excluyendo las posiciones inicial y final.
     * @return true si el camino está libre, false si algún casillero está ocupado.
     */
    public boolean caminoEstaDesocupado(int filaInicial, int columnaInicial, int filaFinal, int columnaFinal) {
        int incrementoFila = Integer.compare(filaFinal, filaInicial);  // -1, 0, 1 según la dirección
        int incrementoColumna = Integer.compare(columnaFinal, columnaInicial);  // -1, 0, 1 según la dirección
        int filaActual = filaInicial + incrementoFila;
        int colActual = columnaInicial + incrementoColumna;
        // Recorremos el camino hasta la posición final, sin incluir las posiciones inicial y final
        while (filaActual != filaFinal || colActual != columnaFinal) {

            //VERIFICAR VUELO VOLAR
            if (getCasillero(filaActual, colActual).estaOcupado()) {
                return false;  // El camino está bloqueado
            }
            filaActual += incrementoFila;
            colActual += incrementoColumna;
        }
        return true;  // El camino está libre
    }

    /**
     * Coloca una pieza en una posición inicial sin realizar validaciones adicionales.
     * @param fila Fila de la posición inicial.
     * @param columna Columna de la posición inicial.
     * @param pieza Pieza que se colocará.
     */
    public void setPiezaInicial(int fila, int columna, Pieza pieza) {
        tablero[fila][columna].setPieza(pieza);
    }

    public boolean casilleroLibre(Integer fila, Integer columna) {
        return getPieza(fila,columna).isEmpty();
    }

    /**
     * Calcula y establece los movimientos posibles iniciales de todas las piezas en el tablero.
     */
    public void calcularMovimientosPosiblesIniciales(){
        for(int fila = 0; fila < dimensiones; fila++){
            for(int columna = 0; columna < dimensiones; columna++){
                actualizarMovimientosPieza(fila, columna);
            }
        }
    }

    /**
     * Actualiza los movimientos posibles de una pieza en una coordenada específica.
     * @param fila Fila de la pieza.
     * @param columna Columna de la pieza.
     */
    public void actualizarMovimientosPieza(int fila, int columna) {
        Optional<Pieza> pieza = getPieza(fila, columna);
        pieza.ifPresent(value -> filtrarAmenazasYPosiciones(fila, columna, value));
    }

    /**
     * Filtra las amenazas y posiciones válidas de una pieza en función de su tipo.
     * @param fila Fila actual de la pieza en el tablero.
     * @param columna Columna actual de la pieza en el tablero.
     * @param piezaActual Pieza que se está evaluando.
     */
    private void filtrarAmenazasYPosiciones(int fila, int columna, Pieza piezaActual) {
        if (piezaActual.getTipoDePieza().equals(Constantes.PEON)) {
            filtrarAmenazasYPosicionesPeon(fila, columna, piezaActual);
        } else {
            filtrarAmenazasYPosicionesGenerales(fila, columna, piezaActual);
        }
    }

    private void filtrarAmenazasYPosicionesPeon(int fila, int columna, Pieza peon){
        PeonBase peonBase = (PeonBase) peon;
        peonBase.quitarOReponerMovimientoDoblePeon();
        ArrayList<Amenaza> amenazas = peon.getAmenazasGeneradas();
        ArrayList<int[]> movimientos = peon.getDireccionesDeMovimiento();
        ArrayList<int[]> posicionesValidas = new ArrayList<>();
        for (Amenaza amenaza : amenazas) {
            int[] direccion = amenaza.getDireccion();
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

    /**
     * Genera una representación FEN del estado actual del tablero.
     * Cuenta los casilleros vacíos consecutivos y usa caracteres FEN para piezas en el tablero.
     * @return  Cadena en formato FEN que representa el estado del tablero.
     */
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

    public Casillero getCasillero(int fila, int columna) {
        return this.tablero[fila][columna];
    }

    public Optional<Pieza> getPieza(Integer i, Integer j) {
        return Optional.ofNullable(tablero[i][j].getPieza());
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





