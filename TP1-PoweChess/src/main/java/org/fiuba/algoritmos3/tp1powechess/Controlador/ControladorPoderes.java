package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Poder;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaPoderes;

import java.util.ArrayList;

public class ControladorPoderes {
    @FXML public FlowPane poderesNegras;
    @FXML public FlowPane poderesBlancas;
    @FXML VBox vboxPoderes;
    private ArrayList<Jugador> JugadoresActuales;
    private VistaPoderes vistaPoderes = new VistaPoderes();

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
        for(Poder poder: jugador.getListaPoderes()){
            Button botonPoder = new Button(poder.getNombre());
            botonPoder.setOnAction(event -> {
                gestorPoderes();
            });
            poderesBlancas.getChildren().add(botonPoder);
        }
    }

    private void cargarPoderesNegras(Jugador jugador) {
        System.out.println("Cargando negras");
        for(Poder poder: jugador.getListaPoderes()){
            Button botonPoder = new Button(poder.getNombre());
            botonPoder.setOnAction(event -> {
                gestorPoderes();
            });
            poderesNegras.getChildren().add(botonPoder);
        }
    }

    private void gestorPoderes(){
        System.out.println("Usando el poder: ");
    }
}
