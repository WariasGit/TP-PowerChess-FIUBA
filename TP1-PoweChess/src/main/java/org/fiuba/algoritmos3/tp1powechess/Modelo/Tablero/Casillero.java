package org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.*;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Estado.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;
import java.util.Objects;

/**
 * La clase Casillero representa una celda en un tablero de juego de ajedrez.
 * Cada casillero tiene un color y un estado de ocupación (ocupado o desocupado), puede contener una pieza.
 * Además, gestiona las amenazas que afectan a dicho casillero.
 */
public class Casillero {
    private Pieza pieza;
    private Configuracion.ColoresJugadores color;
    private EstadoDeOcupacionCasillero estadoDeOcupacionCasillero;
    private GestorDeAmenazas gestorDeAmenazas;

    /**
     * Constructor de la clase Casillero.
     * Inicializa un casillero con un color dado, sin ninguna pieza inicialmente.
     * Establece el estado de ocupación como desocupado y prepara el gestor de amenazas.
     *
     * @param color Color del casillero, correspondiente a los colores designados para el tablero.
     */
    public Casillero(Configuracion.ColoresJugadores color) {
        this.color = color;
        this.pieza = null;
        this.estadoDeOcupacionCasillero = new EstadoDesocupado();
        this.gestorDeAmenazas = new GestorDeAmenazas();
    }

    /**
     * Asigna una pieza al casillero y actualiza su estado a ocupado.
     * Bloquea las amenazas que se extienden más allá de un casillero para evitar conflictos en el juego.
     *
     * @param pieza La pieza a colocar en el casillero.
     */
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        if(!this.estaOcupado()) {
            this.setEstadoDeOcupacion(new EstadoOcupado());
        }
        this.bloquearAmenazasQueSeExtiendenMasDeUnCasillero();
    }

    /**
     * Elimina y devuelve la pieza actual del casillero, estableciendo su estado a desocupado.
     * Además, desbloquea todas las amenazas previamente bloqueadas.
     *
     * @return La pieza que fue removida del casillero.
     */
    public Pieza removerPieza() {
        Pieza piezaAux = this.pieza;
        this.pieza = null;
        this.setEstadoDeOcupacion(new EstadoDesocupado());
        this.desbloquearAmenazasBloqueadas();
        return piezaAux;
    }

    /**
     * Agrega una lista de amenazas activas al casillero usando el gestor de amenazas.
     * Si el casillero está ocupado, bloquea amenazas que se extienden más allá de un casillero.
     *
     * @param amenazas Lista de amenazas a agregar como activas en el casillero.
     */
    public void agregarAmenazas(ArrayList<Amenaza> amenazas) {
        this.gestorDeAmenazas.agregarAmenazasActivas(amenazas);
        if (this.estaOcupado()) {
            bloquearAmenazasQueSeExtiendenMasDeUnCasillero();
        };
    }

    /**
     * Elimina una lista específica de amenazas, permitiendo manejar amenazas activas de manera precisa en el tablero.
     *
     * @param amenazasAEliminar Lista de amenazas que deben ser eliminadas.
     */
    public void removerLasSiguientesAmenazas(ArrayList<Amenaza> amenazasAEliminar){
        this.gestorDeAmenazas.quitarAmenazasIguales(amenazasAEliminar);
    }

    /**
     * Bloquea todas las amenazas activas que se extienden  más allá de un casillero.
     * Esto es útil al agregar piezas que limitan el alcance de las amenazas.
     */
    public void bloquearAmenazasQueSeExtiendenMasDeUnCasillero() {
        gestorDeAmenazas.moverAmenazasActivasAmasDeUnCasilleroABloqueadas();
    }

    /**
     * Desbloquea todas las amenazas bloqueadas en el casillero, restaurándolas a su estado activo.
     * Se utiliza al remover piezas del casillero.
     */
    public void desbloquearAmenazasBloqueadas() {
        gestorDeAmenazas.moverTodasAmenazasBloqueadasAActivas();
    }

    public boolean estaOcupado(){
        return estadoDeOcupacionCasillero.estaOcupado();
    }

    /**
     * Si el casillero esta ocupaddo, devuelve las amenazas generadas por la pieza.
     *
     * @return Lista de amenazas generadas por la pieza en el casillero, o una lista vacía si el casillero está desocupado.
     */
    public ArrayList<Amenaza> getAmenzasDePiezaActual(){
        if(this.estaOcupado()){
            return this.pieza.getAmenazasGeneradas();
        }
        return new ArrayList<Amenaza>();
    }

    /**
     * Verifica si el casillero está amenazado por piezas del color contrario.
     *
     * @param color Color del jugador a verificar.
     * @return true si el casillero está amenazado por el color opuesto; false en caso contrario.
     */
    public boolean estaAmenazadoPorColorDistinto(Configuracion.ColoresJugadores color) {
        ArrayList<Amenaza> amenazasDistintoColor = gestorDeAmenazas.obtenerAmenazasTotalesDistintoColor(color);
        return !amenazasDistintoColor.isEmpty();
    }

    /**
     * Devuelve todas las amenazas en el casillero que no coinciden con el color especificado.
     * Esto ayuda a evaluar si el casillero está amenazado por el oponente.
     *
     * @param color Color del jugador para filtrar amenazas de color opuesto.
     * @return Lista de amenazas del color opuesto en el casillero.
     */
    public ArrayList<Amenaza> getAmenazasJaque(Configuracion.ColoresJugadores color){
        return this.gestorDeAmenazas.obtenerAmenazasTotalesDistintoColor(color);
    }

    public Configuracion.ColoresJugadores getColor() {
        return color;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setEstadoDeOcupacion(EstadoDeOcupacionCasillero estadoDeOcupacionCasillero) {
        this.estadoDeOcupacionCasillero = estadoDeOcupacionCasillero;
    }

    public ArrayList<Amenaza> getAmenazasBloqueadas(){
        return this.gestorDeAmenazas.getAmenazasBloqueadas();
    }

    public ArrayList<Amenaza> obtenerAmenazasActivasQueSeExtiendenMasQueUnCasillero(){
        return this.gestorDeAmenazas.obtenerAmenazasActivasQueSeExtiendenMasQue(1);
    }
}
