package GreenHouse.Events;

public class Water extends Event {

  public Water(long delay) {
    super(delay);
  }

  public void run() {
    System.out.println("Water is Running");
  }

}