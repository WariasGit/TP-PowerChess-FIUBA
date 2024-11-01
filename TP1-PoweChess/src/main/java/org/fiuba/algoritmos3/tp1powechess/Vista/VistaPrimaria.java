package org.fiuba.algoritmos3.tp1powechess.Vista;

import javafx.stage.WindowEvent;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Constantes;


public class VistaPrimaria {
    public static void mostrarConfirmacionCierre(WindowEvent event) {
        Boolean continuar = VistaAlerta.mostrarAlertaEvento(Constantes.TITULO_CIERRE_VENTANA, Constantes.ENCABEZADO_CIERRE_VENTANA, Constantes.PREGUNTA_CIERRE_VENTANA);
        if(!continuar) {
            event.consume();
        }
    }

    public static boolean mostrarMensajeFinDePartida(Configuracion.EstadoJuego estadoJuego, String nombreGanador) {
        String titulo = "Fin de la partida!";
        String mensaje = definirMensajeFinDePartida(estadoJuego, nombreGanador);
        String pregunta = "Quiere jugar otra partida?";
        return VistaAlerta.mostrarAlertaEvento(titulo, mensaje, pregunta);
    }

    private static String definirMensajeFinDePartida(Configuracion.EstadoJuego estadoJuego, String nombreGanador) {
        String mensaje = "";

        if(estadoJuego == Configuracion.EstadoJuego.JAQUE_MATE){
            mensaje = "¡Jaque mate! Ganador: " + nombreGanador;
        }
        else if(estadoJuego == Configuracion.EstadoJuego.FINALIZADO) {
            mensaje = "Por abandono del rival, el ganador es: " + nombreGanador;
        }
        else if (estadoJuego == Configuracion.EstadoJuego.TABLAS) {
            mensaje = "¡Tablas! Es un empate.";
        }
        return mensaje;
    }

}
