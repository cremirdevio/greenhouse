package GreenHouse.Events;

public class Thermostat extends Event {

  public Thermostat(long delay) {
    super(delay);
  }

  public void run() {
    if ( this.canGoNextCycle()) {
      System.out.println("Thermostat is Running");
    }
  }
}
