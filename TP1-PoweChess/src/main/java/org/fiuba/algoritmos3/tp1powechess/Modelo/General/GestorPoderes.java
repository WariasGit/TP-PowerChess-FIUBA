package org.fiuba.algoritmos3.tp1powechess.Modelo.General;

import javafx.event.EventHandler;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoPoder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;

/**
 * Esta clase maneja los poderes en el juego de PowerChess.
 * Utiliza expresiones lambda para gestionar eventos de manera concisa y legible.
 *Al añadir manejadores de eventos, se utiliza la siguiente sintaxis:
 * root.addEventHandler(EventoPoder.DOBLE_JUEGO, evento -> gestorPoderes.activarDobleJuego());
 * Necesitaba hacerlo asi para evitar tener muchos if anidados, en el caso de sobreescribir el metodo handle de la interfaz EventHandler.
 */

public class GestorPoderes {
    private Juego juego;
    private Integer posicionFilaPiezaSeleccionada;
    private Integer posicionColumnaPiezaSeleccionada;

    public GestorPoderes(Juego juego){
        this.juego = juego;
    }

    public void setPosiciones(Integer fila, Integer columna){
        this.posicionFilaPiezaSeleccionada = fila;
        this.posicionColumnaPiezaSeleccionada = columna;
    }

    public void getPosicionFila() {
        return this.posicionFilaPiezaSeleccionada; 
    }

    public void getPosicionColumna() {
        return this.posicionColumnaPiezaSeleccionada; 
    }

    
    public void activarEscudo() {
        try {
            Pieza piezaSeleccionada = juego.getPiezaEnPosicion(this.getPosicionFila(), this.getPosicionColumna());
            jugador.usarPoder(new Escudo(), piezaSeleccionada); 
            System.out.println("Activando Escudo");
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }

    public void activarFreeze() {
        try {
            Pieza piezaSeleccionada = juego.getPiezaEnPosicion(this.getPosicionFila(), this.getPosicionColumna());
            jugador.usarPoder(new Freeze(), piezaSeleccionada); 
            System.out.println("Activando Freeze");
        } catch (IOException e) {
                throw new RuntimeException(e);
        }e
    }

    public void activarVuelo() {
        try {
            Pieza piezaSeleccionada = juego.getPiezaEnPosicion(this.getPosicionFila(), this.getPosicionColumna());
            jugador.usarPoder(new Vuelo(), piezaSeleccionada); 
            System.out.println("Activando Vuelo");
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
}
