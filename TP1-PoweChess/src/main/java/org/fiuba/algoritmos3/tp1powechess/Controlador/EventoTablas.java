package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.event.Event;
import javafx.event.EventType;

public class EventoTablas extends Event {
    public static EventType<EventoTablas> TABLAS_ACEPTADAS_EVENT = new EventType<>("Tablas Aceptadas Event");

    public EventoTablas() {
        super(TABLAS_ACEPTADAS_EVENT);
    }
}
