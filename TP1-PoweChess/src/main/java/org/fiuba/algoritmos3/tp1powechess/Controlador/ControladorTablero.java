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
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoJuego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.General.GestorPoderes;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Torre;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.Coordenada2D;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.*;
import java.util.Objects;
import java.util.Optional;

public class ControladorTablero{
    @FXML private GridPane tableroGrid;
    private Juego juego;
    private Integer posicionOrigenFila;
    private Integer posicionOrigenColumna;
    private GestorPoderes gestorPoderes;
    private final StackPane[][] posiciones = new StackPane[Configuracion.TamanioVentana.DIMENSION_TABLERO][Configuracion.TamanioVentana.DIMENSION_TABLERO];
    private final VistaTablero vistaTablero = new VistaTablero(posiciones);
    private final VistaPoderes vistaPoderes = new VistaPoderes(posiciones);

    public void initialize() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane celdaPrincipal = new StackPane();
                StackPane celdaSecundaria = new StackPane();
                celdaPrincipal.setOnMouseClicked(this::handleEventoClick);
                Rectangle rectanguloColor = new Rectangle(75, 75);
                rectanguloColor.setArcHeight(2.0);
                rectanguloColor.setArcWidth(2.0);
                rectanguloColor.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.web("#0000006e"));
                rectanguloColor.setStroke(Color.BLACK);
                rectanguloColor.setStrokeType(StrokeType.INSIDE);
                celdaPrincipal.getChildren().add(rectanguloColor);
                celdaPrincipal.getChildren().add(celdaSecundaria);
                tableroGrid.add(celdaPrincipal, col, row);
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
            StackPane celdaSecundaria = (StackPane) ((StackPane) stackPane).getChildren().get(1);
            Optional<Pieza> pieza = tablero.getPieza(i, j);
            if (pieza.isPresent()) {
                Pieza piezaActual = pieza.get();
                ImageView vistaImagen = new GeneradorVistaPieza().crearVistaPieza(piezaActual);
                vistaImagen.setFitWidth(70);
                vistaImagen.setFitHeight(70);
                vistaImagen.setPreserveRatio(true);
                celdaSecundaria.getChildren().add(vistaImagen);
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
                    gestorPoderes.setPosiciones(fila, columna); // Establecer posiciones
                    aplicarColorCasillero(fila, columna);  // Pintar casillero de pieza rival
                    guardarPosicionOrigen(fila, columna);  // Guardar la selección
                }
            }
        }
        else {
            if(fila != this.posicionOrigenFila || columna != this.posicionOrigenColumna){
                if (piezaActual.isPresent()) {
                    Pieza pieza = piezaActual.get();
                    if (juego.getColorJugadorActual() == pieza.getColor()) {
                        quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
                        quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                        manejarPrimerClick(pieza, fila, columna);  // Cambiar selección y pintar el nuevo casillero
                    }
                    else {
                        manejarSegundoClick(fila, columna);  // Ejecutar movimiento
                        quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                        quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
                        limpiarSeleccion();  // No se cambia turno
                    }
                }
                else {
                    manejarSegundoClick(fila, columna);  // Ejecutar movimiento
                    quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                }
            }
            else {
                gestorPoderes.setPosiciones(fila, columna); // Establecer posiciones
                quitarMovimientosPosibles();  // Remover las posibles jugadas mostradas
                quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
                limpiarSeleccion();  // No se cambia turno
            }
        }
    }

    private void manejarPrimerClick(Pieza piezaActual ,int fila, int columna) {
        aplicarColorCasillero(fila, columna);
        guardarPosicionOrigen(fila, columna); //Cuenta como seleccionar una pieza, el siguiente click se gestiona como el segundo
        juego.actualizarMovimientosPieza(fila, columna);
        gestorPoderes.setPosiciones(fila, columna); // Establecer posiciones
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
                tableroGrid.fireEvent(new EventoJuego(EventoJuego.CAMBIO_DE_TURNO_EVENT));
            } else {
                System.out.println("Movimiento invalido, se muestra la vista del error");
            }
        }
    }

    private void moverPieza(int destinoFila, int destinoColumna) {
        // Obtener la celda de origen y destino
        System.out.println("La posicion de origen es: " + posicionOrigenFila + ", " + posicionOrigenColumna);
        System.out.println("La posicion de destino es: " + destinoFila + ", " + destinoColumna);
        StackPane celdaOrigen = this.posiciones[this.posicionOrigenFila][this.posicionOrigenColumna];
        StackPane celdaDestino = this.posiciones[destinoFila][destinoColumna];
        // Obtener la celda secundaria, que contiene la imagen de las piezas y los stickers de poderes.
        StackPane contenidoOrigen = (StackPane) celdaOrigen.getChildren().get(Constantes.INDICE_IMAGEN);

        if (this.posiciones[destinoFila][destinoColumna].getChildren().size() == Constantes.NO_TIENE_IMAGEN) {
            // Mover el contenido (pieza + stickers) a la celda de destino
            celdaDestino.getChildren().add(contenidoOrigen);
        }else{
            this.posiciones[destinoFila][destinoColumna].getChildren().remove(Constantes.INDICE_IMAGEN);
            // Mover el contenido (pieza + stickers) a la celda de destino
            celdaDestino.getChildren().add(contenidoOrigen);
        }
        // Limpiar la celda de origen
        celdaOrigen.getChildren().remove(contenidoOrigen);
        quitarColorCasilleroSeleccionado(this.posicionOrigenFila, this.posicionOrigenColumna);
    }

    private void gestionarSiHayEnroque() {
        Pieza piezaCapturada = juego.getUltimaPiezaCapturada();
        if(piezaCapturada != null){
            if(Objects.equals(piezaCapturada.getTipoDePieza(), Constantes.TORRE)){
                Torre torre = (Torre) piezaCapturada;
                if(torre.seHaEnrocado()){
                    Coordenada2D posicionAnterior = piezaCapturada.getPosicionAnterior();
                    Coordenada2D posicionActual = piezaCapturada.getPosicionActual();
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

    private void aplicarColorCasillero(Integer fila, Integer columna) {vistaTablero.pintarCasilleroSeleccionado(fila, columna);}

    private void quitarColorCasilleroSeleccionado(Integer fila, Integer columna) {vistaTablero.pintarCasilleroColorOriginal(fila, columna);}

    private void mostrarMovimientosPosibles(Pieza piezaActual) {vistaTablero.mostrarMovimientosPosibles(piezaActual.getMovimientosPosibles());}

    private void quitarMovimientosPosibles() {
        vistaTablero.limpiarCasillerosPintados();
    }

    public void agregarEscudo(){vistaPoderes.setEscudo(posicionOrigenFila, posicionOrigenColumna);}

    public void agregarCongelado(){vistaPoderes.setCongelado(posicionOrigenFila, posicionOrigenColumna);}

}
