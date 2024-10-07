package org.fiuba.algoritmos3.tp1powechess.Model.General;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorJuego;
import org.fiuba.algoritmos3.tp1powechess.Controlador.EventoCambioDeTurno;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorPrimarioJuego {
    Jugador jugadorBlancas;
    Jugador jugadorNegras;
    Juego Ajedrez;
    private Stage stage;

    public AdministradorPrimarioJuego(Stage stage) {
        this.stage = stage;
    }


    public void iniciarJuego() throws IOException {
        this.jugadorBlancas = new Jugador(Configuracion.ColoresJugadores.BLANCO, "Uno");
        this.jugadorNegras = new Jugador(Configuracion.ColoresJugadores.NEGRO, "Dos");
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorBlancas);
        jugadores.add(jugadorNegras);
        this.Ajedrez = new Juego(jugadores);
        iniciarVentanaJuego();
    }

    private void iniciarVentanaJuego() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.RUTA_JUEGO_FXML));
        VBox root;
        root = loader.load();
        ControladorJuego juegoController = loader.getController();
        juegoController.setJuego(Ajedrez);
        root.addEventHandler(EventoCambioDeTurno.CAMBIO_DE_TURNO_EVENT, juegoController);
        Scene scene = new Scene(root, Configuracion.TamanioVentana.ANCHO, Configuracion.TamanioVentana.ALTO);
        stage.setScene(scene);
        stage.setOnCloseRequest(juegoController::mostrarConfirmacionCierre);
        stage.show();
    }
}
