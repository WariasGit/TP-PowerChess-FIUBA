package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Jugador {
    private Configuracion.ColoresJugadores color;
    private String nombre;
    private Configuracion.EstadoJugador estado;
    private int piezasEnJuego;
    private List<Pieza> piezasPerdidas;

    public Jugador(Configuracion.ColoresJugadores color, String nombre) {
        this.color = color;
        this.nombre = nombre;
        this.estado = null;
        this.piezasEnJuego = 16;
        this.piezasPerdidas = null;
    }

    public void rendirse() {
        this.estado = RENDIDO;
    }

    public boolean estaRendido() {
        return this.estado == RENDIDO;
    }

    public boolean OfrecioTablas() {
        return this.estado == TABLAS;
    }

    public void ofrecerTablas() {
        this.estado = TABLAS;
    }

    public boolean aceptarTablas() {
        alert.setHeaderText("El oponente propuso tablas");
        alert.setContentText("¿Aceptas las tablas?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public void setPiezasEnJuego(Pieza pieza) {
        if (!this.piezasEnJuego.contains(pieza)) {
            piezasEnJuego.add(pieza);
        }
    }

    public void setPiezasPerdidas(Pieza pieza) {
        piezasPerdidas.add(pieza);
        this.piezasEnJuego--;
    }
    }

    public void setNombre(string nombre) {
        this.nombre = nombre;
    }

    public void setColor(Configuracion.ColoresJugadores color) {
        this.color = color;
    }

    public void setEstado(Configuracion.EstadoJugador estado) { this.estado = estado; }

    public void getEstado() { return this.estado; }

    public void getPiezasEnJuego() { return this.piezasEnJuego; }


}


