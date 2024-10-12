package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.io.*;
import java.util.*;

public class Juego {
    private Configuracion.EstadoJuego estado;
    private List<Jugador> jugadores;
    private Turno turno;
    private TableroCuadrado tablero;
    private String ganador;
    private int contadorMovimientosParaTablas;
    private int contadorMovimientosParaChequearPosiciones;
    private int contadorMovimientosTotales;
    private HashMap<String, Integer> historialPosiciones;
    private int piezasEnJuego;


    public Juego(List<Jugador> jugadores) throws IOException {
        this.estado = Configuracion.EstadoJuego.EN_JUEGO;
        this.jugadores = jugadores;
        turno = new Turno(jugadores);
        tablero = new TableroCuadrado();
        contadorMovimientosParaTablas = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
        contadorMovimientosParaChequearPosiciones = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
        contadorMovimientosTotales = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
        piezasEnJuego = Constantes.CANTIDAD_PIEZAS_INICIALES;
        historialPosiciones = new HashMap<>();
    }

    public void gestionarRendicion() {
        terminarPartida();
        ganador = turno.getNombreOponente();
    }

    public void establecerJaqueMate() {
        ganador = turno.getNombreTurno();
        estado = Configuracion.EstadoJuego.FINALIZADO;
    }

    public void establecerTablas() {
        estado = Configuracion.EstadoJuego.TABLAS;
        System.out.println("Tablas");
    }

    public void terminarPartida() {estado = Configuracion.EstadoJuego.FINALIZADO;}

    public String getNombreJugadorBlancas() {
        return jugadores.get(Configuracion.Jugadores.BLANCAS).getNombre();
    }

    public String getNombreJugadorNegras() {
        return jugadores.get(Configuracion.Jugadores.NEGRAS).getNombre();
    }

    public String getNombreJugadorActual() {return turno.getTurno().getNombre();}

    public TableroCuadrado getTablero() {return tablero;}

    public ArrayList<Jugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    public Boolean mover(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        try {
            Pieza piezaComida = tablero.moverPieza(origenFila, origenColumna, destinoFila, destinoColumna);
            contadorMovimientosTotales++;
            if(piezaComida != null){
                quitarPiezaDeJuador(piezaComida);
                restarUnaPieza();
                reiniciarContadorMovimientoParaTablas();
                reiniciarContadorMovimientosParaChequearPosiciones();
                limpiarHistorialPosiciones();
            }else{
                gestionarContadorMovimientosParaTablas(destinoFila, destinoColumna);
                if (contadorMovimientosTotales >= Constantes.CANTIDAD_MOVIMIENTOS_PARAGUARDAR_POSICIONES){
                    guardarEstadoTablero();
                }
            }
            gestionarTablas();
            imprimirTablero();
            //imprimirEstadoDebug();
            return true;
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            return false;
        }
    }



