package org.fiuba.algoritmos3.tp1powechess.Model.Juego;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza.*;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private String nombre;
    private Configuracion.EstadoJugador estado;
    private List<Pieza> piezasEnJuego;
    private List<Pieza> piezasPerdidas;
    private ArrayList<Integer> listaPoderes; //Lo tomo como una lista de enteros, para poder enlazar los botones momentaneamente
    private List<Poder> poderesDisponibles;

    public Jugador(Configuracion.ColoresJugadores color, String nombre) {
        this.color = color;
        this.nombre = nombre;
        this.estado = null;
        this.piezasEnJuego = null;
        this.piezasPerdidas = null;

        //Esto es a modo de prueba
        this.listaPoderes = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            listaPoderes.add(i);
        }
    }

    public void rendirse() {
        this.estado = Configuracion.EstadoJugador.RENDIDO;
    }

    public boolean estaRendido() {
        return this.estado == Configuracion.EstadoJugador.RENDIDO;
    }

    public void setPiezasEnJuego(Pieza pieza) {
        if (!this.piezasEnJuego.contains(pieza)) {
            piezasEnJuego.add(pieza);
        }
    }

    public Rey getRey() {
        for (Pieza pieza : this.getPiezasEnJuego()) {
            if (pieza instanceof Rey) {
                return (Rey) pieza;
            }
        }
        return null;
    }

    public void setPiezasPerdidas(Pieza pieza) {
            piezasPerdidas.add(pieza);
            piezasEnJuego.remove(pieza);
        }

    public void setNombre(String nombre) {this.nombre = nombre;}

    public void setColor(Configuracion.ColoresJugadores color) {this.color = color;}

    public void setEstado(Configuracion.EstadoJugador estado) { this.estado = estado; }

    public Configuracion.EstadoJugador getEstado() { return this.estado; }

    public int getPiezasEnJuego() { return this.piezasEnJuego.size(); }

    public String getNombre() { return this.nombre; }
    public Configuracion.ColoresJugadores getColor() { return this.color; }


    ///METODOS PODERES /////
    public ArrayList<Integer> getListaPoderes() {return new ArrayList<>(listaPoderes);}

    public void usarPoder(Poder poder, Pieza pieza) {
        if (poderesDisponibles.contains(poder) && !pieza.esRey()) {
            poder.aplicar(pieza);
            poderesDisponibles.remove(poder); 
        }
    }

    //No se puede agregar/robar un poder que la persona tenga ya disponible -- HIPOTESIS
    public void agregarPoder(Poder poder) {
        if (!poderesDisponibles.contains(poder)) {
            poderesDisponibles.add(poder);
        }
    }

    public List<Poder> getPoderesDisponibles() {
        return poderesDisponibles;
    }

    private void inicializarPoderes() {
        poderesDisponibles.add(new Freeze(3));  //definir duracion poderes
    }

    //el nombre es dudodoso, porque se "autoroba" un poder, lo tengo que ver
    public Poder robarPoderDisponible(Poder poder) {
        poderesDisponibles.remove(poder);
        return poder;
    }

}



