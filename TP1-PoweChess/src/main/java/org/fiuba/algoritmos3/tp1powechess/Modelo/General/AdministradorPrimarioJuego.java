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
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorTablero;
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
    private Juego Ajedrez;
    private GestorPoderes gestorPoderes;
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
        else if(evento.getEventType().equals(EventoJuego.TERMINAR_PARTIDA)) {
            Configuracion.EstadoJuego estadoJuego = Ajedrez.getEstado();
            String nombreGanador = Ajedrez.getNombreGanador();
            System.out.println("El ganador es: " + nombreGanador);
            boolean jugarDeNuevo =  VistaPrimaria.mostrarMensajeFinDePartida(estadoJuego, nombreGanador);
            if (jugarDeNuevo){
                try {
                    iniciarJuego(Constantes.RUTA_ARCHIVO_INICIO_FEN);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                try {
                    iniciarVentanaPrincipal();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        Jugador jugadorBlancas = new Jugador(Configuracion.ColoresJugadores.BLANCO);
        Jugador jugadorNegras = new Jugador(Configuracion.ColoresJugadores.NEGRO);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorBlancas);
        jugadores.add(jugadorNegras);
        this.Ajedrez = new Juego(jugadores);
        this.gestorPoderes = new GestorPoderes(Ajedrez);
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
        juegoController.setGestorPoderes(this.gestorPoderes);
        ControladorTablero controladorTablero= juegoController.getControladorTablero();
        this.gestorPoderes.setControladorTablero(controladorTablero);
        root.addEventHandler(EventoJuego.CAMBIO_DE_TURNO_EVENT, juegoController);
        root.addEventHandler(EventoJuego.TABLAS_ACEPTADAS_EVENT, juegoController);
        root.addEventHandler(EventoJuego.RENDIRSE_EVENT, juegoController);
        root.addEventHandler(EventoJuego.VOLVER_AL_MENU, this);
        root.addEventHandler(EventoJuego.TERMINAR_PARTIDA, this);
        root.addEventHandler(EventoPoder.ESCUDO, evento -> gestorPoderes.activarEscudo());
        root.addEventHandler(EventoPoder.FREEZE, evento -> gestorPoderes.activarFreeze());
        root.addEventHandler(EventoPoder.LIMPIEZA, evento -> gestorPoderes.activarLimpieza());
        root.addEventHandler(EventoPoder.ROBAR, evento -> gestorPoderes.activarRobar());
        Scene scene = new Scene(root, Configuracion.TamanioVentana.ANCHO, Configuracion.TamanioVentana.ALTO);
        stage.setScene(scene);
        stage.setOnCloseRequest(juegoController::mostrarConfirmacionCierre);
        stage.show();
    }

    public void iniciarVentanaPrincipal() throws IOException {
        //reproductor.reproducirMusicaMenu();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.RUTA_INICIO_FXML));
        Pane root = loader.load();
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
