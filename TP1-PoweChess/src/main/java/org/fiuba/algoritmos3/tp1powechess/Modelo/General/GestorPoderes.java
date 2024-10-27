package org.fiuba.algoritmos3.tp1powechess.Modelo.General;

import javafx.event.EventHandler;
import org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos.EventoPoder;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Juego;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Jugador;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Turno;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Escudo;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Freeze;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Poder.Poder;
import org.fiuba.algoritmos3.tp1powechess.Utiles.Configuracion;

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

    public GestorPoderes(Juego juego){
        this.juego = juego;
    }

    public void setPosiciones(Integer fila, Integer columna){
        this.posicionFilaPiezaSeleccionada = fila;
        this.posicionColumnaPiezaSeleccionada = columna;
    }

    private boolean esPiezaPropia(Optional<Pieza> pieza) {
        return pieza.isPresent() && pieza.get().getColor().equals(juego.getColorJugadorActual());
    }

    private boolean esPiezaOponente(Optional<Pieza> pieza) {
        return pieza.isPresent() && !pieza.get().getColor().equals(juego.getColorJugadorActual());
    }

    public void activarDobleJuego() {System.out.println("Activando doble Juego");}

    public void verificarAplicacionPoder(Poder poder) {
        Optional<Pieza> optionalPieza = juego.getPiezaActual(this.posicionFilaPiezaSeleccionada, this.posicionColumnaPiezaSeleccionada);
        Pieza pieza;


        if (optionalPieza.isPresent()) {
            pieza = optionalPieza.get();
        } else {
            System.out.println("No hay pieza en la posición seleccionada.");
            return;
        }

        Turno turno = this.juego.getTurno();
        Jugador jugador = turno.getTurno();
        System.out.println(jugador);

        // Verificar si el poder es para piezas propias o del oponente
        boolean esPiezaPropia = pieza.getColor() == jugador.getColor();

        // Verificar si el poder se puede aplicar según el tipo de pieza (propia, oponente, o ambos)
        Configuracion.AplicacionPoder tipoPiezaAplicable = poder.getTipoPiezaAplicable();
        if (tipoPiezaAplicable == Configuracion.AplicacionPoder.PROPIA && !esPiezaPropia) {
            System.out.println("El poder solo se puede aplicar en piezas propias.");
            return;
        } else if (tipoPiezaAplicable == Configuracion.AplicacionPoder.RIVAL && esPiezaPropia) {
            System.out.println("El poder solo se puede aplicar en piezas del oponente.");
            return;
        }


        // Verificar si el jugador ya usó este poder antes de aplicarlo
        if (!jugador.puedeUsarPoder(poder)) {
            System.out.println("El poder ya fue utilizado.");
            return;
        }

        // Si la pieza no puede aplicar el poder (por ejemplo, si es un rey, o ya tiene poder
        if (!pieza.aplicarPoder(poder)) {
            System.out.println("No se puede aplicar el poder a esta pieza");
            return;
        }

        // Si el poder fue aplicado correctamente, se agrega a la lista de poderes usados
        jugador.agregarPoderUsado(poder);
        System.out.println("Poder aplicado correctamente.");
        jugador.poderUsado(poder.getTipo());

        //ESTO HAY QUE SACARLO, ES PARA PRUEBAS
        System.out.println("Poder aplicado correctamente a la pieza: " + pieza.getClass().getSimpleName());
        System.out.println("Poder aplicado en posición: (" + this.posicionFilaPiezaSeleccionada + ", " + this.posicionColumnaPiezaSeleccionada + ")");
    }

    public void activarEscudo() {

            Escudo escudo = new Escudo(3);
              verificarAplicacionPoder(escudo);
    }

    public void activarFreeze() {
        Freeze freeze = new Freeze(3);
        verificarAplicacionPoder(freeze);
    }

    public void activarLimpieza() {

        System.out.println("Activando Limpieza");
    }
    public void activarRobar() {System.out.println("Activando Robar");}
    public void activarEvolucion() {System.out.println("Activando Evolucion");}

    public void activarVuelo() {System.out.println("Activando Vuelo");}
}
