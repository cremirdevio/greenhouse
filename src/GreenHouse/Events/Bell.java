/** GreenHouse.Events packages */
package GreenHouse.Events;

/** Primary class for the Bell Event */
public class Bell extends Event {

  /** Constructor of the Bell event */
  public Bell(long delay) {
    super(delay);
  }

  /** The function that shows the activity of an event. */
  public void run() {
    if (this.canGoNextCycle()) System.out.println("🔔 Bell is Ringing");
  }
}