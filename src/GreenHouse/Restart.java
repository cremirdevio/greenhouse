// package GreenHouse;

// public class Restart extends Event {

//   private Controller gc = new Controller();

//   public Restart(long starttime, long delayTime, Event[] eventList) {
//     super(starttime, delayTime);
//     for (Event e : eventList) {
//       gc.addEvent(e);
//     }
//   }

//   public void action() {
//     System.out.println("System Restarting...");
//     gc.run();
//   }

// }