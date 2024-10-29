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

    public String accionarPoder(Pieza pieza) {
        Jugador oponente = this.turno.getOponente();
        Jugador jugador = this.turno.getTurno();

        // Crear una lista de poderes válidos (que no sean "Robar" y tengan usos disponibles)
        List<String> poderesDisponibles = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : oponente.getListaPoderes().entrySet()) {
            String nombrePoder = entry.getKey();
            int cantidad = entry.getValue();
            if (cantidad > 0 && !nombrePoder.equals("Robar")) {
                poderesDisponibles.add(nombrePoder);
            }
        }

        // Si hay poderes válidos, selecciona uno al azar
        if (!poderesDisponibles.isEmpty()) {
            Random random = new Random();
            String poderRobado = poderesDisponibles.get(random.nextInt(poderesDisponibles.size()));

            // Transferir el poder al jugador y eliminarlo del oponente
            jugador.agregarPoder(poderRobado);
            oponente.eliminarPoderUsado(poderRobado);

            return poderRobado;
        }

        return null; // Si no hay poderes disponibles para robar
    }
}