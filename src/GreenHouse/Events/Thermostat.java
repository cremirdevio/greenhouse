package GreenHouse.Events;

public class Thermostat extends Event {

  public Thermostat(long delay) {
    super(delay);
  }

  public void run() {
    System.out.println("Thermostat is On");
  }
}
