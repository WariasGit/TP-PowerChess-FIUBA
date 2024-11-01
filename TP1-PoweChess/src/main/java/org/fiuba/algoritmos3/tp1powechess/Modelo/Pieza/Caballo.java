package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Movimientos.MovimientoNormal;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Caballo extends Pieza {

    public Caballo(Configuracion.ColoresJugadores color) {

        super(color);
        this.tipoDePieza = Constantes.CABALLO;
        this.maxDistanciaDeAmenaza = Constantes.MINIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();
        this.valor = Configuracion.ValorPiezas.VALOR_CABALLO;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.CABALLO_BLANCO, Configuracion.CaracterFenParaPiezas.CABALLO_NEGRO);
        // Inicializamos las direcciones de movimiento (movimiento en "L")
        this.direccionesDeMovimiento = new ArrayList<>();
        this.direccionesDeMovimiento.add(new int[]{Constantes.DOS_EN_FILA, Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{Constantes.DOS_EN_FILA, -Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.DOS_EN_FILA, Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.DOS_EN_FILA, -Constantes.UNO_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.DOS_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, -Constantes.DOS_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.DOS_EN_COLUMNA});
        this.direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, -Constantes.DOS_EN_COLUMNA});

        // Inicializamos las direcciones de amenaza (puede ser distinto en el futuro)
        this.direccionesDeAmenaza = new ArrayList<>(this.direccionesDeMovimiento);

        this.estrategiaDeMovimiento = new MovimientoNormal();
    }

    public String getTipoDePieza() {
        return "Caballo";
    }

}
