package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.fiuba.algoritmos3.tp1powechess.Model.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

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

    public void mostrarMovimientosPosibles(Pieza piezaActual) {
        for (int[] movimiento : piezaActual.getDireccionesDeMovimiento()) {
            int fila = movimiento[Constantes.COORDENADA_FILA];
            int columna = movimiento[Constantes.COORDENADA_COLUMNA];
            try{
                StackPane stackPane = this.posiciones[fila][columna];
                Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
                rectangle.setFill(Color.YELLOW);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No se puede pintar el movimiento en la posición: " + fila + ", " + columna);
            }
        }
    }

    public void mostrarCasillerosAmenazados(Pieza piezaActual) {
        for (Amenaza amenaza : piezaActual.getAmenazasGeneradas()) {
            int[] direccion = amenaza.getDireccion();
            int fila = direccion[Constantes.COORDENADA_FILA];
            int columna = direccion[Constantes.COORDENADA_COLUMNA];
            try{
                StackPane stackPane = this.posiciones[fila][columna];
                Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
                rectangle.setFill(Color.RED);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No se puede pintar la amenaza en la posición: " + fila + ", " + columna);
            }
        }
    }
}
