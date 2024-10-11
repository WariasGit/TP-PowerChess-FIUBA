package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class DobleJuego extends Poder {

    public DobleJuego() {
        super("Doble-juego", 0, Configuracion.CategoriaPoder.ACCION);
    }

    public void aplicar(Pieza pieza) {
        pieza.setMovimientoDoble(true);
    }
    public void desactivar(Pieza pieza) {
        pieza.setMovimientoDoble(false);
    }

    //NO se que hace ni como funciona, lo puse por los errores
    @Override
    public void aplicar(Jugador jugador, Jugador oponente) {

    }
}
