package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
//import org.fiuba.algoritmos3.tp1powechess.Model.Configuracion;
import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Model.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Vista.vistaJuego;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorJuego implements EventHandler<EventoCambioDeTurno>{
    @FXML
    public Rectangle jugador1_color;
    @FXML
    public Rectangle jugador2_color;
    private Juego juego;
    @FXML
    private ControladorTablero controladorTablero = new ControladorTablero();
    @FXML
    private Label jugador1;
    @FXML
    private Label jugador2;
    @FXML
    private Label jugadorActual;

    private vistaJuego vistaJuego = new vistaJuego();

    private static final Map<Configuracion.ColoresJugadores, Color> colores = Map.ofEntries(
            Map.entry(Configuracion.ColoresJugadores.BLANCO, Color.WHITE),
            Map.entry(Configuracion.ColoresJugadores.NEGRO, Color.BLACK)
    );

    public void setJuego(Juego juego) {
        this.juego = juego;
        this.jugador1.setText(juego.getJugadorBlancas().getNombre());
        this.jugador2.setText(juego.getJugadorNegras().getNombre());
        this.jugador1_color.setFill(colores.get(juego.getJugadorBlancas().getColor()));
        this.jugador2_color.setFill(colores.get(juego.getJugadorNegras().getColor()));
        this.jugadorActual.setText("Jugador actual: " + juego.getJugadorActual().getNombre());
        controladorTablero.setJuego(juego);
    }

    @Override
    public void handle(EventoCambioDeTurno cambioDeTurnoEvent) {
        this.juego.cambiarTurno();
        this.jugadorActual.setText("Jugador actual: " + juego.getJugadorActual().getNombre());
    }


    public void mostrarConfirmacionCierre(WindowEvent windowEvent) {
        vistaJuego.mostrarConfirmacionCierre(windowEvent);
    }
}
