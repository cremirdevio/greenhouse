package GreenHouse;

import java.util.*;
import GreenHouse.Events.Event;
public class Controller {

  private int defaultPriority = 10;
  
  private Queue<Event> eventList = new PriorityQueue<>();

  public void addEvent(Event c) {
    c.setPriority(defaultPriority);
    eventList.add(c);
  }

  public void setDefalutPriority(int priority) {
    this.defaultPriority = priority;
  }

  public void run() {
    while (eventList.size() > 0) {
      // Here we can implement fails mechanism as well.. Incase action action return
      // some error we can call restart event..
      // Fails mechanism can be on different types but based on whats given in problem
      // statement we can have that..
      for (Event e : new ArrayList<Event>(eventList)) {
        if (e.ready()) {
          // Perform a try catch here
          e.start();
          // In the catch part, stop the event and remove it from the list or flag it
          eventList.remove(e);
        }
      }
    }
  }


}

