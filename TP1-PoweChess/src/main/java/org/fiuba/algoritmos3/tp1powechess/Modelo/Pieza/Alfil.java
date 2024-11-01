package org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class Alfil extends Pieza {

    public Alfil(Configuracion.ColoresJugadores color) {
        super(color);
        this.tipoDePieza = Constantes.ALFIL;
        this.maxDistanciaDeAmenaza = Constantes.MAXIMA_DISTANCIA;
        this.movimientosPosibles = new ArrayList<>();
        this.valor = Configuracion.ValorPiezas.VALOR_ALFIL;
        asignarCaracterFEN(Configuracion.CaracterFenParaPiezas.ALFIL_BLANCO, Configuracion.CaracterFenParaPiezas.ALFIL_NEGRO);
        // Inicializamos las direcciones de movimiento
        direccionesDeMovimiento = new ArrayList<>();
        direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});   // Diagonal derecha arriba
        direccionesDeMovimiento.add(new int[]{-Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA});  // Diagonal izquierda arriba
        direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, Constantes.UNO_EN_COLUMNA});  // Diagonal derecha abajo
        direccionesDeMovimiento.add(new int[]{Constantes.UNO_EN_FILA, -Constantes.UNO_EN_COLUMNA}); // Diagonal izquierda abajo

        // Para el Alfil, las direcciones de movimiento y de amenaza son las mismas
        direccionesDeAmenaza = new ArrayList<>(direccionesDeMovimiento);
    }

    public String getTipoDePieza() {
        return "Alfil";
    }

}
