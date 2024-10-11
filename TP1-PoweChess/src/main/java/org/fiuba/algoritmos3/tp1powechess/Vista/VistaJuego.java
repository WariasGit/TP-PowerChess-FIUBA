package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;


public class VistaJuego {
    private VistaAlerta vistaAlerta = new VistaAlerta();
    public void mostrarConfirmacionCierre(WindowEvent event) {
        Boolean continuar = vistaAlerta.mostrarAlertaEvento(Constantes.TITULO_CIERRE_VENTANA, Constantes.ENCABEZADO_CIERRE_VENTANA, Constantes.PREGUNTA_CIERRE_VENTANA);
        if(!continuar) {
            event.consume();
        }
    }

    public Boolean mostrarConfirmacionTablas(String NombreJugador) {
        String encabezado = NombreJugador + Constantes.ENCABEZADO_TABLAS;
        return (vistaAlerta.mostrarAlertaEvento(Constantes.TITULO_TABLAS, encabezado, Constantes.PREGUNTA_TABLAS));
    }

    public Boolean mostrarConfirmacionAbandono(String nombreJugadorActual) {
        String pregunta = nombreJugadorActual + Constantes.PREGUNTA_ABANDONO;
        return (vistaAlerta.mostrarAlertaEvento(Constantes.TITULO_ABANDONO, Constantes.ENCABEZADO_ABANDONO, pregunta));
    }
}
