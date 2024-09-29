package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Model.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.GeneradorVistaPieza;
import org.fiuba.algoritmos3.tp1powechess.Vista.vistaJuego;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class ControladorJuego implements EventHandler<EventoCambioDeTurno>{
    private Juego juego;
    private Integer posicionOrigenFila;
    private Integer posicionOrigenColumna;
    private final StackPane[][] posiciones = new StackPane[8][8];;
    private vistaJuego vistaJuego = new vistaJuego();

    @FXML GridPane tableroGrid;
    @FXML public Rectangle jugador1_color;
    @FXML public Rectangle jugador2_color;
    @FXML private Label jugador1;
    @FXML private Label jugador2;
    @FXML private Label jugadorActual;


    private static final Map<Configuracion.ColoresJugadores, Color> colores = Map.ofEntries(
            Map.entry(Configuracion.ColoresJugadores.BLANCO, Color.WHITE),
            Map.entry(Configuracion.ColoresJugadores.NEGRO, Color.BLACK)
    );


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
        this.jugador1.setText(juego.getJugadorBlancas().getNombre());
        this.jugador2.setText(juego.getJugadorNegras().getNombre());
        this.jugador1_color.setFill(colores.get(juego.getJugadorBlancas().getColor()));
        this.jugador2_color.setFill(colores.get(juego.getJugadorNegras().getColor()));
        this.jugadorActual.setText("Jugador actual: " + juego.getJugadorActual().getNombre());
        cargarPiezas();
    }

    @Override
    public void handle(EventoCambioDeTurno cambioDeTurnoEvent) {
        this.juego.cambiarTurno();
        this.jugadorActual.setText("Jugador actual: " + juego.getJugadorActual().getNombre());
    }

    public void mostrarConfirmacionCierre(WindowEvent windowEvent) {
        vistaJuego.mostrarConfirmacionCierre(windowEvent);
    }

    private void handleEventoClick(MouseEvent mouseEvent) {
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

    private void cargarPiezas() {
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
