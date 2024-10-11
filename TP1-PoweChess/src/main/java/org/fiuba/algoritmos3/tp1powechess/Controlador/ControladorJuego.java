package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoJuego;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaPrimaria;
import java.io.IOException;
import java.util.Map;


public class ControladorJuego implements EventHandler<EventoJuego>{
    private Juego juego;
    private ControladorTablero controladorTablero;
    private ControladorSecundario controladorSecundario;
    @FXML private VBox contenedor;
    @FXML private VBox poderes;
    @FXML private GridPane tablero;
    @FXML private Rectangle jugador1_color;
    @FXML private Rectangle jugador2_color;
    @FXML private Label jugador1;
    @FXML private Label jugador2;
    @FXML private Label jugadorActual;

    @FXML private void initialize() {
        try {
            FXMLLoader childLoader = new FXMLLoader(getClass().getResource("/org/fiuba/algoritmos3/tp1powechess/tablero-vista.fxml"));
            Parent tableroNode = childLoader.load();
            tablero.getChildren().add(tableroNode);
            controladorTablero = childLoader.getController();
        } catch(IOException e) {
            System.out.println("Error al cargar el controlador del tablero");
        }
        try {
            FXMLLoader childLoader = new FXMLLoader(getClass().getResource("/org/fiuba/algoritmos3/tp1powechess/poderes-vista.fxml"));
            Parent poderesNode = childLoader.load();
            poderes.getChildren().add(poderesNode);
            controladorSecundario = childLoader.getController();
        } catch(IOException e) {
            System.out.println("Error al cargar el controlador de los poderes");
        }
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
        this.jugador1.setText(juego.getNombreJugadorBlancas());
        this.jugador2.setText(juego.getNombreJugadorNegras());
        this.jugador1_color.setFill(colores.get(Configuracion.ColoresJugadores.BLANCO));
        this.jugador2_color.setFill(colores.get(Configuracion.ColoresJugadores.NEGRO));
        this.jugadorActual.setText("Jugador actual: " + juego.getNombreJugadorActual());
        controladorTablero.setJuego(juego);
        controladorSecundario.setJuego(juego.getJugadores());
    }

    private static final Map<Configuracion.ColoresJugadores, Color> colores = Map.ofEntries(
            Map.entry(Configuracion.ColoresJugadores.BLANCO, Color.WHITE),
            Map.entry(Configuracion.ColoresJugadores.NEGRO, Color.BLACK)
    );

    @Override
    public void handle(EventoJuego cambioDeTurnoEvent) {
        this.juego.cambiarTurno();
        this.jugadorActual.setText("Jugador actual: " + juego.getNombreJugadorActual());
    }

    public void mostrarConfirmacionCierre(WindowEvent windowEvent) {
        VistaPrimaria.mostrarConfirmacionCierre(windowEvent);
    }

    public void volverAlMenu(){
        tablero.fireEvent(new EventoJuego(EventoJuego.VOLVER_AL_MENU));
    }

    public void guardarPartida() throws IOException {
        juego.guardarPartida();
    }
}
