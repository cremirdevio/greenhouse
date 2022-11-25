package GreenHouse.Events;

public class Bell extends Event {

  public Bell(long delay) {
    super(delay);
  }

  public void run() {
    if (this.canGoNextCycle()) System.out.println("Bell is Ringing");
  }
}