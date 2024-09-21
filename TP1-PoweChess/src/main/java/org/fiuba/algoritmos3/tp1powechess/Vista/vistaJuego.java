package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class vistaJuego {
    public void mostrarConfirmacionCierre(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de cierre");
        alert.setHeaderText("Estás a punto de salir del juego");
        alert.setContentText("¿Estás seguro de que deseas cerrar el juego?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
            event.consume();
        }
    }
}
