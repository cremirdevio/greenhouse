/** GreenHouse.Events packages */
package GreenHouse.Events;

/** Primary class for the Water Event */
public class Water extends Event {

  /** Constructor of the Water event */
  public Water(long delay) {
    super(delay);
  }

  /** The function that shows the activity of an event. */
  public void run() {
    if (this.canGoNextCycle()) {
      System.out.println("💦🚰 Water is Running");
    }
  }

}