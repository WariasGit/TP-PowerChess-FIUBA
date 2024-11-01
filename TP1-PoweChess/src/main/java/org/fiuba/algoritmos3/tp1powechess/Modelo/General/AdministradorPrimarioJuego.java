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

/**
 * La clase AdministradorPrimarioJuego es la encargada de manejar los eventos principales del flujo de juego.
 * Como la inicialización, carga de partidas, gestión de fin de partida y navegación entre ventanas (menú principal y ventana de juego).
 * Implementa la interfaz EventHandler<EventoJuego> para responder a diferentes tipos de eventos que ocurren en el juego.
 * Actúa como mediador entre las diferentes etapas del juego y la interfaz gráfica.
 */
public class AdministradorPrimarioJuego implements EventHandler<EventoJuego> {
    private Juego Ajedrez;
    private GestorPoderes gestorPoderes;
    private final Stage stage;
    Reproductor reproductor = new Reproductor();

    /**
     * Constructor de la clase AdministradorPrimarioJuego.
     * @param stage La ventana (Stage) en la que se ejecuta el juego.
     */
    public AdministradorPrimarioJuego(Stage stage) {
        this.stage = stage;
    }

    /**
     * Maneja los eventos del juego. Dependiendo del tipo de evento recibido, se ejecutan diferentes acciones,
     * como iniciar el juego, volver al menú, cargar una partida guardada, terminar la partida o salir del juego.
     * @param evento El evento de juego a manejar.
     */
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

    /**
     * Inicializa un nuevo juego de ajedrez cargando los jugadores y la partida desde un archivo.
     * @param path La ruta del archivo que contiene la información inicial de la partida en formato FEN.
     * @throws IOException Si ocurre un error al cargar la partida.
     */
    private void iniciarJuego(String path) throws IOException {
        Jugador jugadorBlancas = new Jugador(Configuracion.ColoresJugadores.BLANCO);
        Jugador jugadorNegras = new Jugador(Configuracion.ColoresJugadores.NEGRO);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorBlancas);
        jugadores.add(jugadorNegras);
        this.Ajedrez = new Juego(jugadores);
        this.gestorPoderes = new GestorPoderes(Ajedrez);
        Ajedrez.getTurno().setGestorPoderes(gestorPoderes);
        Ajedrez.cargarPartida(path);
        iniciarVentanaJuego();
    }

    /**
     * Carga una partida guardada desde un archivo.
     * @throws IOException Si ocurre un error al cargar la partida guardada.
     */
    private void cargarPartidaGuardada() throws IOException {
        iniciarJuego(Constantes.RUTA_ARCHIVO_PARTIDDA_GUARDADA);
    }

    /**
     * Inicia la ventana de juego y configura los controladores de eventos necesarios.
     * @throws IOException Si ocurre un error al cargar la interfaz de usuario del juego.
     */
    private void iniciarVentanaJuego() throws IOException {
        reproductor.reproducirMusicaJuego();
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

    /**
     * Inicia la ventana principal del juego y configura los controladores de eventos necesarios.
     * @throws IOException Si ocurre un error al cargar la interfaz de usuario del menú principal.
     */
    public void iniciarVentanaPrincipal() throws IOException {
        reproductor.reproducirMusicaMenu();
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

    /**
     * Muestra una ventana de confirmación al intentar cerrar la aplicación.
     * @param windowEvent El evento de ventana que se dispara al intentar cerrar.
     */
    public void mostrarConfirmacionCierre(WindowEvent windowEvent) {
        VistaPrimaria.mostrarConfirmacionCierre(windowEvent);
    }
}
