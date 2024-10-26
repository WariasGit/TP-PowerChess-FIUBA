package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoJuego;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoPoder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Amenaza.Amenaza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.General.GestorPoderes;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ControladorTablero{
    @FXML private GridPane tableroGrid;
    private Juego juego;
    private Integer posicionOrigenFila;
    private Integer posicionOrigenColumna;
    private GestorPoderes gestorPoderes;
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

    public void setGestorPoderes(GestorPoderes gestorPoderes) {
        this.gestorPoderes = gestorPoderes;
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
        //System.out.println("Click en: " + fila + ", " + columna);
        // Obtener la pieza en la posición actual, si existe
        Optional<Pieza> piezaActual = juego.getPiezaActual(fila, columna);
        if (esPrimeraSeleccion()) {
            // Primer click
            if (piezaActual.isPresent()) {
                Pieza pieza = piezaActual.get();
                // Si el jugador está tocando una pieza del color correcto para su turno
                if (juego.getColorJugadorActual() == pieza.getColor()) {
                    //System.out.println("Primer click en una pieza del color del jugador actual");
                    manejarPrimerClick(pieza, fila, columna);  // Guardar selección y pintar
                }
                else {
                    // Seleccionando una pieza del rival, por ejemplo para poderes
                    //System.out.println("Primer click en una pieza del color rival");
                    aplicarColorCasillero(fila, columna);  // Pintar casillero de pieza rival
                    guardarPosicionOrigen(fila, columna);  // Guardar la selección
                }
            }
            else {
                // Si se selecciona un casillero vacío en el primer click, no se hace nada
                //System.out.println("Primer click en casillero vacío, no se hace nada");
            }
        }
        else {
            // Segundo click
            //System.out.println("Segundo click");
            if(fila != this.posicionOrigenFila || columna != this.posicionOrigenColumna){
                if (piezaActual.isPresent()) {
                    Pieza pieza = piezaActual.get();
                    if (juego.getColorJugadorActual() == pieza.getColor()) {
                        // Click en una nueva pieza propia, cambiar la selección
                        //System.out.println("Cambiando selección a una nueva pieza del mismo jugador");
                        quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
                        quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                        manejarPrimerClick(pieza, fila, columna);  // Cambiar selección y pintar el nuevo casillero
                    }
                    else {
                        // Segundo click en pieza del rival, deseleccionar
                        //System.out.println("Click en pieza rival");
                        manejarSegundoClick(fila, columna);  // Ejecutar movimiento
                        quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                        quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
                        limpiarSeleccion();  // No se cambia turno
                    }
                }
                else {
                    // Segundo click en un casillero vacío,
                    manejarSegundoClick(fila, columna);  // Ejecutar movimiento
                    quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                }
            }
            else {
                //Segundo click en la misma pieza, deseleccionar
                quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
                limpiarSeleccion();  // No se cambia turno
            }
        }
    }


    private void manejarPrimerClick(Pieza piezaActual ,int fila, int columna) {
        aplicarColorCasillero(fila, columna);
        guardarPosicionOrigen(fila, columna); //Cuenta como seleccionar una pieza, el siguiente click se gestiona como el segundo
        gestorPoderes.setPosiciones(fila, columna); // Establecer posiciones
        juego.actualizarMovimientosPieza(fila, columna);
        juego.gestionarJaque();
        juego.gestionarEnroque();
        mostrarMovimientosPosibles(piezaActual);
    }

    private void manejarSegundoClick(int fila, int columna) {
        if(fila != this.posicionOrigenFila || columna != this.posicionOrigenColumna){
            gestorPoderes.setPosiciones(fila, columna); // Establecer posiciones
            boolean movimientoValido = juego.mover(this.posicionOrigenFila, this.posicionOrigenColumna, fila, columna);
            if (movimientoValido) {
                moverPieza(fila, columna);
                gestionarSiHayEnroque();
                juego.actualizarMovimientosPieza(fila, columna);
                tableroGrid.fireEvent(new EventoJuego(EventoJuego.CAMBIO_DE_TURNO_EVENT));
            } else {
                System.out.println("Movimiento invalido, se muestra la vista del error");
            }
        }
    }

    private void moverPieza(int fila, int columna) {
        System.out.println("Moviendo pieza");
        ImageView imageView = (ImageView) this.posiciones[this.posicionOrigenFila][this.posicionOrigenColumna].getChildren().remove(1);

        if (this.posiciones[fila][columna].getChildren().size() == Constantes.NO_TIENE_IMAGEN) {
            this.posiciones[fila][columna].getChildren().add(imageView);
        }else{
            this.posiciones[fila][columna].getChildren().remove(Constantes.INDICE_IMAGEN);
            this.posiciones[fila][columna].getChildren().add(imageView);
        }
        quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
        limpiarSeleccion();  // Limpiar selección después del segundo click
    }

    private void gestionarSiHayEnroque() {
        Pieza piezaCapturada = juego.getUltimaPiezaCapturada();
        if(piezaCapturada != null){
            if(Objects.equals(piezaCapturada.getTipoDePieza(), Constantes.TORRE)){
                Torre torre = (Torre) piezaCapturada;
                if(torre.seHaEnrocado()){
                    System.out.println("Se capturo una torre luego del enroque");
                    Coordenada2D posicionAnterior = piezaCapturada.getPosicionAnterior();
                    Coordenada2D posicionActual = piezaCapturada.getPosicionActual();
                    System.out.println("Posicion anterior: " + posicionAnterior.getRow() + ", " + posicionAnterior.getCol());
                    System.out.println("Posicion actual: " + posicionActual.getRow() + ", " + posicionActual.getCol());
                    this.posicionOrigenFila = posicionAnterior.getRow();
                    this.posicionOrigenColumna = posicionAnterior.getCol();
                    moverPieza(posicionActual.getRow(), posicionActual.getCol());
                }
            }
        }
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

    private void quitarColorCasilleroSeleccionado(Integer fila, Integer columna) {
        vistaTablero.pintarCasilleroColorOriginal(fila, columna);
    }

    private void mostrarMovimientosPosibles(Pieza piezaActual) {
            vistaTablero.mostrarMovimientosPosibles(piezaActual.getMovimientosPosibles());
    }

    private void quitarMovimientosPosibles() {
        vistaTablero.limpiarCasillerosPintados();
    }

}
