package GreenHouse.Events;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import GreenHouse.EventStatus;
import GreenHouse.EventStopStatus;

public abstract class Event implements Runnable  {
  final private long TIME_ALLOWANCE = 1000; // -+1000 ms allowance
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
    this.startTime = System.nanoTime() / 1_000_000; // in milliseconds
    
    scheduler.scheduleAtFixedRate(this, this.delay, 1000, TimeUnit.MILLISECONDS);
  }

  // Stop should get a reason, FAILED or NORMAL
  public void stop(EventStopStatus reason) {
    if (reason == EventStopStatus.GRACEFUL) this.setStatus(EventStatus.STOPPED);
    else this.setStatus(EventStatus.FAILED);

    System.out.printf("Event completed: [%d]", System.nanoTime() + delay);

    scheduler.shutdownNow();
  }

  public boolean canGoNextCycle() {
    long endTime = this.startTime + this.duration + this.TIME_ALLOWANCE;
    long currentTime = System.nanoTime() / 1_000_000;

    if ((currentTime <= endTime)) {
      return true;
    }

    // Check if its been stopped already
    if (this.status == EventStatus.RUNNING) {
      this.scheduler.shutdownNow();
    }

    return false;
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
