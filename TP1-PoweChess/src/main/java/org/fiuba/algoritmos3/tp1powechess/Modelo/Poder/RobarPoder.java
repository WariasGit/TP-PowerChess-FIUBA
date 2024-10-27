package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;


import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

public class RobarPoder extends Poder {
    public RobarPoder(int duracion) {
        super(Configuracion.CaracteristicasPoderes.ROBAR, -1, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.ROBAR, Configuracion.AplicacionPoder.RIVAL);
    }


    @Override
    public void aplicarPoder(Jugador jugador, Jugador oponente, Poder poder, Pieza pieza) {

        if (oponente.puedeUsarPoder(poder)) {
            jugador.agregarPoder(poder);
            oponente.eliminarPoderUsado(poder.getTipo());
        }
        System.out.println("Poder robado: " + poderRobado.getTipo() + " del jugador oponente.");
    }
}
