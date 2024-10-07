package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoEstadoPartida;
import org.fiuba.algoritmos3.tp1powechess.Model.General.AdministradorPrimarioJuego;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;
import org.fiuba.algoritmos3.tp1powechess.Vista.VistaPrimaria;
import java.io.IOException;

public class ControladorPrimario {
    public Pane panePrincipal;

    public void iniciarVentanaPrincipal(Stage stage, AdministradorPrimarioJuego administrador) throws IOException {
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource(Constantes.RUTA_INICIO_FXML));
        Pane root = secondaryLoader.load();
        root.addEventHandler(EventoEstadoPartida.INICIAR_JUEGO, administrador);
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.setOnCloseRequest(ControladorPrimario.this::mostrarConfirmacionCierre);
        stage.show();
    }

    public void entrarAlJuego() {
        panePrincipal.fireEvent(new EventoEstadoPartida(EventoEstadoPartida.INICIAR_JUEGO));
    }

    public void salirDelJuego(){
        panePrincipal.fireEvent(new EventoEstadoPartida(EventoEstadoPartida.SALIR_JUEGO));
    }

    public void opciones(){
        //
    }

    public void cargarPartida(){
        //
    }

    public void mostrarConfirmacionCierre(WindowEvent windowEvent) {
        VistaPrimaria.mostrarConfirmacionCierre(windowEvent);
    }
}
