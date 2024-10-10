package org.fiuba.algoritmos3.tp1powechess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorJuego;
import org.fiuba.algoritmos3.tp1powechess.Controlador.EventoCambioDeTurno;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Jugador j1 = new Jugador(Configuracion.ColoresJugadores.BLANCO, "Uno");
        Jugador j2 = new Jugador(Configuracion.ColoresJugadores.NEGRO, "Dos");

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1); // Añade el jugador blanco primero
        jugadores.add(j2); // Añade el jugador negro después

        Juego Ajedrez = new Juego(jugadores);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.RUTA_JUEGO_FXML));
        VBox root;
        root = loader.load();
        ControladorJuego juegoController = loader.getController();
        juegoController.setJuego(Ajedrez);
        root.addEventHandler(EventoCambioDeTurno.CAMBIO_DE_TURNO_EVENT, juegoController);
        Scene scene = new Scene(root, 900, 750);
        stage.setScene(scene);
        stage.setOnCloseRequest(juegoController::mostrarConfirmacionCierre);
        stage.show();
    }
}
