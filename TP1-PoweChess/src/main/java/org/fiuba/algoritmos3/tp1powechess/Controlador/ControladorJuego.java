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
import org.fiuba.algoritmos3.tp1powechess.Modelo.General.GestorPoderes;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaJuego;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaPrimaria;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


public class ControladorJuego implements EventHandler<EventoJuego>{
    private Juego juego;
    private GestorPoderes gestorPoderes;
    private ControladorTablero controladorTablero;
    private ControladorSecundario controladorSecundario;
    @FXML private VBox poderes;
    @FXML private GridPane tablero;
    @FXML private Rectangle jugador1_color;
    @FXML private Rectangle jugador2_color;
    @FXML private Label nombreJugador1;
    @FXML private Label nombreJugador2;
    @FXML private Label nombreJugadorActual;

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
        cargarNombresJugadores();
        this.nombreJugador1.setText(juego.getNombreJugadorBlancas());
        this.nombreJugador2.setText(juego.getNombreJugadorNegras());
        this.jugador1_color.setFill(colores.get(Configuracion.ColoresJugadores.BLANCO));
        this.jugador2_color.setFill(colores.get(Configuracion.ColoresJugadores.NEGRO));
        this.nombreJugadorActual.setText("Es turno de: " + juego.getNombreJugadorActual());
        controladorTablero.setJuego(juego);
        controladorSecundario.setJuego(juego.getJugadores());
    }

    public void setGestorPoderes(GestorPoderes gestorPoderes) {
        this.gestorPoderes = gestorPoderes;
        controladorTablero.setGestorPoderes(gestorPoderes);
    }

    private static final Map<Configuracion.ColoresJugadores, Color> colores = Map.ofEntries(
            Map.entry(Configuracion.ColoresJugadores.BLANCO, Color.WHITE),
            Map.entry(Configuracion.ColoresJugadores.NEGRO, Color.BLACK)
    );

    @Override
    public void handle(EventoJuego juegoEvent) {
        if(juegoEvent.getEventType().equals(EventoJuego.CAMBIO_DE_TURNO_EVENT)){
            this.juego.cambiarTurno();
            this.nombreJugadorActual.setText("Jugador actual: " + juego.getNombreJugadorActual());
            this.juego.gestionarJaque();
            if(!juego.sigueElJuego()){
                generarEventoFinDePartida();
            }
        }
        else if (juegoEvent.getEventType().equals(EventoJuego.TABLAS_ACEPTADAS_EVENT)) {
            this.juego.establecerTablas();
            generarEventoFinDePartida();
        }
        else if (juegoEvent.getEventType().equals(EventoJuego.RENDIRSE_EVENT)) {
            this.juego.gestionarRendicion();
            generarEventoFinDePartida();
        }
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

    private void generarEventoFinDePartida(){
        tablero.fireEvent(new EventoJuego(EventoJuego.TERMINAR_PARTIDA));
    }

    public void cargarNombresJugadores() {
        String[] nombresJugadores = VistaJuego.pedirNombresJugadores();
        String nombreBlancas = nombresJugadores[0];
        String nombreNegras = nombresJugadores[1];
        juego.setNombreJugadorBlancas(nombreBlancas);
        juego.setNombreJugadorNegras(nombreNegras);
        System.out.println("Nombres establecidos: Blancas - " + nombreBlancas + ", Negras - " + nombreNegras);
    }

    public ControladorTablero getControladorTablero() {
        return controladorTablero;
    }
}
