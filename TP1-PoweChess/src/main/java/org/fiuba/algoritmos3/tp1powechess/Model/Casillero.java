package main.java.org.fiuba.algoritmos3.tp1powechess.Model;

public class Casillero {
    private Pieza pieza;
    private String color;
    private EstadoCasillero estadoCasillero;

    public Casillero(String color) {
        this.color = color;
        this.pieza = null;
        this.estadoCasillero = new CasilleroDesocupado();
    }

    public String getColor() {
        return color;
    }

    public EstadoCasillero getEstadoCasillero() {
        return estadoCasillero;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setEstadoCasillero(EstadoCasillero estadoCasillero) {
        this.estadoCasillero = estadoCasillero;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public void removePieza(Pieza pieza) {
        this.pieza = null;
    }

    public boolean estaOcupado(){
        return estadoCasillero.estaOcupado();
    }


}
