package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

public class Casillero {
    private Pieza pieza;
    private String color;
    private EstadoDeOcupacionCasillero estadoDeOcupacionCasillero;

    public Casillero(String color) {
        this.color = color;
        this.pieza = null;
        this.estadoDeOcupacionCasillero = new EstadoDesocupado();
    }

    public String getColor() {
        return color;
    }

    public EstadoDeOcupacionCasillero getEstadoCasillero() {
        return estadoDeOcupacionCasillero;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setEstadoCasillero(EstadoDeOcupacionCasillero estadoDeOcupacionCasillero) {
        this.estadoDeOcupacionCasillero = estadoDeOcupacionCasillero;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public void removePieza(Pieza pieza) {
        this.pieza = null;
    }

    public boolean estaOcupado(){
        return estadoDeOcupacionCasillero.estaOcupado();
    }


}
