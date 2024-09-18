package org.fiuba.algoritmos3.tp1powechess.Model;

public class Turno {
    private String turno;

    public Turno() {
       //Turno no tiene por que conocer a los jugadores, y si o si inicia en blanco
        this.turno = null;
    }

    public gestionarTurno() {
        //el primer turno va a ser blanco, porque es null
        if (this.turno = 'blanco') {
            this.turno = 'negro';
            return;
        }
        this.turno = 'blanco';
    }

    public getTurno() {
        return this.turno;
    }

}