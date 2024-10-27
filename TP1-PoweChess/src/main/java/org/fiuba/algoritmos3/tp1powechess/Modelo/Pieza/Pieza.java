package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Movible.Movible;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.EstrategiaDeMovimiento;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoNormal;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Escudo;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Freeze;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Poder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

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
    protected Boolean movimientoDoble;
    protected Poder poderActual;

    public Pieza(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.seHaMovido = false;
        this.estrategiaDeMovimiento = new MovimientoNormal();
        this.seHaMovido = false;
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public Coordenada2D getPosicionAnterior(){return this.posicionAnterior;}

    public Coordenada2D getPosicionActual(){return this.posicionActual;}

    public boolean seHaMovido() {return seHaMovido;}

    public void setPosicionActual(Coordenada2D posicionNueva) {this.posicionActual = posicionNueva; this.posicionAnterior = posicionNueva;}

    public void actualizarPosicion(Coordenada2D posicionNueva) {
        this.posicionAnterior = this.posicionActual;
        this.posicionActual = posicionNueva;
    }

    public void marcarComoMovida() {
        this.seHaMovido = true;
    }

    public boolean esDelMismoColorQue(Pieza otraPieza){
        return this.color.equals(otraPieza.color);
    }

    public Pieza ejecutarMovimientoSegunEstrategia(Coordenada2D coordenadaInicial, Coordenada2D coordenadaFinal, TableroCuadrado tableroCuadrado){
        return  this.estrategiaDeMovimiento.ejecutarMovimientoSiEsValido(coordenadaInicial, coordenadaFinal, tableroCuadrado);
    };

    public abstract boolean esDireccionDeMovimientoValida(int difX, int difY);

    public abstract boolean esCapturaValida(int inicioX, int inicioY, int finX, int finY);

    public ArrayList<Amenaza> getAmenazasGeneradas() {
        ArrayList<Amenaza> amenazas = new ArrayList<>();
        int maxDistancia = getMaxDistanciaDeAmenaza();
        for (int[] direccion : direccionesDeAmenaza) {
            amenazas.add(new Amenaza(this.color, direccion, maxDistancia, posicionActual));
        }
        return amenazas;
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

    public void setMovimientosPosibles(ArrayList<int[]> movimientosPosibles){
        limpiarListaMovimientosPosibles();
        this.movimientosPosibles = new ArrayList<int[]>(movimientosPosibles);
    }

    public ArrayList<int[]> getMovimientosPosibles(){
        return new ArrayList<int[]>(movimientosPosibles);
    }

    public void limpiarListaMovimientosPosibles() {
        this.movimientosPosibles.clear();
    }

    public boolean tieneMovimientosPosibles() {
        System.out.println("tiene freeze??: " + this.tieneFreeze());
        System.out.println("tiene poder??: " + this.poderActual);

        if (this.tieneFreeze()) {
            return false;
        }

        return !movimientosPosibles.isEmpty();
    }

    public int getValor() {
        return valor;
    }

    public void asignarCaracterFEN(char opcionBlanca, char opcionNegra) {
        if(this.color == Configuracion.ColoresJugadores.BLANCO){
            caracterFEN = opcionBlanca;
        }else {
            caracterFEN = opcionNegra;
        }
    }

    public char getCaracterFEN() {
        return caracterFEN;
    }

    //esto se deberia llamar directamente de poder
    public boolean aplicarPoder(Poder poder) {

        if (!this.verificarAplicacionPoder()) {
           return false;
       }
       //poder.aplicarPoder(this);
        this.poderActual = poder;

        return true;
    }

    public void setPoder(Poder poder) {
        this.poderActual = poder;
    }

    public boolean verificarAplicacionPoder() {
        if (this.esRey()) {
            return false;
        }
        return !this.tienePoderActivo();
    }

    public void desactivarPoder() {
        poderActual = null;
    }

    public boolean tieneFreeze() {
        System.out.println("Poder de la piezaaa: " + this.poderActual);
        return this.tienePoderActivo() && poderActual.getTipo() == Configuracion.TipoPoder.FREEZE;
    }
    public boolean tieneEscudo() {
        return this.tienePoderActivo() && poderActual.getTipo() == Configuracion.TipoPoder.ESCUDO;
    }



    public boolean tienePoderActivo() {
        return poderActual != null;
    }

    public boolean puedeMoverseA(int filaFinal, int columnaFinal) {
        for(int[] movimiento : movimientosPosibles) {
            if(movimiento[0] == filaFinal && movimiento[1] == columnaFinal) {
                return true;
            }
        }
        return false;
    }

    public boolean esRey() {
        return this.getTipoDePieza().equals("Rey");
    }


    public void gestionarPoder() {
        if (this.tienePoderActivo()) {
            this.poderActual.reducirDuracionoDesactivar(this);
        }
    }
}
