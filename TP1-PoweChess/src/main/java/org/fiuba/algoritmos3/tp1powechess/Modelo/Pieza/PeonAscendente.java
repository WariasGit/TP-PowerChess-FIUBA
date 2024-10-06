package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import java.util.ArrayList;

public class PeonAscendente extends PeonBase {

    public PeonAscendente(Configuracion.ColoresJugadores color){
        super(color);
        // Definimos las direcciones de amenaza
        direccionesDeAmenaza = new ArrayList<>();
        direccionesDeAmenaza.add(new int[]{getDireccion(),1});  // Captura diagonal derecha
        direccionesDeAmenaza.add(new int[]{-1, getDireccion()}); // Captura diagonal izquierda
    }

    protected int getDireccion() {return -1;}
}
