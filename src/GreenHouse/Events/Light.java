package GreenHouse.Events;

public class Light extends Event {

  public Light(long delay) {
    super(delay);
  }

  public void run() {
    if (this.canGoNextCycle()) {
      System.out.println("Light is On");
    }
  }
}
