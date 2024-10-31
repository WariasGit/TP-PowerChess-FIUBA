package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable.Enrocable;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoRey;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Casillero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Rey extends Pieza implements Enrocable {
    protected Casillero casilleroActual;
    private ArrayList<int[]> movimientosDeEnroqueIzquierda;
    private ArrayList<int[]> movimientosDeEnroqueDerecha;

    public Rey(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.REY;
        this.maxDistanciaDeAmenaza = Constantes.MINIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();
        this.valor = Configuracion.ValorPiezas.VALOR_REY;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.REY_BLANCO, Configuracion.CaracterFenParaPiezas.REY_NEGRO);
        // Inicializamos las direcciones de movimiento
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Derecha
        this.direccionesDeMovimiento.add(new int[]{Constantes.CERO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Izquierda
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});   // Arriba
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.CERO_EN_COLUMNA});  // Abajo
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Diagonal derecha arriba
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Diagonal izquierda arriba
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});  // Diagonal derecha abajo
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA}); // Diagonal izquierda abajo
        // Para el Rey, las direcciones de movimiento y de amenaza son las mismas
        direccionesDeAmenaza = new ArrayList<>(direccionesDeMovimiento);
        this.estrategiaDeMovimiento = new MovimientoRey();
        this.movimientosDeEnroqueIzquierda = new ArrayList<>();
        this.movimientosDeEnroqueDerecha = new ArrayList<>();
    }

    public void enrocarSegunEnroqueDerecho(TableroCuadrado tableroCuadrado,int row) {
        tableroCuadrado.removePieza(this.posicionAnterior);
        System.out.println("Posicion a eliminar del rey: " + posicionActual.getRow() + "," + posicionActual.getCol());
        tableroCuadrado.setPieza(new Coordenada2D(row,6),this);
    }

    public void enrocarSegunEnroqueIzquierdo(TableroCuadrado tableroCuadrado,int row) {
        tableroCuadrado.removePieza(this.posicionAnterior);
        System.out.println("Posicion a eliminar del rey: " + posicionActual.getRow() + "," + posicionActual.getCol());
        tableroCuadrado.setPieza(new Coordenada2D(row,2),this);
    }

    public void actualizarCasilleroActual(Casillero casillero) {
        this.casilleroActual = casillero;
    }

    public boolean estaEnJaque(){return this.casilleroActual.estaAmenazadoPorColorDistinto(color);}

    public void quitarMovimientoPosible(int[] movimientoPosible) {
        this.movimientosPosibles.removeIf(movimiento ->
                movimiento[0] == movimientoPosible[0] && movimiento[1] == movimientoPosible[1]);
    }

    public ArrayList<Amenaza> getAmenazasRecibidas() {return this.casilleroActual.getAmenazasJaque(color);}

    public void cargarMovimientosDeEnroque(){
        Coordenada2D posicionActual = this.posicionActual;
        this.movimientosDeEnroqueDerecha.add(new int[]{posicionActual.getRow(), (posicionActual.getCol() + Constantes.UNO_EN_COLUMNA)});
        this.movimientosDeEnroqueDerecha.add(new int[]{posicionActual.getRow(), (posicionActual.getCol() + Constantes.DOS_EN_COLUMNA)});
        this.movimientosDeEnroqueIzquierda.add(new int[]{posicionActual.getRow(), (posicionActual.getCol() - Constantes.UNO_EN_COLUMNA)});
        this.movimientosDeEnroqueIzquierda.add(new int[]{posicionActual.getRow(), (posicionActual.getCol() - Constantes.DOS_EN_COLUMNA)});
    }

    public ArrayList<int[]> getMovimientosDeEnroqueIzquierda() {return new ArrayList<>(movimientosDeEnroqueIzquierda);}

    public ArrayList<int[]> getMovimientosDeEnroqueDerecha() {return new ArrayList<>(movimientosDeEnroqueDerecha);}

    public void agregarMovimientosPosiblesParaEnrocar(ArrayList<int[]> movimientosDeEnroque){
        if(!this.seHaMovido && !this.estaEnJaque()){
            System.out.println("Se agregan movimientos de enroque");
            this.movimientosPosibles.addAll(movimientosDeEnroque);
        }
    }

}