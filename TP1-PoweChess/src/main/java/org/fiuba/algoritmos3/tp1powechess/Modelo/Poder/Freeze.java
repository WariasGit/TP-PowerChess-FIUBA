package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class Freeze extends Poder {
    public Freeze(int duracion) {
        super(Configuracion.CaracteristicasPoderes.FREEZE, duracion, Configuracion.CategoriaPoder.DURACION);
    }

    @Override
    public void aplicar(Pieza pieza) {
        pieza.aplicarPoder(this);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.desactivarPoder(this);  // Elimina el poder Freeze de la pieza
    }
}