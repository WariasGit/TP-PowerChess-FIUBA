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
    private List<Poder> poderesActivos;

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

    public ArrayList<Integer> getListaPoderes() {return new ArrayList<>(listaPoderes);}

    public void usarPoder(Poder poder, Pieza pieza) {
        if (poderesDisponibles.contains(poder) && !poder.esRey()) {
            poder.aplicar(pieza);
            poderesActivos.add(poder);
            if (poder.esDeDuracion()) {
                poder.reducirDuracion();
            } else {
                poder.desactivar(pieza);
            }
            poderesDisponibles.remove(poder); 
        }
    }

    public void actualizarDuracionPoderes() {
        List<Poder> poderesFinalizados = new ArrayList<>();
        for (Poder poder : this.poderesActivos) {
            poder.reducirDuracion();
            if (!poder.estaActivo()) {
                poder.desactivar(piezaAsociadaConPoder(poder)); 
                poderesFinalizados.add(poder);
            }
        }
        poderesActivos.removeAll(this.poderesFinalizados);
    }

   // private Pieza piezaAsociadaConPoder(Poder poder) {
     //   return null;
    //}

    public void agregarPoder(Poder poder) {
        poderesDisponibles.add(poder);
    }

    public List<Poder> getPoderesPorCategoria(CategoriaPoder categoria) {
        return poderesDisponibles.stream()
                .filter(poder -> poder.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

}



