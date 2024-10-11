package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Vuelo extends Poder {

    public Vuelo() {
        super("Vuelo", 0, Configuracion.CategoriaPoder.ACCION); // Solo dura un turno
    }

    //Por los errores, agregue estos metodos

    @Override
    public void aplicar(Jugador jugador, Jugador oponente) {
        //
    }

    @Override
    public void aplicar(Pieza pieza) {
        //
    }
}


