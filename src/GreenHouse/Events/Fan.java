package GreenHouse.Events;

public class Fan extends Event {

  public Fan(long delay) {
    super(delay);
  }

  public void run() {
    if (this.canGoNextCycle()) {
      System.out.println("🧚 Fan is running");
    }
  }
}
