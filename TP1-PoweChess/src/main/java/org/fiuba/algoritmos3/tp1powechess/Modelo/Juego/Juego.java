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
    private final List<Jugador> jugadores;
    private final Turno turno;
    private final TableroCuadrado tablero;
    private String ganador;
    private int contadorMovimientosParaTablas;
    private int contadorMovimientosParaChequearPosiciones;
    private int contadorMovimientosTotales;
    private final HashMap<String, Integer> historialPosiciones;
    private int piezasEnJuego;
    private final GestorDeTablas gestorDeTablas;
    private final GestorDeJaque gestorDeJaque;
    private final GestorDeEnroque gestorDeEnroque;
    private Pieza ultimaPiezaCapturada;


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
        gestorDeTablas = new GestorDeTablas();
        gestorDeJaque = new GestorDeJaque();
        gestorDeEnroque = new GestorDeEnroque();
        gestorDeJaque.setTablero(tablero);
        gestorDeEnroque.setTablero(tablero);
    }

    public boolean sigueElJuego(){return this.estado == Configuracion.EstadoJuego.EN_JUEGO;}

    public void gestionarRendicion() {
        ganador = turno.getNombreOponente();
        terminarPartida();
    }

    public void establecerTablas(){this.estado = Configuracion.EstadoJuego.TABLAS;}

    public void establecerJaqueMate(){
        ganador = turno.getNombreOponente();
        this.estado = Configuracion.EstadoJuego.JAQUE_MATE;
    }

    public void terminarPartida() {estado = Configuracion.EstadoJuego.FINALIZADO;}

    public Boolean mover(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        boolean sePuedeMover = false;
        try {
            if(esturnoDeMover(origenFila, origenColumna)){
                Pieza piezaComida = tablero.moverPieza(origenFila, origenColumna, destinoFila, destinoColumna);
                this.ultimaPiezaCapturada = piezaComida;
                gestionarJaque();
                //Se verifica si luego de mover, el jugador continua en jaque, o si un movimiento lo pone en jaque.
                if(turno.estaEnJaqueJugadorActual()){
                    revertirMovimiento(origenFila, origenColumna, destinoFila, destinoColumna);
                }
                else{
                    aplicarLogicaDeMovimientos(piezaComida, destinoFila, destinoColumna);
                    sePuedeMover = true;
                }
            }
            else{
                System.out.println("Espera a tu turno para realizar un movimiento");
            }
        }
        catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
        return sePuedeMover;
    }

    private boolean esturnoDeMover(int fila, int columna){
        Optional<Pieza> piezaAMover = tablero.getPieza(fila, columna);
        Pieza piezaActual = null;
        if(piezaAMover.isPresent()){
            piezaActual = piezaAMover.get();
        }
        return turno.estaPiezaEsDelJugadorActual(piezaActual);
    }

    private void revertirMovimiento(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        System.out.print("Debe realizar un movimiento para evitar el Jaque");
        Optional<Pieza> piezaMovida = tablero.getPieza(destinoFila, destinoColumna);
        Coordenada2D posicionAnterior = new Coordenada2D(origenFila, origenColumna);
        if(piezaMovida.isPresent()){
            Pieza piezaActual = piezaMovida.get();
            //Se revierte el movimiento
            tablero.setPieza(posicionAnterior, piezaActual);
        }
    }

    private void aplicarLogicaDeMovimientos(Pieza piezaComida, int destinoFila, int destinoColumna) {
        contadorMovimientosTotales++;
        calcularMovimientosPosiblesIniciales();
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
    }

    private void quitarPiezaDeJuador(Pieza piezaComida) {
        if(piezaComida.getColor() == Configuracion.ColoresJugadores.BLANCO) {
            jugadores.get(Configuracion.Jugadores.BLANCAS).quitarPiezaEnJuego(piezaComida);
        } else {
            jugadores.get(Configuracion.Jugadores.NEGRAS).quitarPiezaEnJuego(piezaComida);
        }
    }

    private void restarUnaPieza() {piezasEnJuego--;}

    private void reiniciarContadorMovimientosParaChequearPosiciones() {contadorMovimientosParaChequearPosiciones = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;}

    private void limpiarHistorialPosiciones() {historialPosiciones.clear();}

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

    public void guardarPartida() {
        String estadoTablero = tablero.estadoActualTablero();
        GestorDeArchivos.guardarFen(estadoTablero);
    }

    public void cargarPartida(String path) throws IOException {
        String cadenaFen = GestorDeArchivos.leerArchivoFen(path);
        setearPiezasDesdeFEN(cadenaFen);
        tablero.setearCasillerosReyes();
        guardarReferenciaDeReyes();
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
                    Coordenada2D posicionActual = new Coordenada2D(fila, columna);
                    pieza.setPosicionActual(posicionActual);
                    guardarPiezaJugador(pieza);
                }
                columna++;
            }
        }
        calcularMovimientosPosiblesIniciales();
    }

    private void calcularMovimientosPosiblesIniciales() {this.tablero.calcularMovimientosPosiblesIniciales();}

    private void guardarReferenciaDeReyes(){
        Optional<Pieza> reyBlancoOpcional = tablero.getReyBlancoPosicionInicial();
        Optional<Pieza> reyNegroOpcional = tablero.getReyNegroPosicionInicial();
        ArrayList<Rey> reyes = new ArrayList<>();
        if(reyBlancoOpcional.isPresent()){
            Rey reyBlanco = (Rey) reyBlancoOpcional.get();
            gestorDeJaque.setReyBlanco(reyBlanco);
            reyes.add(reyBlanco);
        }
        if(reyNegroOpcional.isPresent()){
            Rey reyNegro = (Rey) reyNegroOpcional.get();
            gestorDeJaque.setReyNegro(reyNegro);
            reyes.add(reyNegro);
        }
        turno.setReyes(reyes);
    }

    private void guardarPiezaJugador(Pieza pieza) {
        if(pieza.getColor() == Configuracion.ColoresJugadores.BLANCO) {
            jugadores.get(Configuracion.Jugadores.BLANCAS).setPiezasEnJuego(pieza);
        } else {
            jugadores.get(Configuracion.Jugadores.NEGRAS).setPiezasEnJuego(pieza);
        }
    }

    public void actualizarMovimientosPieza(int fila, int columna) {tablero.actualizarMovimientosPieza(fila, columna);}

    private void gestionarTablas() {
        Jugador jugadorActual = turno.getTurno();
        gestorDeTablas.tablasPorAhogado(jugadorActual);
        if(piezasEnJuego <= Constantes.MINIMO_PIEZAS_PARA_CHEQUEAR_TABLAS) {
            gestorDeTablas.tablasPorMaterialInsuficiente(jugadores);
        }
        gestorDeTablas.tablasPorMovimientos(contadorMovimientosParaTablas);
        if(contadorMovimientosParaChequearPosiciones >= Constantes.CANTIDAD_MOVIMIENTOS_MINIMOS_PARA_CHEQUEAR_POSICIONES) {
            gestorDeTablas.tablasPorMovimientosRepetidos(historialPosiciones);
        }
        if(gestorDeTablas.haytablas()){
            establecerTablas();
        }
    }

    public void gestionarJaque(){
        Jugador jugadorActual = turno.getTurno();
        gestorDeJaque.gestionarJaque(jugadorActual);
        if(gestorDeJaque.mateJugadorActual(jugadorActual)){
            establecerJaqueMate();
            System.out.print("Jaque Mate");
        }
    }

    public void gestionarEnroque(){gestorDeEnroque.gestionarEnroque(turno.getReyJugadorActual());}

    public void setNombreJugadorBlancas(String nombre){jugadores.get(Configuracion.Jugadores.BLANCAS).setNombre(nombre);}

    public void setNombreJugadorNegras(String nombre){jugadores.get(Configuracion.Jugadores.NEGRAS).setNombre(nombre);}

    public Configuracion.EstadoJuego getEstado(){return this.estado;}

    public String getNombreGanador(){return this.ganador;}

    public String getNombreJugadorBlancas() {return jugadores.get(Configuracion.Jugadores.BLANCAS).getNombre();}

    public String getNombreJugadorNegras() {return jugadores.get(Configuracion.Jugadores.NEGRAS).getNombre();}

    public String getNombreJugadorActual() {return turno.getNombreTurno();}

    public TableroCuadrado getTablero() {return tablero;}

    public ArrayList<Jugador> getJugadores() {return new ArrayList<>(jugadores);}

    public Configuracion.ColoresJugadores getColorJugadorActual() {return turno.getColorJugadorActual();}

    public Optional<Pieza> getPiezaActual(Integer i, Integer j) {return tablero.getPieza(i, j);}

    public Pieza getUltimaPiezaCapturada(){return this.ultimaPiezaCapturada;}

    public void imprimirEstadoDebug() {
        System.out.println("----- Estado de Debug -----");
        System.out.println("Movimientos para tablas: " + contadorMovimientosParaTablas);
        System.out.println("Movimientos para chequear posiciones: " + contadorMovimientosParaChequearPosiciones);
        System.out.println("Movimientos totales: " + contadorMovimientosTotales);
        System.out.println("Tamaño del historial de posiciones: " + historialPosiciones.size());
        System.out.println("Piezas en juego: " + piezasEnJuego);
        System.out.println("---------------------------");
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
}

