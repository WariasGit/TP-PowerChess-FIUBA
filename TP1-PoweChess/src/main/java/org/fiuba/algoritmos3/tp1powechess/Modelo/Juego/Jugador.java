package org.fiuba.algoritmos3.tp1powechess.Modelo.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private Boolean jaque;
    private String nombre;
    ArrayList<Pieza> piezasEnJuego;

    public Jugador(Configuracion.ColoresJugadores color, String nombre) {
        this.color = color;
        jaque = false;
        this.nombre = nombre;
        piezasEnJuego = new ArrayList<>();
    }

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
}


