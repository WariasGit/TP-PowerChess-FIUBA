package org.fiuba.algoritmos3.tp1powechess.Modelo.General;

import javafx.application.Platform;
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
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoPoder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
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
    GestorPoderes gestorPoderes = new GestorPoderes(Ajedrez);
    private final Stage stage;
    //Reproductor reproductor = new Reproductor();

    public AdministradorPrimarioJuego(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(EventoJuego evento) {
        System.out.println("Evento: " + evento.getEventType());
        if (evento.getEventType().equals(EventoJuego.INICIAR_JUEGO)){
            try {
                iniciarJuego(Constantes.RUTA_ARCHIVO_INICIO_FEN);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (evento.getEventType().equals(EventoJuego.VOLVER_AL_MENU)){
            try {
                iniciarVentanaPrincipal();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(evento.getEventType().equals(EventoJuego.CARGAR_PARTIDA_GUARDADA)){
            try {
                cargarPartidaGuardada();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (evento.getEventType().equals(EventoJuego.SALIR_JUEGO)){
            System.out.println("Saliendo del juego");
            stage.close();
            Platform.exit();
            System.gc();
        }
    }


    private void iniciarJuego(String path) throws IOException {
        this.jugadorBlancas = new Jugador(Configuracion.ColoresJugadores.BLANCO, "Uno");
        this.jugadorNegras = new Jugador(Configuracion.ColoresJugadores.NEGRO, "Dos");
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorBlancas);
        jugadores.add(jugadorNegras);
        this.Ajedrez = new Juego(jugadores);
        Ajedrez.cargarPartida(path);
        iniciarVentanaJuego();
    }

    private void cargarPartidaGuardada() throws IOException {
        iniciarJuego(Constantes.RUTA_ARCHIVO_PARTIDDA_GUARDADA);
    }

    private void iniciarVentanaJuego() throws IOException {
        //reproductor.reproducirMusicaJuego();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.RUTA_JUEGO_FXML));
        VBox root;
        root = loader.load();
        ControladorJuego juegoController = loader.getController();
        juegoController.setJuego(Ajedrez);
        root.addEventHandler(EventoJuego.CAMBIO_DE_TURNO_EVENT, juegoController);
        root.addEventHandler(EventoJuego.VOLVER_AL_MENU, this);
        root.addEventHandler(EventoPoder.DOBLE_JUEGO, evento -> gestorPoderes.activarDobleJuego());
        root.addEventHandler(EventoPoder.ESCUDO, evento -> gestorPoderes.activarEscudo());
        root.addEventHandler(EventoPoder.EVOLUCION, evento -> gestorPoderes.activarEvolucion());
        root.addEventHandler(EventoPoder.FREEZE, evento -> gestorPoderes.activarFreeze());
        root.addEventHandler(EventoPoder.LIMPIEZA, evento -> gestorPoderes.activarLimpieza());
        root.addEventHandler(EventoPoder.ROBAR, evento -> gestorPoderes.activarRobar());
        root.addEventHandler(EventoPoder.VUELO, evento -> gestorPoderes.activarVuelo());
        Scene scene = new Scene(root, Configuracion.TamanioVentana.ANCHO, Configuracion.TamanioVentana.ALTO);
        stage.setScene(scene);
        stage.setOnCloseRequest(juegoController::mostrarConfirmacionCierre);
        stage.show();
    }

    public void iniciarVentanaPrincipal() throws IOException {
        //reproductor.reproducirMusicaMenu();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.RUTA_INICIO_FXML));
        Pane root = loader.load();
        this.controladorPrimario = loader.getController();
        root.addEventHandler(EventoJuego.INICIAR_JUEGO, this);
        root.addEventHandler(EventoJuego.SALIR_JUEGO, this);
        root.addEventHandler(EventoJuego.CARGAR_PARTIDA_GUARDADA, this);
        Scene scene = new Scene(root, Configuracion.TamanioVentana.ANCHO, Configuracion.TamanioVentana.ALTO);
        stage.setScene(scene);
        stage.setOnCloseRequest(AdministradorPrimarioJuego.this::mostrarConfirmacionCierre);
        stage.show();
    }

    public void mostrarConfirmacionCierre(WindowEvent windowEvent) {
        VistaPrimaria.mostrarConfirmacionCierre(windowEvent);
    }
}
