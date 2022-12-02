/** GreenHouse.Events packages */
package GreenHouse.Events;

/** Primary class for the Thermostat Event */
public class Thermostat extends Event {

  /** Constructor of the Thermostat event */
  public Thermostat(long delay) {
    super(delay);
  }

  /** The function that shows the activity of an event. */
  public void run() {
    if ( this.canGoNextCycle()) {
      System.out.println("🌡 Thermostat is Running");
    }
  }
}
