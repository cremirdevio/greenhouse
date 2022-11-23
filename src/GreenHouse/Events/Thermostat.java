package GreenHouse.Events;

public class Thermostat extends Event {

  public Thermostat(long starttime, long delayTime) {
    super(starttime, delayTime);
  }

  public void switchOn() {
    System.out.println("Thermostat is On");
  }

  public void switchOff() {
    System.out.println("Thermostat is Off");
  }

}
