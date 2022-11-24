package GreenHouse.Events;

public class Alarm extends Event {

  public Alarm(long delay) {
    super(delay);
  }

  public void run() {
    System.out.println("Alarm is running");
  }
}
