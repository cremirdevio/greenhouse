package GreenHouse;

import GreenHouse.Events.Bell;
import GreenHouse.Events.Event;
import GreenHouse.Events.Light;
import GreenHouse.Events.Thermostat;
import GreenHouse.Events.Water;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Controller {

  private int defaultPriority = 10;
  private EventStatus controllerStatus = EventStatus.IDLE;

  private Queue<Event> eventList = new PriorityQueue<>(
    new Comparator<Event>() {
      public int compare(Event e1, Event e2) {
        if (e1.getPriority() == e2.getPriority()) return 0; else if (
          e1.getPriority() > e2.getPriority()
        ) return 1; else return -1;
      }
    }
  );

  private ArrayList flaggedEvents = new ArrayList<EventType>();

  public void addEvent(Event c) {
    c.setPriority(defaultPriority);
    eventList.add(c);
  }

  public void setDefalutPriority(int priority) {
    this.defaultPriority = priority;
  }

  public void run() {
    if (this.controllerStatus == EventStatus.IDLE) {
      System.out.println("Controller start each event.");
      for (Event event : new ArrayList<Event>(eventList)) {
        try {
          event.start();
        } catch (Exception e) {
          eventList.remove(event);
        }
      }
    }
  }

  public void parsePlan(String filePath) throws FileNotFoundException {
    Scanner s = new Scanner(new BufferedReader(new FileReader(filePath)));

    ArrayList<String> list = new ArrayList<String>();
    while (s.hasNext()) {
      list.add(s.next());
    }

    // Debuggin purpose
    // Listing the commands
    System.out.println(
      "*******************************************\n" +
      "*********** Parsing Commands **************\n"
    );
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
    System.out.println("/***********************************/\n\n");

    // Parse the commands
    for (int i = 0; i < list.size(); i++) {
      String currentCmd = list.get(i);
      String delimiter = "[=]";
      String[] tokens = currentCmd.split(delimiter);

      parseCommand(tokens[0], tokens[1]);
    }

    System.out.println(">>> Operation plan has been successfully extracted.");
  }

  public void parseCommand(String type, String cmd) {
    String delimiter = "[=,]";
    String[] activity = cmd.split(delimiter);
    EventType eventType = getEventType(activity[0]);

    long firstParameter = Long.parseLong(activity[1]);

    int secondParameter;
    if (activity.length > 2) {
      secondParameter =
        activity[2].equals("*")
          ? Integer.MAX_VALUE
          : Integer.parseInt(activity[2]);
    } else {
      secondParameter = 0;
    }

    switch (type) {
      case "priority":
        this.setEventPriority(eventType, (int) firstParameter);
        break;
      case "event":
        this.setUpEvent(eventType, firstParameter, secondParameter);
        break;
      case "test":
        // this.setUpEvent(eventType, firstParameter, 0);
        break;
      case "failed":
        // this event should be flagged as fail event
        // this.setUpEvent(eventType, firstParameter, secondParameter);
        break;
      default:
        break;
    }
  }

  public EventType getEventType(String evt) {
    switch (evt.toLowerCase()) {
      case "*":
        return EventType.ALL;
      case "light":
        return EventType.LIGHT;
      case "thermostat":
        return EventType.THERMOSTAT;
      case "bell":
        return EventType.BELL;
      case "water":
        return EventType.WATER;
      case "fan":
        return EventType.FAN;
      default:
        return EventType.ALL;
    }
  }

  public void setEventPriority(EventType evt, int value) {
    // if its * set the default priority
    // else create the event and set its priority
    switch (evt) {
      case WATER:
        // Water water = new Water(value);
        // eventList.add(water);
        break;
      default:
        this.setDefalutPriority(value);
        break;
    }
  }

  public void setUpEvent(EventType evt, long delay) {
    switch (evt) {
      case LIGHT:
        Light lightEvent = new Light(delay);
        eventList.add(lightEvent);
        break;
      case THERMOSTAT:
        Thermostat thermostat = new Thermostat(delay);
        eventList.add(thermostat);
        break;
      case BELL:
        Bell bell = new Bell(delay);
        eventList.add(bell);
        break;
      case WATER:
        Water water = new Water(delay);
        eventList.add(water);
        break;
      default:
        break;
    }
  }

  public void setUpEvent(EventType evt, long delay, int duration) {
    switch (evt) {
      case LIGHT:
        Event lightEvent = new Light(delay);
        lightEvent.setDuration(duration);
        eventList.add(lightEvent);
        break;
      case THERMOSTAT:
        Event thermostat = new Thermostat(delay);
        thermostat.setDuration(duration);
        eventList.add(thermostat);
        System.out.println("Thermostat added to the event list.");
        break;
      case BELL:
        Event bell = new Bell(delay);
        bell.setDuration(duration);
        eventList.add(bell);
        break;
      case WATER:
        Event water = new Water(delay);
        water.setDuration(duration);
        eventList.add(water);
        break;
      default:
        break;
    }
  }

  public String capitalize(String word) {
    String firstLetter = word.substring(0, 1);
    String remainingLetters = word.substring(1, word.length());

    return firstLetter.toUpperCase() + remainingLetters.toLowerCase();
  }
}
