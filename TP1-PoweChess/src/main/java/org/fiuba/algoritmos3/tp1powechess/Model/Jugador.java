package org.fiuba.algoritmos3.tp1powechess.Model;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private String nombre;
    private int puntaje;

    public Jugador(Configuracion.ColoresJugadores color, String nombre) {
        this.color = color;
        this.nombre = nombre;
        this.puntaje = 0;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    /*public boolean aceptarTablas() {
        //devuelve los datos enviados por la vista, seleccionado por usuario
    }*/

    public void rendirse() {
    }

    public String getNombre() {
        return nombre;
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public int getPuntaje() {
        return puntaje;
    }
}

