package GreenHouse.Events;

public class Bell extends Event {

  public Bell(long delay) {
    super(delay);
  }

  public void run() {
    System.out.println("Bell is Ringing");
  }
}