package org.fiuba.algoritmos3.tp1powechess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorJuego;
import org.fiuba.algoritmos3.tp1powechess.Controlador.EventoCambioDeTurno;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Jugador j1 = new Jugador(Configuracion.ColoresJugadores.BLANCO, "Uno");
        Jugador j2 = new Jugador(Configuracion.ColoresJugadores.NEGRO, "Dos");

        //Pongo el blanco primero en la lista
        List<Jugador> jugadores = Arrays.asList(j1, j2);
        jugadores.sort((j1, j2) -> j1.getColor().equals("blanco") ? -1 : 1);

    }
        Juego Ajedrez = new Juego(jugadores);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.RUTA_JUEGO_FXML));
        VBox root;
        root = loader.load();
        ControladorJuego juegoController = loader.getController();
        juegoController.setJuego(juego);
        root.addEventHandler(EventoCambioDeTurno.CAMBIO_DE_TURNO_EVENT, juegoController);
        Scene scene = new Scene(root, 640, 700);
        stage.setScene(scene);
        stage.setOnCloseRequest(juegoController::mostrarConfirmacionCierre);
        stage.show();
    }

}
