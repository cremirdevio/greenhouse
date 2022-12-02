/** GreenHouse.Events packages */
package GreenHouse.Events;

/** Primary class for the Light Event */
public class Light extends Event {

  /** Constructor of the Light event */
  public Light(long delay) {
    super(delay);
  }

  /** The function that shows the activity of an event. */
  public void run() {
    if (this.canGoNextCycle()) {
      System.out.println("🔆 Light is On");
    }
  }
}
