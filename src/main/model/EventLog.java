package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Code influenced by CPSC210/AlarmSystem

// Represents the log of Scoreboard events. We use the singleton pattern
// to ensure that we only use one instance of this class.
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    // Constructor is private as to follow the Singleton design pattern
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // Gets the instance of the EventLog, creates a new instance if one
    // does not already exist
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

   // EFFECTS: Adds the given event to the EventLog
    public void logEvent(Event e) {
        events.add(e);
    }

    // EFFECTS: Clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}

