package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoJuego;
import org.fiuba.algoritmos3.tp1powechess.Model.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaJuego;

import java.util.ArrayList;

public class ControladorPoderes {
    @FXML public FlowPane poderesNegras;
    @FXML public FlowPane poderesBlancas;
    @FXML VBox vboxPoderes;
    @FXML private Button botonOfrecerTablasNegras;
    @FXML private Button botonRendirseNegras;
    @FXML private Button botonOfrecerTablasBlancas;
    @FXML private Button botonRendirseBlancas;
    private ArrayList<Jugador> JugadoresActuales;

    public void setJuego(ArrayList<Jugador> Jugadores) {
        JugadoresActuales = Jugadores;
        cargarPoderes();
    }

    private void cargarPoderes(){
        System.out.println("Cargando poderes");
        cargarPoderesBlancas(JugadoresActuales.get(Configuracion.Jugadores.BLANCAS));
        cargarPoderesNegras(JugadoresActuales.get(Configuracion.Jugadores.NEGRAS));
    }

    private void cargarPoderesBlancas(Jugador jugador) {
        System.out.println("Cargando blancas");
        for(Integer poder: jugador.getListaPoderes()){
            Button botonPoder = new Button("Poder: " + poder);
            botonPoder.setOnAction(event -> {
            });
            poderesBlancas.getChildren().add(botonPoder);
        }
    }

    private void cargarPoderesNegras(Jugador jugador) {
        System.out.println("Cargando negras");
        for(Integer poder: jugador.getListaPoderes()){
            Button botonPoder = new Button("Poder: " + poder);
            botonPoder.setOnAction(event -> {
            });
            poderesNegras.getChildren().add(botonPoder);
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
            //juego.gestionarRendicion();
        }
    }
}
