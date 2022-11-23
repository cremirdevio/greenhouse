package GreenHouse.Events;

public class Alarm extends Event {
  public Alarm(long starttime, long delayTime) {
    super(starttime, delayTime);
  }

  public void switchOn() {
    System.out.println("Alarm is On");
  }

  public void switchOff() {
    System.out.println("Alarm is Off");
  }

}