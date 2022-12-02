
package GreenHouse;

/** Greenhouse Event packages */
import GreenHouse.Events.Alarm;
import GreenHouse.Events.Bell;
import GreenHouse.Events.Event;
import GreenHouse.Events.Fan;
import GreenHouse.Events.Light;
import GreenHouse.Events.Thermostat;
import GreenHouse.Events.Water;

/** Java core packages */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/** primary (public) class for Controller */
public class Controller {

  /** This integer variable holds the value to the default priority to apply to all Events  */
  private int defaultPriority = 10;
  /** EventStatus (enum) variable holds the current status of the Event */
  private EventStatus controllerStatus = EventStatus.IDLE;
  /** Integer variable to store the start time of an event. */
  private long startTime = 0;

  /** A PriorityQueue to store the list of events in order to their priority value. */
  private Queue<Event> eventList = new PriorityQueue<>(
    new Comparator<Event>() {
      public int compare(Event e1, Event e2) {
        if (e1.getPriority() == e2.getPriority()) return 0; else if (
          e1.getPriority() > e2.getPriority()
        ) return 1; else return -1;
      }
    }
  );

  /** An HashMap to store a mapping of the time(int [milliseconds]) to the event modification actions that should happen at the specified time. */
  private HashMap<Integer, EventType> commands = new HashMap<>();

  /** This method is used to add an event to the Priority Queue */
  public void addEvent(Event c) {
    c.setPriority(defaultPriority);
    eventList.add(c);
  }

  /** This method sets the default priority of the controller */
  public void setDefalutPriority(int priority) {
    this.defaultPriority = priority;
  }

  /** This method Start all events */
  public void run() {
    this.startTime = System.nanoTime() / 1_000_000;

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

  /** A method that checks if thermostart failed */
  public boolean check() {
    // Get current seconds spent
    int timeSpent =
      ((int) ((System.nanoTime() / 1_000_000) - this.startTime) / 1_000) *
      1_000;
    System.out.println("Time Spent is  " + timeSpent / 1_000);

    // Check commands
    EventType eventToFail = this.commands.get(timeSpent);
    if (eventToFail != null) {
      this.updateEvent(eventToFail, EventStatus.FAILED);
    }

    for (Event event : new ArrayList<Event>(eventList)) {
      if (
        event
          .getClass()
          .toString()
          .toLowerCase()
          .contains(EventType.THERMOSTAT.toString().toLowerCase())
      ) {
        return event.getStatus() == EventStatus.FAILED;
      }
    }

    return false;
  }

  /** Update the status of an event when you don't want to stop the event */
  public void updateEvent(EventType eventType, EventStatus status) {
    Event event = this.findEvent(eventType);

    if (event != null) {
      if (
        status == EventStatus.FAILED && event.getStatus() == EventStatus.RUNNING
      ) {
        event.stop(EventStopStatus.FAILED);
      } else if (
        status == EventStatus.STOPPED &&
        event.getStatus() == EventStatus.RUNNING
      ) {
        event.stop(EventStopStatus.GRACEFUL);
      } else {
        event.setStatus(status);
      }
    }
  }

  /** Search for a specific event */
  public Event findEvent(EventType eventType) {
    for (Event event : new ArrayList<Event>(eventList)) {
      if (
        event
          .getClass()
          .toString()
          .toLowerCase()
          .contains(eventType.toString().toLowerCase())
      ) {
        return event;
      }
    }
    return null;
  }

  /** Parse the event plan from the text file */
  public void parsePlan(String filePath)
    throws FileNotFoundException, ArrayIndexOutOfBoundsException {
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

  /** Parse the command gotten from the text file*/
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
        this.setUpEvent(eventType, firstParameter, 1000);
        break;
      case "failed":
        this.commands.put((int) firstParameter, eventType);
        break;
      default:
        break;
    }
  }

  /** Get the type of the event */
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

  /** Set the individual priority of an event */
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

  /** Instantiate an event */
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
      case FAN:
        Fan fan = new Fan(delay);
        eventList.add(fan);
        break;
      case ALARM:
        Alarm alarm = new Alarm(delay);
        eventList.add(alarm);
        break;
      default:
        break;
    }
  }

  /** Instantiate an event (Method overload) */
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
      case FAN:
        Fan fan = new Fan(delay);
        fan.setDuration(duration);
        eventList.add(fan);
      case ALARM:
        Alarm alarm = new Alarm(delay);
        alarm.setDuration(duration);
        eventList.add(alarm);
      default:
        break;
    }
  }

  /** Capitalize: the first letter of a string */
  public String capitalize(String word) {
    String firstLetter = word.substring(0, 1);
    String remainingLetters = word.substring(1, word.length());

    return firstLetter.toUpperCase() + remainingLetters.toLowerCase();
  }
}
