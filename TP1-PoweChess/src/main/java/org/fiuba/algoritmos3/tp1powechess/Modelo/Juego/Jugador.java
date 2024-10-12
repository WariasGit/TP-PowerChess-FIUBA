package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.*;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private String nombre;
    private Configuracion.EstadoJugador estado;
    private List<Pieza> piezasEnJuego;
    private List<Pieza> piezasPerdidas;
    private ArrayList<Integer> listaPoderes; //Lo tomo como una lista de enteros, para poder enlazar los botones momentaneamente
    private List<Poder> poderesDisponibles;
    private List<Poder> poderesActivos; 

    public void setPiezasEnJuego(Pieza pieza) { piezasEnJuego.add(pieza); }

    public void quitarPiezaEnJuego(Pieza pieza) { piezasEnJuego.remove(pieza); }

    public ArrayList<Pieza> getPiezasEnJuego() { return new ArrayList<>(piezasEnJuego); }

    public String getNombre() { return this.nombre; }

    public Configuracion.ColoresJugadores getColor() { return this.color; }

    public Boolean estaEnJaque() {
        return jaque;
    }

    public void cambiarEstadoJaque() {
        jaque = !jaque;
    }

    public Rey getRey() {
        for (Pieza pieza : piezasEnJuego) {
            if (pieza instanceof Rey) {
                return (Rey) pieza;
            }
        }
        return null;
    }

    public Boolean tieneMovimientosPosibles(){
        for (Pieza pieza : piezasEnJuego) {
            if (pieza.tieneMovimientosPosibles()) {
                return true;
            }
        }
        return false;
    }

    public Boolean tieneMaterialSuficiente(){
        int valorTotal = 0;
        for (Pieza pieza : piezasEnJuego) {
            valorTotal += pieza.getValor();
        }
        if(valorTotal == Configuracion.ValorPiezas.REY_MAS_PEON){
            valorTotal += Configuracion.ValorPiezas.VALOR_MINIMO_PIEZAS;
        }
        System.out.println("Valor total de piezas: " + valorTotal);
        return valorTotal >= Configuracion.ValorPiezas.VALOR_MINIMO_PIEZAS;
    }

     ///METODOS PODERES /////
    public ArrayList<Integer> getListaPoderes() {return new ArrayList<>(listaPoderes);}

    public void usarPoder(Poder poder, Pieza pieza) {
        if (poderesDisponibles.contains(poder) && !pieza.esRey()) {
            poder.aplicar(pieza);
        }
        if (!poder.esDeDuracion()) {
            poderesDisponibles.remove(poder); 
        } else {
            poder.reducirDuracion() //falta que en cada turno que quede se vaya restando la duracion 
        }
        }

    //No se puede agregar/robar un poder que la persona tenga ya disponible -- HIPOTESIS
    public void agregarPoder(Poder poder) {
        if (!poderesDisponibles.contains(poder)) {
            poderesDisponibles.add(poder);
        }
    }

    public List<Poder> getPoderesDisponibles() {
        return this.poderesDisponibles;
    }
    public List<Poder> getPoderesActivos() {
        return this.poderesActivos;
    }

    private void inicializarPoderes() {
        poderesDisponibles.add(new Freeze(3));  //definir duracion poderes
    }

    //el nombre es dudodoso, porque se "autoroba" un poder, lo tengo que ver
    public Poder robarPoderDisponible(Poder poder) {
        poderesDisponibles.remove(poder);
        return poder;
    }

    public void reducirDuracionPoderes() {
        List<Poder> poderesRestantes = new ArrayList<>();
        for (Poder poder : poderesAplicados) {
            if (poder.esDeDuracion()) {
            poder.reducirDuracion();
            }
            if (poder.getDuracion() > 0) {
                poderesRestantes.add(poder);  // Mantener el poder si sigue activo
            } else {
                poder.desactivar(this);  
            }
        }
        this.poderesAplicados = poderesRestantes;  // Actualizar la lista de poderes activos
    }

}



