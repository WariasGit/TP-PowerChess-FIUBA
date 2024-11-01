package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Movible.Movible;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.EstrategiaDeMovimiento;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoNormal;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Poder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

/**
 * Clase abstracta que representa una pieza en el juego de ajedrez.
 * Esta clase define las propiedades y comportamientos comunes de todas las piezas.
 */
public abstract class Pieza implements Movible {
    protected Configuracion.ColoresJugadores color;
    protected Coordenada2D posicionActual;
    protected Coordenada2D posicionAnterior;
    protected boolean seHaMovido;
    protected int maxDistanciaDeAmenaza;
    protected EstrategiaDeMovimiento estrategiaDeMovimiento;
    protected ArrayList<int[]> direccionesDeMovimiento;
    protected ArrayList<int[]> direccionesDeAmenaza;
    protected ArrayList<int[]> movimientosPosibles;
    protected String tipoDePieza;
    protected char caracterFEN;
    protected int valor;
    protected Poder poderActual;

    public Pieza(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.estrategiaDeMovimiento = new MovimientoNormal();
        this.seHaMovido = false;
    }

    public void actualizarPosicion(Coordenada2D posicionNueva) {
        this.posicionAnterior = this.posicionActual;
        this.posicionActual = posicionNueva;
    }

    /**
     * Ejecuta un movimiento de acuerdo con la estrategia de movimiento definida.
     * @param coordenadaInicial Posición inicial de la pieza.
     * @param coordenadaFinal Posición final a la que se desea mover la pieza.
     * @param tableroCuadrado Tablero en el que se realiza el movimiento.
     * @return La pieza capturada si la hubo, o null si no se capturó ninguna.
     */
    public Pieza ejecutarMovimientoSegunEstrategia(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        return  this.estrategiaDeMovimiento.ejecutarMovimientoSiEsValido(coordenadaInicial, coordenadaFinal, tableroCuadrado);
    };

    /**
     * Genera una lista de amenazas generadas por la pieza en función de sus direcciones de amenaza.
     * @return Lista de amenazas generadas por la pieza.
     */
    public ArrayList<Amenaza> getAmenazasGeneradas() {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int maxDistancia = getMaxDistanciaDeAmenaza();
        for (int[] direccion : direccionesDeAmenaza) {
            amenazas.add(new Amenaza(this.color, direccion, maxDistancia, posicionActual));
        }
        return amenazas;
    }

    /**
     * Asigna el carácter FEN a la pieza según su color.
     * @param opcionBlanca Carácter FEN para las piezas blancas.
     * @param opcionNegra Carácter FEN para las piezas negras.
     */
    public void asignarCaracterFEN(char opcionBlanca, char opcionNegra) {
        if(this.color == Configuracion.ColoresJugadores.BLANCO){
            caracterFEN = opcionBlanca;
        } else {
            caracterFEN = opcionNegra;
        }
    }

    public void setMovimientosPosibles(ArrayList<int[]> movimientosPosibles){
        limpiarListaMovimientosPosibles();
        this.movimientosPosibles = new ArrayList<int[]>(movimientosPosibles);
    }

    public void corrigeMarcandoComoNoMovida(){this.seHaMovido = false;}

    public void marcarComoMovida() {
        this.seHaMovido = true;
    }

    public String aplicarPoder(Poder poder) {
        return poder.accionarPoder(this);
    }

    public boolean verificarAplicacionPoder(Poder poder) {
        if (this.esRey()) {
            return false;
        }
        if (poder.getTipo() == Configuracion.TipoPoder.LIMPIEZA) {
            return true;
        }
        return !this.tienePoderActivo();
    }

    public String desactivarPoder() {
        String nombrePoderActual = null;
        if (poderActual != null) {
            nombrePoderActual = poderActual.getNombre();
        }
        poderActual = null;
        return nombrePoderActual;
    }

    public boolean tieneEscudo() {return (this.tienePoderActivo() && poderActual.getTipo() == Configuracion.TipoPoder.ESCUDO);}

    public boolean tieneFreeze() {return (this.tienePoderActivo() && poderActual.getTipo() == Configuracion.TipoPoder.FREEZE);}

    public boolean tienePoderActivo() {
        return poderActual != null;
    }

    public boolean puedeMoverseA(int filaFinal, int columnaFinal) {
        if (this.tieneFreeze()) {
            return false;
        }
        for(int[] movimiento : movimientosPosibles) {
            if(movimiento[0] == filaFinal && movimiento[1] == columnaFinal) {
                return true;
            }
        }
        return false;
    }

    public char getCaracterFEN() {
        return caracterFEN;
    }

    public int getValor() {
        return valor;
    }

    public void setPoder(Poder poder) {
        this.poderActual = poder;
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public Coordenada2D getPosicionAnterior(){return this.posicionAnterior;}

    public Coordenada2D getPosicionActual(){return this.posicionActual;}

    public boolean seHaMovido() {return seHaMovido;}

    public void setPosicionInicial(Coordenada2D posicionNueva) {this.posicionActual = posicionNueva; this.posicionAnterior = posicionNueva;}

    public boolean esRey() {
        return this.getTipoDePieza().equals("Rey");
    }

    public ArrayList<int[]> getMovimientosPosibles(){
        return new ArrayList<int[]>(movimientosPosibles);
    }

    public void limpiarListaMovimientosPosibles() {
        this.movimientosPosibles.clear();
    }

    public boolean tieneMovimientosPosibles() {
        return !movimientosPosibles.isEmpty();
    }

    public int getMaxDistanciaDeAmenaza() {
        return maxDistanciaDeAmenaza;
    }

    public ArrayList<int[]> getDireccionesDeMovimiento() {
        return new ArrayList<int[]> (direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return tipoDePieza;
    }

    public Poder getPoderActual() {
        return this.poderActual;
    }

}
