package org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos;

import javafx.event.Event;
import javafx.event.EventType;

public class EventoEstadoPartida extends Event {
    public static final EventType<EventoEstadoPartida> INICIAR_JUEGO = new EventType<>("Iniciar Juego.");
    public static final EventType<EventoEstadoPartida> SALIR_JUEGO = new EventType<>("Salir del juego.");
    public static final EventType<EventoEstadoPartida> RENDIRSE_EVENT = new EventType<>("Rendirse.");
    public static final EventType<EventoEstadoPartida> TABLAS_ACEPTADAS_EVENT = new EventType<>("Tablas Aceptadas.");

    public EventoEstadoPartida(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
