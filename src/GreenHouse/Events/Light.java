package GreenHouse.Events;

public class Light extends Event {

  public Light(long delay) {
    super(delay);
  }

  public void run() {
    System.out.println("Light is On Now");
    // System.out.println("Additon of time: " + (this.getStartTime() + this.getDuration()) );
    // System.out.println("Current Time of time: " +  System.nanoTime() / 1_000_000 );


    if ( (this.getStartTime() + this.getDuration()) <= System.nanoTime() / 1_000_000) {
      System.out.println("Light will now go off.");
      this.getSheduler().shutdown();
    } else System.out.println("Light is On Now");
  }
}