    private void imprimirTablero() {
        Casillero[][] casilleros = tablero.getTablero();
        int dimension = tablero.getDimension();
        // Imprimir los índices de las columnas
        System.out.print("   ");
        for (int col = 0; col < dimension; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();
        // Imprimir el tablero con bordes
        for (int i = 0; i < dimension; i++) {
            // Imprimir índice de la fila
            System.out.print(i + " |");
            for (int j = 0; j < dimension; j++) {
                Pieza pieza = casilleros[i][j].getPieza();
                if (pieza != null) {
                    System.out.print(" " + pieza.getCaracterFEN() + " ");
                } else {
                    System.out.print(" . ");  // Espacio vacío
                }
            }
            System.out.println("| " + i);  // Cerrar el borde de la fila
        }
        // Imprimir los índices de las columnas nuevamente
        System.out.print("   ");
        for (int col = 0; col < dimension; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();
    }




    private void quitarPiezaDeJuador(Pieza piezaComida) {
        if(piezaComida.getColor() == Configuracion.ColoresJugadores.BLANCO) {
            jugadores.get(Configuracion.Jugadores.BLANCAS).quitarPiezaEnJuego(piezaComida);
        } else {
            jugadores.get(Configuracion.Jugadores.NEGRAS).quitarPiezaEnJuego(piezaComida);
        }
    }

    private void restarUnaPieza() {
        piezasEnJuego--;
    }

    private void reiniciarContadorMovimientosParaChequearPosiciones() {
        contadorMovimientosParaChequearPosiciones = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
    }

    private void limpiarHistorialPosiciones() {
        historialPosiciones.clear();
    }

    private void guardarEstadoTablero() {
        String estadoTablero = tablero.estadoActualTablero();
        historialPosiciones.put(estadoTablero, historialPosiciones.getOrDefault(estadoTablero, Constantes.CERO) + Constantes.UNO);
        contadorMovimientosParaChequearPosiciones++;
    }

    private void reiniciarContadorMovimientoParaTablas() {
        contadorMovimientosParaTablas = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
    }

    private void gestionarContadorMovimientosParaTablas(int fila, int columna) {
        if(movioPeon(fila, columna)){
            reiniciarContadorMovimientoParaTablas();
        } else {
            contadorMovimientosParaTablas++;
        }
    }

    private boolean movioPeon(int fila, int columna) {
        Optional<Pieza> pieza = tablero.getPieza(fila, columna);
        if (pieza.isPresent()) {
            return Objects.equals(pieza.get().getTipoDePieza(), Constantes.PEON);
        }
        return false;
    }

    public void cambiarTurno() {turno.gestionarTurno();}


    /*Implemento los metodos "leerArchivoFen" y "setearPiezasDesdeFEN"
    para cargar una partida desde un archivo de texto,la notacion de FEN
    es una forma de almacenar el estado de una partida en caso de ser guardada*/

    public static String leerArchivoFen(String rutaArchivo) throws IOException {
        InputStream inputStream = Constantes.class.getClassLoader().getResourceAsStream(rutaArchivo);

        if (inputStream == null) {
            throw new FileNotFoundException("InputStream is null, Archivo no encontrado: " + rutaArchivo);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea = reader.readLine();
            if (linea == null || linea.length() < 4) {
                throw new IllegalArgumentException("La cadena FEN no es válida");
            }
            return linea;
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                throw new FileNotFoundException("Archivo no encontrado: " + rutaArchivo);
            } else {
                throw new IOException("Error al leer el archivo: " + rutaArchivo, e);
            }
        }
    }

    public void setearPiezasDesdeFEN(String cadenaFen) {
        String[] secciones = cadenaFen.split(" ");
        String posicionPiezas = secciones[0];
        int fila = 0;
        int columna = 0;
        for (char caracter : posicionPiezas.toCharArray()) {
            if (caracter == '/') {
                fila++;
                columna = 0;
            } else if (Character.isDigit(caracter)) {
                columna += Character.getNumericValue(caracter);
            } else {
                Pieza pieza = Configuracion.getPieza(caracter);
                if (pieza != null) {
                    tablero.setPiezaInicial(fila, columna, pieza);
                    guardarPiezaJugador(pieza);
                }
                columna++;
            }
        }
    }

    private void guardarPiezaJugador(Pieza pieza) {
        if(pieza.getColor() == Configuracion.ColoresJugadores.BLANCO) {
            jugadores.get(Configuracion.Jugadores.BLANCAS).setPiezasEnJuego(pieza);
        } else {
            jugadores.get(Configuracion.Jugadores.NEGRAS).setPiezasEnJuego(pieza);
        }
    }

    public void cargarPartida(String path) throws IOException {
        String linea = leerArchivoFen(path);
        setearPiezasDesdeFEN(linea);
    }

    public Optional<Pieza> getPiezaActual(Integer i, Integer j) {
        return tablero.getPieza(i, j);
    }

    public void actualizarMovimientosPieza(int fila, int columna) {
        tablero.actualizarMovimientosPieza(fila, columna);
    }

    private Boolean jugadorActualEstaEnJaque(){
        return turno.estaEnJaqueJugadorActual();
    }

    private Boolean jugadorActualTieneMovimientos(){
        return turno.tieneMovimientosJugadorActual();
    }

    private void tablasPorAhogado(){
        if(!jugadorActualTieneMovimientos() && !jugadorActualEstaEnJaque()){
            establecerTablas();
        }
    }

    private Boolean jugadoresTienenMaterialMinimo(){
        boolean continuar = false;
        for (Jugador jugador : jugadores) {
            if(jugador.tieneMaterialSuficiente()){
                continuar = true;
            }
        }
        return continuar;
    }

    private void tablasPorMaterialInsuficiente(){
        if(!jugadoresTienenMaterialMinimo()){
            establecerTablas();
        }
    }

    private void tablasPorMovimientos(){
        if(contadorMovimientosParaTablas == Constantes.CANTIDAD_MOVIMIENTOS_PARA_TABLAS){
            establecerTablas();
        }
    }

    private boolean hayMovimientosRepetidos() {
        for (Integer contador : historialPosiciones.values()) {
            if (contador >= 3) {
                return true;
            }
        }
        return false;
    }

    private void tablasPorMovimientosRepetidos(){
        if (hayMovimientosRepetidos()) {
            establecerTablas();
        }
    }

    public void gestionarTablas() {
        tablasPorAhogado();
        if(piezasEnJuego <= Constantes.MINIMO_PIEZAS_PARA_CHEQUEAR_TABLAS) {
            tablasPorMaterialInsuficiente();
        }
        tablasPorMovimientos();
        if(contadorMovimientosParaChequearPosiciones >= Constantes.CANTIDAD_MOVIMIENTOS_MINIMOS_PARA_CHEQUEAR_POSICIONES) {
            tablasPorMovimientosRepetidos();
        }
    }

    public void guardarPartida() throws IOException {
        String estadoTablero = tablero.estadoActualTablero();
        try {
            FileWriter escritorArchivo = new FileWriter(Constantes.RUTA_ARCHIVO_GUARDAR_PARTIDA);
            BufferedWriter bufferEscritor = new BufferedWriter(escritorArchivo);
            bufferEscritor.write(estadoTablero);
            bufferEscritor.close();
            System.out.println("Archivo guardado exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo.");
            e.printStackTrace();
        }
    }

    public Configuracion.ColoresJugadores getColorJugadorActual() {
        return turno.getColorJugadorActual();
    }




    public void imprimirEstadoDebug() {
        System.out.println("----- Estado de Debug -----");
        System.out.println("Movimientos para tablas: " + contadorMovimientosParaTablas);
        System.out.println("Movimientos para chequear posiciones: " + contadorMovimientosParaChequearPosiciones);
        System.out.println("Movimientos totales: " + contadorMovimientosTotales);
        System.out.println("Tamaño del historial de posiciones: " + historialPosiciones.size());
        System.out.println("Piezas en juego: " + piezasEnJuego);
        System.out.println("---------------------------");
    }

}

