package GreenHouse.Events;

public class Light extends Event {

  public Light(long starttime, long delaytime) {
    super(starttime, delaytime);
  }

  public void switchOn() {
    System.out.println("Light is switched on");
  }

  public void switchOff() {
    System.out.println("Light is switched off");
  }

}

