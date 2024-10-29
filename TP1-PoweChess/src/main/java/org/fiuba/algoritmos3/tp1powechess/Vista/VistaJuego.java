package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;

import java.util.Optional;


public class VistaJuego {
    public static Boolean mostrarConfirmacionTablas(String NombreJugador) {
        String encabezado = NombreJugador + Constantes.ENCABEZADO_TABLAS;
        return (VistaAlerta.mostrarAlertaEvento(Constantes.TITULO_TABLAS, encabezado, Constantes.PREGUNTA_TABLAS));
    }

    public static Boolean mostrarConfirmacionAbandono() {
        return (VistaAlerta.mostrarAlertaEvento(Constantes.TITULO_ABANDONO, Constantes.ENCABEZADO_ABANDONO, Constantes.PREGUNTA_ABANDONO));
    }

    public static String[] pedirNombresJugadores() {
        // Crear la alerta
        Alert dialogoNombres = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoNombres.setTitle("Nombres de Jugadores");
        dialogoNombres.setHeaderText("Ingrese los nombres de los jugadores.\n Si presiona 'Cancelar', se asignaran nombres por defecto.");

        // Crear los campos de texto para los nombres
        TextField nombreBlancas = new TextField();
        nombreBlancas.setPromptText("Jugador Blancas");
        TextField nombreNegras = new TextField();
        nombreNegras.setPromptText("Jugador Negras");
        // Disponer los campos de texto en un GridPane
        GridPane layout = new GridPane();
        layout.add(new Label("Jugador Blancas:"), 0, 0);
        layout.add(nombreBlancas, 1, 0);
        layout.add(new Label("Jugador Negras:"), 0, 1);
        layout.add(nombreNegras, 1, 1);
        dialogoNombres.getDialogPane().setContent(layout);

        // Configurar los botones
        ButtonType aceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialogoNombres.getButtonTypes().setAll(aceptar, ButtonType.CANCEL);
        // Obtener el botón Aceptar de la alerta y desactivarlo inicialmente
        Button botonAceptar = (Button) dialogoNombres.getDialogPane().lookupButton(aceptar);
        botonAceptar.setDisable(true);
        // Listener para habilitar el botón solo si ambos campos tienen texto
        nombreBlancas.textProperty().addListener((observable, oldValue, newValue) -> {
            botonAceptar.setDisable(nombreBlancas.getText().trim().isEmpty() || nombreNegras.getText().trim().isEmpty());
        });
        nombreNegras.textProperty().addListener((observable, oldValue, newValue) -> {
            botonAceptar.setDisable(nombreBlancas.getText().trim().isEmpty() || nombreNegras.getText().trim().isEmpty());
        });

        DialogPane dialogPane = dialogoNombres.getDialogPane();
        dialogPane.getStylesheets().add(VistaAlerta.class.getResource("/org/fiuba/algoritmos3/tp1powechess/css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> resultado = dialogoNombres.showAndWait();
        if (resultado.isPresent() && resultado.get() == aceptar) {
            String nombreJugadorBlancas = nombreBlancas.getText().trim();
            String nombreJugadorNegras = nombreNegras.getText().trim();
            return new String[]{nombreJugadorBlancas, nombreJugadorNegras};
        }
        return Configuracion.NombresJugadoresPorDefecto;
    }
}
