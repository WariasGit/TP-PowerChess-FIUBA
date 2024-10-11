package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

public class PeonAscendente extends PeonBase {

    public PeonAscendente(Configuracion.ColoresJugadores color){
        super(color);
    }

    protected int getDireccion() {return -1;}
}
