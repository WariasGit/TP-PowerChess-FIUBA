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
    protected Coordenada2D posicion;
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

    public boolean seHaMovido() {return seHaMovido;}

    public void setPosicion(Coordenada2D posicion) {this.posicion = posicion;}

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
            amenazas.add(new Amenaza(this.color, direccion, maxDistancia, posicion));
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

    public boolean aplicarPoder(Poder poder) {
       if (!this.verificarAplicacionPoder()) {
           return false;
       }
       poder.aplicarPoder(this);
        //poderActual = poder;
        return true;
    }

    public void setPoder(Poder poder) {
        poderActual = poder;
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
        return poderActual instanceof Freeze;
    }
    public boolean tieneEscudo() {
        return poderActual instanceof Escudo;
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
