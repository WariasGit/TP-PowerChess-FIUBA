package org.fiuba.algoritmos3.tp1powechess.Modelo.General;

import javafx.event.EventHandler;
import org.fiuba.algoritmos3.tp1powechess.Controlador.ControladorTablero;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoPoder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Turno;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.*;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

import javax.sound.sampled.Control;
import java.util.Optional;

/**
 * Esta clase maneja los poderes en el juego de PowerChess.
 * Utiliza expresiones lambda para gestionar eventos de manera concisa y legible.
 *Al añadir manejadores de eventos, se utiliza la siguiente sintaxis:
 * root.addEventHandler(EventoPoder.DOBLE_JUEGO, evento -> gestorPoderes.activarDobleJuego());
 * Necesitaba hacerlo asi para evitar tener muchos if anidados, en el caso de sobreescribir el metodo handle de la interfaz EventHandler.
 */

public class GestorPoderes {
    private Juego juego;
    private Integer posicionFilaPiezaSeleccionada;
    private Integer posicionColumnaPiezaSeleccionada;
    private ControladorTablero controladorTablero;

    public GestorPoderes(Juego juego){
        this.juego = juego;
    }

    public void setPosiciones(Integer fila, Integer columna){
        this.posicionFilaPiezaSeleccionada = fila;
        this.posicionColumnaPiezaSeleccionada = columna;
    }

    public void setControladorTablero(ControladorTablero controladorTablero) {
        this.controladorTablero = controladorTablero;
    }

    public String verificarAplicacionPoder(Poder poder) {

        Turno turno = this.juego.getTurno();
        Jugador jugador = turno.getTurno();

        Optional<Pieza> optionalPieza = juego.getPiezaActual(this.posicionFilaPiezaSeleccionada, this.posicionColumnaPiezaSeleccionada);
        Pieza pieza;

        if (optionalPieza.isPresent()) {
            pieza = optionalPieza.get();
        } else {
            System.out.println("No hay pieza en la posición seleccionada.");
            return null;
        }

        // Verificar si el poder es para piezas propias o del oponente
        boolean esPiezaPropia = pieza.getColor() == jugador.getColor();

        // Verificar si el poder se puede aplicar según el tipo de pieza (propia, oponente, o ambos)
        Configuracion.AplicacionPoder tipoPiezaAplicable = poder.getTipoPiezaAplicable();
        if (tipoPiezaAplicable == Configuracion.AplicacionPoder.PROPIA && !esPiezaPropia) {
            System.out.println("El poder solo se puede aplicar en piezas propias.");
            return null;
        } else if (tipoPiezaAplicable == Configuracion.AplicacionPoder.RIVAL && pieza.getColor() == jugador.getColor()) {
            System.out.println("PIEZA ACTUAL" + pieza.getColor());
            System.out.println("El poder solo se puede aplicar en piezas del oponente.");
            return null;
        }

        // Verificar si el jugador ya usó este poder antes de aplicarlo
        if (!jugador.puedeUsarPoder(poder.getNombre())) {
            System.out.println("El poder ya fue utilizado.");
            return null;
        }

        // Si la pieza no puede aplicar el poder (por ejemplo, si es un rey, o ya tiene poder
        if (!pieza.verificarAplicacionPoder(poder)) {
            System.out.println("No se puede aplicar el poder a esta pieza");
            return null;
        }

        String poderAccionado = pieza.aplicarPoder(poder);

        if (poderAccionado != null) {
            jugador.eliminarPoderUsado(poder.getNombre());
        }
        return poderAccionado;

    }

    public void activarEscudo() {
            Escudo escudo = new Escudo();
            if (verificarAplicacionPoder(escudo) != null) {
                System.out.println("ESCUDO ACCIONADO CORRECTAMENTE");
                controladorTablero.agregarEscudo();
            } else {
                System.out.println("NO se pudo accionar el poder");
            }
    }

    public void activarFreeze() {
        Freeze freeze = new Freeze();
        if (verificarAplicacionPoder(freeze) != null) {
            System.out.println("FREEZE ACCIONADO CORRECTAMENTE");
            controladorTablero.agregarCongelado();
        } else {
            System.out.println("NO se pudo accionar el poder");
        }    }

    public void activarLimpieza() {
        Limpieza limpieza = new Limpieza();
        String poder = verificarAplicacionPoder(limpieza);
        if (poder != null) {
            System.out.println("SE LIMPIO EL PODER DE " + poder);
        } else {
            System.out.println("No se pudo accionar el poder");
        }
    }

    public void activarRobar() {
        Robar robar = new Robar(juego.getTurno());
        String poder = verificarAplicacionPoder(robar);
        if (poder != null) {
            System.out.println("SE ROBO EL PODER DE " + poder);
        } else {
            System.out.println("No se pudo accionar el poder");
        }
    }
}
