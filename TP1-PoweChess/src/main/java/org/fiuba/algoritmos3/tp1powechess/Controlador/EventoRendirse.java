package org.fiuba.algoritmos3.tp1powechess.Controlador;

import javafx.event.Event;
import javafx.event.EventType;

public class EventoRendirse extends Event{
    public static EventType<EventoRendirse> RENDIRSE_EVENT = new EventType<>("Rendirse Event");

    public EventoRendirse() {
        super(RENDIRSE_EVENT);
    }
}
