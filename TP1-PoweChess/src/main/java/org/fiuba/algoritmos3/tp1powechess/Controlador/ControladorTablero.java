package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Model.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Vista.*;
import java.util.Optional;

public class ControladorTablero {
    @FXML public GridPane tableroGrid;
    private VistaTablero vistaTablero = new VistaTablero();
    private Juego juego;
    private Integer posicionOrigenFila;
    private Integer posicionOrigenColumna;
    private final StackPane[][] posiciones = new StackPane[8][8];


    public void initialize() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane stackPane = new StackPane();
                stackPane.setOnMouseClicked(this::handleEventoClick);
                Rectangle rectangle = new Rectangle(75, 75);
                rectangle.setArcHeight(2.0);
                rectangle.setArcWidth(2.0);
                rectangle.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.web("#0000006e"));
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeType(StrokeType.INSIDE);
                stackPane.getChildren().add(rectangle);
                tableroGrid.add(stackPane, col, row);
            }
        }
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
        cargarPiezas();
    }


    private void handleEventoClick(MouseEvent mouseEvent) {
        StackPane stackPane = (StackPane) mouseEvent.getSource();
        Integer fila = GridPane.getRowIndex(stackPane);
        Integer columna = GridPane.getColumnIndex(stackPane);
        Integer i = fila != null ? fila : 0;
        Integer j = columna != null ? columna : 0;

        if (this.posicionOrigenFila == null && this.posicionOrigenColumna == null) {
            System.out.println("Primer clic");
            if (juego.getTablero().casilleroLibre(i,j)) {
                System.out.println("Tocaste un espacio vacio");
                return;
            }
            System.out.println("Tocaste una pieza");
            this.posicionOrigenFila = i;
            this.posicionOrigenColumna = j;
        }
        else {
            System.out.println("Segundo clic");
            Boolean resultado = juego.mover(this.posicionOrigenFila, this.posicionOrigenColumna, i, j);
            if (resultado) {
                ImageView imageView = (ImageView) this.posiciones[this.posicionOrigenFila][this.posicionOrigenColumna].getChildren().remove(1);
                this.posiciones[i][j].getChildren().add(imageView);
                tableroGrid.fireEvent(new EventoCambioDeTurno());
            }
            else{
                System.out.println("Movimiento invalido, se muestra la vista del error");
            }
            this.posicionOrigenFila = null;
            this.posicionOrigenColumna = null;
        }
    }

    public void cargarPiezas() {
        TableroCuadrado tablero = juego.getTablero();
        for (Node stackPane : tableroGrid.getChildren()) {
            Integer fila = GridPane.getRowIndex(stackPane);
            Integer columna = GridPane.getColumnIndex(stackPane);
            Integer i = fila != null ? fila : 0;
            Integer j = columna != null ? columna : 0;
            this.posiciones[i][j] = (StackPane) stackPane;
            Optional<Pieza> pieza = tablero.getPieza(i, j);
            if (pieza.isPresent()) {
                ImageView vistaImagen = new GeneradorVistaPieza().crearVistaPieza(pieza.get());
                vistaImagen.setFitWidth(70);
                vistaImagen.setFitHeight(70);
                vistaImagen.setPreserveRatio(true);
                this.posiciones[i][j].getChildren().add(vistaImagen);
            }
        }
    }
}
