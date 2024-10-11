package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;


public class VistaJuego {
    public static Boolean mostrarConfirmacionTablas(String NombreJugador) {
        String encabezado = NombreJugador + Constantes.ENCABEZADO_TABLAS;
        return (VistaAlerta.mostrarAlertaEvento(Constantes.TITULO_TABLAS, encabezado, Constantes.PREGUNTA_TABLAS));
    }

    public static Boolean mostrarConfirmacionAbandono() {
        return (VistaAlerta.mostrarAlertaEvento(Constantes.TITULO_ABANDONO, Constantes.ENCABEZADO_ABANDONO, Constantes.PREGUNTA_ABANDONO));
    }
}
