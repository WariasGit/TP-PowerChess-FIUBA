package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class RobarPoder extends Poder {

    public RobarPoder() {
        super("Robar poder", 1, Configuracion.CategoriaPoder.ACCION);
    }

    @Override
    public void aplicar(Jugador jugador, Jugador oponente) {
        Poder poderRobado = oponente.robarPoderDisponible(this); //No se que hace ni como funciona, lo puse por el error
        jugador.agregarPoder(poderRobado);
    }

    @Override
    public void aplicar(Pieza pieza) {
        //
    }
}
