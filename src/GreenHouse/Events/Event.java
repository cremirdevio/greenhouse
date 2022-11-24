package GreenHouse.Events;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import GreenHouse.EventStatus;

public abstract class Event implements Runnable  {
  private long duration;
  protected final long delay;
  private int priority;
  private long startTime;
  private EventStatus status = EventStatus.IDLE;

  private ScheduledExecutorService scheduler
            = Executors.newScheduledThreadPool(1);

  public Event(long delay) {
    this.delay = delay;
  }

  public void start() {
    this.setStatus(EventStatus.RUNNING);
    this.startTime = System.nanoTime() / 1000000;
    
    scheduler.scheduleAtFixedRate(this, this.delay, this.duration, TimeUnit.MILLISECONDS);
  }

  public void stop() {
    this.setStatus(EventStatus.STOPPED);

    System.out.printf("Event completed: [%d]", System.nanoTime() + delay);

    scheduler.shutdownNow();
  }

  public long getStartTime() {
    return this.startTime;
  }

  public ScheduledExecutorService getSheduler() {
    return this.scheduler;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public long getDuration() {
    return this.duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public int getPriority() {
    return priority;
  }

  public EventStatus getStatus() {
    return this.status;
  }

  public void setStatus(EventStatus _status) {
    this.status = _status;
  }

  public int compareTo(Event event) {
    if (priority == event.priority)
      return 0;
    else if (priority > event.priority)
      return 1;
    else
      return -1;
  }
}
