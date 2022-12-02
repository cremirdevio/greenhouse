/** GreenHouse.Events packages */
package GreenHouse.Events;

/** Primary class for the Fan Event */
public class Fan extends Event {

  /** Constructor of the Fan event */
  public Fan(long delay) {
    super(delay);
  }

  /** The function that shows the activity of an event. */
  public void run() {
    if (this.canGoNextCycle()) {
      System.out.println("🧚 Fan is running");
    }
  }
}
