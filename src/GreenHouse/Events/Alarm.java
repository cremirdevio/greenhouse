/** GreenHouse.Events packages */
package GreenHouse.Events;

/** Primary class for the Alarm Event */
public class Alarm extends Event {

  /** Constructor of the Alarm event */
  public Alarm(long delay) {
    super(delay);
  }

  /** The function that shows the activity of an event. */
  public void run() {
    if (this.canGoNextCycle()) {
      System.out.println("🚨 Alarm is running");
    }
  }
}
