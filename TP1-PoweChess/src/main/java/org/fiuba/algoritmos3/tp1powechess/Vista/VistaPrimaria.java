package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;


public class VistaPrimaria {
    public static void mostrarConfirmacionCierre(WindowEvent event) {
        Boolean continuar = VistaAlerta.mostrarAlertaEvento(Constantes.TITULO_CIERRE_VENTANA, Constantes.ENCABEZADO_CIERRE_VENTANA, Constantes.PREGUNTA_CIERRE_VENTANA);
        if(!continuar) {
            event.consume();
        }
    }
}
