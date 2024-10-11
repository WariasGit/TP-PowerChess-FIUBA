package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;

public class VistaTablero {
    private final StackPane[][] posiciones;

    public VistaTablero(StackPane[][] posiciones) {
        this.posiciones = posiciones;
    }

    public void pintarCasilleroSeleccionado(Integer fila, Integer columna) {
        StackPane stackPane = this.posiciones[fila][columna];
        Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
        rectangle.setFill(Color.GREEN);
    }

    public void pintarCasilleroColorOriginal(Integer fila, Integer columna) {
        StackPane stackPane = this.posiciones[fila][columna];
        Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
        rectangle.setFill((fila + columna) % 2 == 0 ? Color.WHITE : Color.web("#0000006e"));
    }

    public void mostrarMovimientosPosibles(ArrayList<int[]> movimientosPosibles) {
        for(int [] movimiento : movimientosPosibles) {
            int fila = movimiento[Constantes.COORDENADA_FILA];
            int columna = movimiento[Constantes.COORDENADA_COLUMNA];
            try{
                StackPane stackPane = this.posiciones[fila][columna];
                Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
                rectangle.setFill(Color.BLUE);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No se puede pintar el movimiento en la posición: " + fila + ", " + columna);
            }
        }
    }

}
