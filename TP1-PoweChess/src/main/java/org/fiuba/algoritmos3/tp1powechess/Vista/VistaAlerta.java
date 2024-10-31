package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import javafx.scene.control.DialogPane;
import javafx.stage.WindowEvent;

public class VistaAlerta {
    public static Boolean mostrarAlertaEvento(String titulo, String encabezado, String pregunta) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(pregunta);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(VistaAlerta.class.getResource("/org/fiuba/algoritmos3/tp1powechess/css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        Optional<ButtonType> resultado = alert.showAndWait();
        return (resultado.isPresent() && resultado.get() == ButtonType.OK);
    }
}
