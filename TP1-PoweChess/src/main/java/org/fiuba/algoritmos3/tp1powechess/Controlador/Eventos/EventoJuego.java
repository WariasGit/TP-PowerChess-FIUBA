package org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos;

import javafx.event.Event;
import javafx.event.EventType;

public class EventoJuego extends Event {
    public static EventType<EventoJuego> CAMBIO_DE_TURNO_EVENT = new EventType<>("Cambio de Turno Event");
    public static final EventType<EventoJuego> VOLVER_AL_MENU = new EventType<>("Volver al menu.");
    public static final EventType<EventoJuego> SALIR_JUEGO = new EventType<>("Salir del juego.");
    public static final EventType<EventoJuego> INICIAR_JUEGO = new EventType<>("Iniciar Juego.");
    public static final EventType<EventoJuego> RENDIRSE_EVENT = new EventType<>("Rendirse.");
    public static final EventType<EventoJuego> TABLAS_ACEPTADAS_EVENT = new EventType<>("Tablas Aceptadas.");
    public static final EventType<EventoJuego> CARGAR_PARTIDA_GUARDADA = new EventType<>("Guardar Partida.");

    public EventoJuego(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
