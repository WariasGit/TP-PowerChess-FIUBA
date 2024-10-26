package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public abstract class PeonBase extends Pieza {
    public PeonBase(Configuracion.ColoresJugadores color) {
        super(color);
        this.tipoDePieza = Constantes.PEON;
        this.movimientosPosibles = new ArrayList<>();
        this.maxDistanciaDeAmenaza = Constantes.MINIMA_DISTANCIA;
        this.valor = Configuracion.ValorPiezas.VALOR_PEON;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.PEON_BLANCO, Configuracion.CaracterFenParaPiezas.PEON_NEGRO);
        // Definimos las direcciones de movimiento del peón
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{getDireccion(), Constantes.CERO_EN_COLUMNA});   // Movimiento hacia adelante
        direccionesDeMovimiento.add(new int[]{Constantes.DOS_EN_FILA * getDireccion(), Constantes.CERO_EN_COLUMNA}); // Movimiento inicial doble
    }

    public void quitarOReponerMovimientoDoblePeon() {
        if (seHaMovido() && tieneMovimientoDoble()) {
            direccionesDeMovimiento.remove(Constantes.INDICE_MOVIMIENTO_DOBLE);
        }
        //Esto sirve para reponer el movimiento doble en caso de que se haya revertido el movimiento de un peon al no evitar el jaque.
        else if(!seHaMovido() && !tieneMovimientoDoble()) {
            direccionesDeMovimiento.add(new int[]{Constantes.DOS_EN_FILA * getDireccion(), Constantes.CERO_EN_COLUMNA}); // Movimiento inicial doble
        }
    }

    private boolean tieneMovimientoDoble(){
        return direccionesDeMovimiento.size() > Constantes.INDICE_MOVIMIENTO_DOBLE;
    }

    // Metodo abstracto para obtener la dirección de movimiento del peón (positivo o negativo según el color)
    protected abstract int getDireccion();
}