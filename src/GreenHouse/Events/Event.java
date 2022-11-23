package GreenHouse.Events;

public abstract class Event implements Comparable<Event> {
  private long duration;
  protected final long delay;
  private int priority;

  public Event(long starttime, long delay) {
    this.duration = starttime;
    this.delay = delay;
  }

  public void start() {
    duration = System.nanoTime() + delay;
  }

  public boolean ready() {
    return System.nanoTime() >= duration;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public abstract void switchOn();
  public abstract void switchOff();

  public int compareTo(Event event) {
    if (priority == event.priority)
      return 0;
    else if (priority > event.priority)
      return 1;
    else
      return -1;
  }
}
