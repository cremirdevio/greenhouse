package GreenHouse.Events;

public class Light extends Event {

  public Light(long delay) {
    super(delay);
  }

  public void run() {
    System.out.printf(
      "Checking if %l + %l >= %l",
      this.getStartTime(),
      this.getDuration(),
      System.nanoTime()
    );
    if (this.getStartTime() + this.getDuration() >= System.nanoTime()) {
      System.out.println("Light will now go off.");
      this.getSheduler().shutdown();
    } else System.out.println("Light is On Now");
  }
}
