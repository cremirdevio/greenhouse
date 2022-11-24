package GreenHouse.Events;

public class Thermostat extends Event {

  public Thermostat(long delay) {
    super(delay);
  }

  public void run() {
    System.out.println("Thermostat is On");

    if ( (this.getStartTime() + this.getDuration()) <= System.nanoTime() / 1_000_000) {
      System.out.println("Thermostat will now go off.");
      this.getSheduler().shutdown();
    } else System.out.println("Thermostat is On");
  }
}
