package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoPoder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Poder;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoJuego;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaJuego;

import java.util.ArrayList;
import java.util.Map;

public class ControladorSecundario {
    @FXML public FlowPane poderesNegras;
    @FXML public FlowPane poderesBlancas;
    @FXML VBox vboxPoderes;
    @FXML private Button botonOfrecerTablasNegras;
    @FXML private Button botonOfrecerTablasBlancas;
    private ArrayList<Jugador> JugadoresActuales;

    public void setJuego(ArrayList<Jugador> Jugadores) {
        JugadoresActuales = Jugadores;
        cargarPoderes();
    }

    private void cargarPoderes(){
        cargarPoderesBlancas(JugadoresActuales.get(Configuracion.Jugadores.BLANCAS));
        cargarPoderesNegras(JugadoresActuales.get(Configuracion.Jugadores.NEGRAS));
    }

    private void cargarPoderesBlancas(Jugador jugador) {
        poderesBlancas.getChildren().clear(); // Limpiar antes de recargar
        for (Map.Entry<String, Integer> entry : jugador.getListaPoderes().entrySet()) {
            String nombrePoder = entry.getKey();
            int cantidadUsos = entry.getValue();
            if (cantidadUsos > 0) {
                Button botonPoder = new Button(nombrePoder + " (" + cantidadUsos + ")");
                botonPoder.setOnAction(this::gestorPoderes);
                poderesBlancas.getChildren().add(botonPoder);
            }
        }
    }

    private void cargarPoderesNegras(Jugador jugador) {
        poderesNegras.getChildren().clear(); // Limpiar antes de recargar
        for (Map.Entry<String, Integer> entry : jugador.getListaPoderes().entrySet()) {
            String nombrePoder = entry.getKey();
            int cantidadUsos = entry.getValue();
            if (cantidadUsos > 0) {
                Button botonPoder = new Button(nombrePoder + " (" + cantidadUsos + ")");
                botonPoder.setOnAction(this::gestorPoderes);
                poderesNegras.getChildren().add(botonPoder);
            }
        }
    }


    private void gestorPoderes(javafx.event.ActionEvent actionEvent) {
        Button boton = (Button) actionEvent.getSource();
        String nombrePoder = boton.getText().split(" \\(")[0].trim(); // 'Escudo', 'Freeze', etc.

        EventType<EventoPoder> eventoPoder = Configuracion.getEventoPoder(nombrePoder);
        if (eventoPoder != null) {
            vboxPoderes.fireEvent(new EventoPoder(eventoPoder));
            System.out.println("Poder: " + nombrePoder);
            cargarPoderes();

        } else {
            System.out.println("Poder no encontrado: " + nombrePoder);
        }
    }


    public void gestionarTablas(javafx.event.ActionEvent actionEvent){
        String NombreJugadorTablas = "";
        Button boton = (Button) actionEvent.getSource();
        if (boton == botonOfrecerTablasNegras) {
            NombreJugadorTablas = Constantes.NOMBRE_NEGRAS;
        } else if (boton == botonOfrecerTablasBlancas) {
            NombreJugadorTablas = Constantes.NOMBRE_BLANCAS;
        }
        vboxPoderes.fireEvent(new EventoJuego(EventoJuego.CAMBIO_DE_TURNO_EVENT));
        Boolean continuar = VistaJuego.mostrarConfirmacionTablas(NombreJugadorTablas);
        if(continuar){
            vboxPoderes.fireEvent(new EventoJuego(EventoJuego.TABLAS_ACEPTADAS_EVENT));
        }
        else {
            vboxPoderes.fireEvent(new EventoJuego(EventoJuego.CAMBIO_DE_TURNO_EVENT));
        }
    }

    public void gestionarAbandono(javafx.event.ActionEvent actionEvent){
        Boolean continuar = VistaJuego.mostrarConfirmacionAbandono();
        if(continuar){
            vboxPoderes.fireEvent(new EventoJuego(EventoJuego.RENDIRSE_EVENT));
        }
    }
}
