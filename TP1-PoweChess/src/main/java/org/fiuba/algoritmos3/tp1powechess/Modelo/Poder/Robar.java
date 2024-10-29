package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Turno;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Robar extends Poder{
    protected Turno turno;
    public Robar(Turno turno) {
        super("Robar", -1, Configuracion.CategoriaPoder.ACCION, Configuracion.TipoPoder.ROBAR, Configuracion.AplicacionPoder.AMBOS);
        this.turno = turno;
    }

    public void aplicarPoder(Pieza pieza) {
        Jugador oponente = this.turno.getOponente();
        Jugador jugador = this.turno.getTurno();

        for (Map.Entry<String, Integer> entry : oponente.getListaPoderes().entrySet()) {
            String nombrePoder = entry.getKey();
            int cantidad = entry.getValue();

            if (cantidad > 0 && !nombrePoder.equals("Robar")) {
                jugador.agregarPoder(nombrePoder); // Se añade a la misma lista
                oponente.eliminarPoderUsado(nombrePoder);

                System.out.println("Poder robado: " + nombrePoder);
                return;
            }
        }
        System.out.println("El oponente no tiene poderes para robar.");
    }
}