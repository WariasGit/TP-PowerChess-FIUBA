package org.fiuba.algoritmos3.tp1powechess;

import javafx.application.Application;
import javafx.stage.Stage;
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorPrimario;
import org.fiuba.algoritmos3.tp1powechess.Model.General.AdministradorPrimarioJuego;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AdministradorPrimarioJuego administrador = new AdministradorPrimarioJuego(stage);
        ControladorPrimario controladorPrimario = new ControladorPrimario();
        controladorPrimario.iniciarVentanaPrincipal(stage, administrador);
    }
}
