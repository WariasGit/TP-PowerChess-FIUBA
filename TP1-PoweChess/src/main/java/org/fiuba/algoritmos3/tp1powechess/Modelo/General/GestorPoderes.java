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

    public void activarDobleJuego() {System.out.println("Activando doble Juego");}
    public void activarEscudo() {System.out.println("Activando Escudo");}
    public void activarEvolucion() {System.out.println("Activando Evolucion");}
    public void activarFreeze() {System.out.println("Activando Freeze");}
    public void activarLimpieza() {System.out.println("Activando Limpieza");}
    public void activarRobar() {System.out.println("Activando Robar");}
    public void activarVuelo() {System.out.println("Activando Vuelo");}
}
