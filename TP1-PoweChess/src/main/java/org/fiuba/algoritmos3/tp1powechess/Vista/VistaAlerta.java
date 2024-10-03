package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.stage.WindowEvent;

public class VistaAlerta {
    public Boolean mostrarAlertaEvento(String titulo, String encabezado, String pregunta) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(pregunta);
        Optional<ButtonType> resultado = alert.showAndWait();
        return (resultado.isPresent() && resultado.get() == ButtonType.OK);
    }
}
