package GreenHouse.Events;

public class Water extends Event {

  public Water(long starttime, long delayTime) {
    super(starttime, delayTime);
  }

  public void switchOn() {
    System.out.println("Water is On");
  }

  public void switchOff() {
    System.out.println("Water is Off");
  }

}