package GreenHouse;

import GreenHouse.Events.*;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GreenHouse {

  public static void main(String[] args) {
    Controller greenHouseController = new Controller();
    ScheduledExecutorService controllerScheduler = Executors.newScheduledThreadPool(
      1
    );

    Runnable runner = new Runnable() {
      @Override
      public void run() {
        System.out.println("\nInspecting the Greenhouse...");
        boolean thermostatFailed = greenHouseController.check();
        if (thermostatFailed) {
          // Stop Fan if On
          greenHouseController.updateEvent(EventType.FAN, EventStatus.STOPPED);
          Event alarm = greenHouseController.findEvent(EventType.ALARM);
          if (alarm == null) {
            greenHouseController.setUpEvent(EventType.ALARM, 0);
            alarm = greenHouseController.findEvent(EventType.ALARM);

            if (alarm != null) {
              alarm.setDuration(5000);
              alarm.start();
            }
          } else {
            
          }
        } else {
          // Check if fan should be on
          // If yes, put it on
        }
      }
    };

    try {
      greenHouseController.parsePlan(
        "/Users/user/software-projects/java/Greenhouse/greenhouse_plan.txt"
      );
      greenHouseController.run();
    } catch (FileNotFoundException e) {
      System.out.printf(
        "GreenHouse operation plan file not found : [%s] \n",
        e.getMessage()
      );
      System.exit(0);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.printf(
        "GreenHouse operation plan file format invalid: [%s] \n",
        e.getMessage()
      );
      System.exit(0);
    }

    controllerScheduler.scheduleAtFixedRate(
      runner,
      0,
      1000,
      TimeUnit.MILLISECONDS
    );
  }
}
