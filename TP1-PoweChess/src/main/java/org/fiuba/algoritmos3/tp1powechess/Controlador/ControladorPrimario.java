package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.scene.layout.Pane;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoJuego;

public class ControladorPrimario {
    public Pane panePrincipal;

    public void entrarAlJuego() {
        panePrincipal.fireEvent(new EventoJuego(EventoJuego.INICIAR_JUEGO));
    }

    public void salirDelJuego(){
        panePrincipal.fireEvent(new EventoJuego(EventoJuego.SALIR_JUEGO));
        System.out.println("Se quiere salir del juego");
    }

    public void opciones(){
        //
    }

    public void cargarPartida(){
        panePrincipal.fireEvent(new EventoJuego(EventoJuego.CARGAR_PARTIDA_GUARDADA));
        System.out.println("Se carga una partida guardada");
    }

}
