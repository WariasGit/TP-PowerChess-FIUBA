package org.fiuba.algoritmos3.tp1powechess.Model;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;


public class Turno {
    private Configuracion.ColoresJugadores color;

    public Turno() {
        this.color = Configuracion.ColoresJugadores.BLANCO;
    }

    public void gestionarTurno() {
        if (this.turno == Configuracion.ColoresJugadores.BLANCO) {
            this.turno = Configuracion.ColoresJugadores.NEGRO;
            return;
        }
        this.turno = Configuracion.ColoresJugadores.BLANCO;
    }

    public String getTurno() {
        return this.turno;
    }

}