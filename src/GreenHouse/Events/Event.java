/** GreenHouse.Events packages */
package GreenHouse.Events;

/** Java core packages */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** GreenHouse core packages */
import GreenHouse.EventStatus;
import GreenHouse.EventStopStatus;

/** Abstract class that all Event types implements */
public abstract class Event implements Runnable  {
  final private long TIME_ALLOWANCE = 1000; // -+1000 ms allowance
  /** This long variable stores the lifetime duration for the event */
  private long duration;
  /** This long variable stores the delay to be observed before event starts. */
  protected final long delay;
  /** This variable stores the priority of the event. */
  private int priority;
  /** The variable keeps track of the start time for an event. Works together with the delay variable. */
  private long startTime;
  /** This EventStatus variable keeps track of the status of the event. */
  private EventStatus status = EventStatus.IDLE;

  /** ScheduledExecutorService variable is used to handle scheduling for the event.  */
  private ScheduledExecutorService scheduler
            = Executors.newScheduledThreadPool(1);

  public Event(long delay) {
    this.delay = delay;
  }

  /** This method starts an event. */
  public void start() {
    this.setStatus(EventStatus.RUNNING);
    this.startTime = System.nanoTime() / 1_000_000; // in milliseconds
    
    scheduler.scheduleAtFixedRate(this, this.delay, 1000, TimeUnit.MILLISECONDS);
  }

  /** Stop should get a reason, FAILED or NORMAL */
  public void stop(EventStopStatus reason) {
    String action = "completed ✅";

    if (reason == EventStopStatus.GRACEFUL) {
      this.setStatus(EventStatus.STOPPED);
    } else{
      action = "failed ❌";
      this.setStatus(EventStatus.FAILED);
    }

    System.out.printf("%s %s: [%d] \n\n", this.getEventName(), action, (System.nanoTime()/1_000_000) + delay);

    scheduler.shutdownNow();
  }

  /** Check if the events duration is up. */
  public boolean canGoNextCycle() {
    long endTime = this.startTime + this.duration + this.TIME_ALLOWANCE;
    long currentTime = System.nanoTime() / 1_000_000;

    if ((currentTime <= endTime)) {
      return true;
    }

    // Check if its been stopped already
    if (this.status == EventStatus.RUNNING) {
      this.stop(EventStopStatus.GRACEFUL);
    }

    return false;
  }

  /** Gets the events name from the class name. */
  public String getEventName() {
    String className = this.getClass().toString();
    String[] details = className.split("[.]");

    return details[details.length - 1];
  }

  /** Returns the start time of an event */
  public long getStartTime() {
    return this.startTime;
  }

  /** Returns the scheduler instance of the event. */
  public ScheduledExecutorService getSheduler() {
    return this.scheduler;
  }

  /** Sets the priority fot the event. */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /** Returns the duration of an event. */
  public long getDuration() {
    return this.duration;
  }

  /** Sets the duration of an event. */
  public void setDuration(long duration) {
    this.duration = duration;
  }

  /** Returns the priority value of an event. */
  public int getPriority() {
    return priority;
  }

  /** Returns the status of an event. */
  public EventStatus getStatus() {
    return this.status;
  }

  /** Sets the event status. */
  public void setStatus(EventStatus _status) {
    this.status = _status;
  }

  /** The comparator function for the priority queue. */
  public int compareTo(Event event) {
    if (priority == event.priority)
      return 0;
    else if (priority > event.priority)
      return 1;
    else
      return -1;
  }
}
