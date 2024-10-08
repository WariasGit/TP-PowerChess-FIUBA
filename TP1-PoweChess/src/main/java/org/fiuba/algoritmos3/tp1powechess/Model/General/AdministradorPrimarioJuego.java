package org.fiuba.algoritmos3.tp1powechess.Model.General;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorJuego;
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorPrimario;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoJuego;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaPrimaria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorPrimarioJuego implements EventHandler<EventoJuego> {
    Jugador jugadorBlancas;
    Jugador jugadorNegras;
    Juego Ajedrez;
    String NombreJugadorBlancas;
    String NombreJugadorNegras;
    ControladorPrimario controladorPrimario;
    private Stage stage;

    public AdministradorPrimarioJuego(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(EventoJuego evento) {
        if (evento.getEventType().equals(EventoJuego.INICIAR_JUEGO)){
            try {
                iniciarJuego();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (evento.getEventType().equals(EventoJuego.VOLVER_AL_MENU)){
            try {
                System.out.println("Volviendo al menu");
                iniciarVentanaPrincipal();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    private void iniciarJuego() throws IOException {
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
        root.addEventHandler(EventoJuego.CAMBIO_DE_TURNO_EVENT, juegoController);
        root.addEventHandler(EventoJuego.VOLVER_AL_MENU, this);
        Scene scene = new Scene(root, Configuracion.TamanioVentana.ANCHO, Configuracion.TamanioVentana.ALTO);
        stage.setScene(scene);
        stage.setOnCloseRequest(juegoController::mostrarConfirmacionCierre);
        stage.show();
    }

    public void iniciarVentanaPrincipal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.RUTA_INICIO_FXML));
        Pane root = loader.load();
        this.controladorPrimario = loader.getController();
        root.addEventHandler(EventoJuego.INICIAR_JUEGO, this);
        Scene scene = new Scene(root, Configuracion.TamanioVentana.ANCHO, Configuracion.TamanioVentana.ALTO);
        stage.setScene(scene);
        stage.setOnCloseRequest(AdministradorPrimarioJuego.this::mostrarConfirmacionCierre);
        stage.show();
    }

    public void mostrarConfirmacionCierre(WindowEvent windowEvent) {
        VistaPrimaria.mostrarConfirmacionCierre(windowEvent);
    }
}
