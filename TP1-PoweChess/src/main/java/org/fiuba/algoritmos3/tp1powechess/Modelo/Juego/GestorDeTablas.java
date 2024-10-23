package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;

import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.HashMap;
import java.util.List;

public class GestorDeTablas {
    private Configuracion.EstadoJuego estado;

    public GestorDeTablas() {
        this.estado = Configuracion.EstadoJuego.EN_JUEGO;
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
}
