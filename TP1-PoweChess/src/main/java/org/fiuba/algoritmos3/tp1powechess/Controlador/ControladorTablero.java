package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Vista.GeneradorVistaPieza;
//import org.fiuba.algoritmos3.tp1powechess.Vista.GeneradorVistaPieza;

import java.util.Optional;

public class ControladorTablero {
    private Juego juego;

    @FXML
    private GridPane tableroGrid;

    private Integer posicionOrigenFila;

    private Integer posicionOrigenColumna;

    private StackPane[][] posiciones = null;

    public void initialize() {
        this.posiciones = new StackPane[8][8];
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
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
                this.posiciones[i][j].getChildren().add(vistaImagen);
            }
        }


    }



    public void handleEventoClick(MouseEvent mouseEvent) {
        StackPane stackPane = (StackPane) mouseEvent.getSource();
        Integer fila = GridPane.getRowIndex(stackPane);
        Integer columna = GridPane.getColumnIndex(stackPane);
        Integer i = fila != null ? fila : 0;
        Integer j = columna != null ? columna : 0;
        if (this.posicionOrigenFila == null && this.posicionOrigenColumna == null) {
            // Origen ocupado
            if (this.juego.getTablero().casilleroLibre(i,j)) {
                return;
            }
            this.posicionOrigenFila = i;
            this.posicionOrigenColumna = j;
        } else {
            // Destino libre
            if (this.juego.getTablero().getPieza(i, j).isPresent()) {
                return;
            }
            Boolean resultado = this.juego.mover(this.posicionOrigenFila, this.posicionOrigenColumna, i, j);
            if (resultado) {
                ImageView imageView = (ImageView) this.posiciones[this.posicionOrigenFila][this.posicionOrigenColumna].getChildren().remove(1);
                this.posiciones[i][j].getChildren().add(imageView);
                tableroGrid.fireEvent(new EventoCambioDeTurno());
            }
            this.posicionOrigenFila = null;
            this.posicionOrigenColumna = null;
        }
    }
}
