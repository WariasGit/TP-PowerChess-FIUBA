package org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos;

import javafx.event.Event;
import javafx.event.EventType;

public class EventoPoder extends Event {
    public static final EventType<EventoPoder> ESCUDO = new EventType<>("Activa el escudo.");
    public static final EventType<EventoPoder> FREEZE = new EventType<>("Activa el freeze.");
    public static final EventType<EventoPoder> LIMPIEZA = new EventType<>("Activa la limpieza.");
    public static final EventType<EventoPoder> ROBAR = new EventType<>("Activa el robar.");

    public EventoPoder(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
