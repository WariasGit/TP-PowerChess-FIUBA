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
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoCambioDeTurno;
import org.fiuba.algoritmos3.tp1powechess.Model.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Model.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ControladorTablero {
    @FXML private GridPane tableroGrid;
    private Juego juego;
    private Integer posicionOrigenFila;
    private Integer posicionOrigenColumna;
    private final StackPane[][] posiciones = new StackPane[Configuracion.TamanioVentana.DIMENSION_TABLERO][Configuracion.TamanioVentana.DIMENSION_TABLERO];
    private VistaTablero vistaTablero = new VistaTablero(posiciones);


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
                Pieza piezaActual = pieza.get();
                ImageView vistaImagen = new GeneradorVistaPieza().crearVistaPieza(piezaActual);
                vistaImagen.setFitWidth(70);
                vistaImagen.setFitHeight(70);
                vistaImagen.setPreserveRatio(true);
                this.posiciones[i][j].getChildren().add(vistaImagen);
            }
        }
    }

    private int getGridIndex(Integer index) {
        return index != null ? index : 0;
    }

    private boolean esPrimeraSeleccion() {
        return this.posicionOrigenFila == null && this.posicionOrigenColumna == null;
    }

    private void handleEventoClick(MouseEvent mouseEvent) {
        StackPane stackPane = (StackPane) mouseEvent.getSource();
        int fila = getGridIndex(GridPane.getRowIndex(stackPane));
        int columna = getGridIndex(GridPane.getColumnIndex(stackPane));
        System.out.println("Click en: " + fila + ", " + columna);

        if (esPrimeraSeleccion()) {
            manejarPrimerClick(fila, columna);
        } else {
            manejarSegundoClick(fila, columna);
            limpiarSeleccion();
        }
    }

    private void manejarPrimerClick(int fila, int columna) {
        if (juego.esCasilleroLibre(fila, columna)) {
            System.out.println("Tocaste un espacio vacio");
            return;
        }
        System.out.println("Tocaste una pieza");
        aplicarColorCasillero(fila, columna);
        Optional<Pieza> piezaActual = juego.getPiezaActual(fila, columna);
        mostrarMovimientosPosibles(piezaActual, fila, columna);
        guardarPosicionOrigen(fila, columna);
    }

    private void manejarSegundoClick(int fila, int columna) {
        boolean movimientoValido = juego.mover(this.posicionOrigenFila, this.posicionOrigenColumna, fila, columna);
        if (movimientoValido) {
            moverPieza(fila, columna);
            tableroGrid.fireEvent(new EventoCambioDeTurno());
        } else {
            System.out.println("Movimiento invalido, se muestra la vista del error");
        }
        quitarColorCasillero(fila, columna);
    }

    private void moverPieza(int fila, int columna) {
        ImageView imageView = (ImageView) this.posiciones[this.posicionOrigenFila][this.posicionOrigenColumna].getChildren().remove(1);
        this.posiciones[fila][columna].getChildren().add(imageView);
    }

    private void guardarPosicionOrigen(int fila, int columna) {
        this.posicionOrigenFila = fila;
        this.posicionOrigenColumna = columna;
    }

    private void limpiarSeleccion() {
        this.posicionOrigenFila = null;
        this.posicionOrigenColumna = null;
    }

    private void aplicarColorCasillero(Integer fila, Integer columna) {
        vistaTablero.pintarCasilleroSeleccionado(fila, columna);
    }

    private void quitarColorCasillero(Integer fila, Integer columna) {
        vistaTablero.pintarCasilleroColorOriginal(fila, columna);
    }

    private void mostrarMovimientosPosibles(Optional<Pieza> piezaActual, int fila, int columna) {
        if(piezaActual.isPresent()){
            Pieza pieza = piezaActual.get();
            ArrayList<int[]> listaPosicionesPosibles = new ArrayList<>();
            obtenerPosicionesCasillero(pieza, listaPosicionesPosibles, fila, columna);
            vistaTablero.mostrarMovimientosPosibles(listaPosicionesPosibles);

        }

    }

    private void obtenerPosicionesCasillero(Pieza piezaActual, ArrayList<int[]> lista_posiciones, int fila, int columna){
        for(Amenaza amenaza: piezaActual.getAmenazasGeneradas()){
            int[] direccion = amenaza.getDireccion();
            int cantidadCasilleros = amenaza.getCantidadCasilleros();
            for(int i = 1; i <= cantidadCasilleros; i++){
                int[] nuevaPosicion = {fila + direccion[Constantes.COORDENADA_FILA] * i, columna + direccion[Constantes.COORDENADA_COLUMNA] * i};
                lista_posiciones.add(nuevaPosicion);
            }
        }
        if (Objects.equals(piezaActual.getTipoDePieza(), Constantes.PEON)) {
            for (int[] direccion : piezaActual.getDireccionesDeMovimiento()) {
                int[] nuevaPosicion = {fila + direccion[Constantes.COORDENADA_FILA], columna + direccion[Constantes.COORDENADA_COLUMNA]};
                lista_posiciones.add(nuevaPosicion);
            }
        }
    }

    private void mostrarCasillerosAmenazados(ArrayList<int[]> lista_posiciones){
        //TODO: Implementar
    }
}
