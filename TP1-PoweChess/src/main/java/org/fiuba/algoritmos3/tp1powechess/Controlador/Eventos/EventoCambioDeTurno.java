package org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos;

import javafx.event.Event;
import javafx.event.EventType;

public class EventoCambioDeTurno extends Event {
    public static EventType<EventoCambioDeTurno> CAMBIO_DE_TURNO_EVENT = new EventType<>("Cambio de Turno Event");

    public EventoCambioDeTurno() {
        super(CAMBIO_DE_TURNO_EVENT);
    }
}
