package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import java.util.HashMap;
import java.util.List;

public class GestorDeTablas {
    private Configuracion.EstadoJuego estado;
    private int contadorMovimientosParaTablas;
    private int contadorMovimientosParaChequearPosiciones;
    private int contadorMovimientosTotales;
    private int piezasEnJuego;
    private final HashMap<String, Integer> historialPosiciones;

    public GestorDeTablas() {
        this.estado = Configuracion.EstadoJuego.EN_JUEGO;
        contadorMovimientosParaTablas = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
        contadorMovimientosParaChequearPosiciones = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
        contadorMovimientosTotales = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
        piezasEnJuego = Constantes.CANTIDAD_PIEZAS_INICIALES;
        historialPosiciones = new HashMap<>();
    }

    public void establecerTablas() {
        estado = Configuracion.EstadoJuego.TABLAS;
    }

    public boolean haytablas(){
        return estado == Configuracion.EstadoJuego.TABLAS;
    }

    private Boolean jugadorActualEstaEnJaque(Jugador jugador) {
        return jugador.estaEnJaque();
    }

    private Boolean jugadorActualTieneMovimientos(Jugador jugador) {
        return jugador.tieneMovimientosPosibles();
    }

    public void tablasPorAhogado(Jugador jugadorActual){
        if(!jugadorActualTieneMovimientos(jugadorActual) && !jugadorActualEstaEnJaque(jugadorActual)){
            establecerTablas();
        }
    }

    private Boolean jugadoresTienenMaterialMinimo(List<Jugador> jugadores){
        boolean continuar = false;
        for (Jugador jugador : jugadores) {
            if(jugador.tieneMaterialSuficiente()){
                continuar = true;
            }
        }
        return continuar;
    }

    public void tablasPorMaterialInsuficiente(List<Jugador> jugadores){
        if(!jugadoresTienenMaterialMinimo(jugadores)){
            establecerTablas();
        }
    }

    public void tablasPorMovimientos(int contadorMovimientosParaTablas){
        if(contadorMovimientosParaTablas == Constantes.CANTIDAD_MOVIMIENTOS_PARA_TABLAS){
            establecerTablas();
        }
    }

    private boolean hayMovimientosRepetidos(HashMap<String, Integer> historialPosiciones) {
        for (Integer contador : historialPosiciones.values()) {
            if (contador >= 3) {
                return true;
            }
        }
        return false;
    }

    public void tablasPorMovimientosRepetidos(HashMap<String, Integer> historialPosiciones){
        if (hayMovimientosRepetidos(historialPosiciones)) {
            establecerTablas();
        }
    }

    public void guardarEstadoTablero(String estadoTablero) {
        historialPosiciones.put(estadoTablero, historialPosiciones.getOrDefault(estadoTablero, Constantes.CERO) + Constantes.UNO);
        contadorMovimientosParaChequearPosiciones++;
    }

    public void gestionarTablas(Jugador jugadorActual, List<Jugador> jugadores) {
        tablasPorAhogado(jugadorActual);
        if(piezasEnJuego <= Constantes.MINIMO_PIEZAS_PARA_CHEQUEAR_TABLAS) {
            tablasPorMaterialInsuficiente(jugadores);
        }
        tablasPorMovimientos(contadorMovimientosParaTablas);
        if(contadorMovimientosParaChequearPosiciones >= Constantes.CANTIDAD_MOVIMIENTOS_MINIMOS_PARA_CHEQUEAR_POSICIONES) {
            tablasPorMovimientosRepetidos(historialPosiciones);
        }
    }

    public void aumentarContadorDeMovimientosTotales(){contadorMovimientosTotales++;}

    public void aumentarContadorDeMovimientosParaTablas(){contadorMovimientosParaTablas++;}

    public int getContadorDeMovimientosTotales(){return contadorMovimientosTotales;}

    public void restarUnaPieza() {piezasEnJuego--;}

    public void reiniciarContadorMovimientosParaChequearPosiciones() {contadorMovimientosParaChequearPosiciones = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;}

    public void limpiarHistorialPosiciones() {historialPosiciones.clear();}

    public void reiniciarContadorMovimientoParaTablas() {
        contadorMovimientosParaTablas = Constantes.CANTIDAD_MOVIMIENTOS_INICIALES;
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
