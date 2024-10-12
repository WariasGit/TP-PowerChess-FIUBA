package org.fiuba.algoritmos3.tp1powechess;

import javafx.application.Application;
import javafx.stage.Stage;
import org.fiuba.algoritmos3.tp1powechess.Modelo.General.AdministradorPrimarioJuego;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AdministradorPrimarioJuego administrador = new AdministradorPrimarioJuego(stage);
        administrador.iniciarVentanaPrincipal();
    }
}
