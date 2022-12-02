/**
 * Ayodeji Randle
 * Student ID: 3584419
 * Comp 501
 * Date: November 28, 2022
 * Program Nmae: GreenHouse.java
 * Description: A program that runs/schedules different events like Alarm, Light, Thermostat, Water, Bell, Fan etc in a Greenhouse. Events start at the
 * specified tiime and run for the set duration.
 */

/**
 * DOCUMENTATION
 */

/**
 * Purpose and Description
 * 
 * PURPOSE:
 * The Class Greenhouse is used to instantiate another class called Controller which is used to schedule and run operations in the greenhouse.
 * 
 * DESCRIPTION:
 * 
 * This program creates an instance of the Controller class, creates a Scheduler using ScheduledExecutorService and runs the Controller inside the scheduler.
 * Using this approach, the controller can monitor all existing events every 1 second. This way it can stop any event that needs to be stopped, simulate failure of an event or restart all events.
 * 
 * VARIABLES AND CLASSES
 * 
 * Controller: contains all control methods for all events.
 * ScheduledExecutorService: creates a scheduler that can be run at a fixed rate.
 * 
 * COMPILING AND RUNNING INSTRUCTION
 * 
 * Assuming SDK 1.3 (or later) and the CLASSPATH are set up properly.
 * Change to the directory containing the source code.
 * cd into the `src` directory
 * Compile:     `javac -cp . GreenHouse/GreenHouse.java` 
 * Run:         `java GreenHouse/GreenHouse` 
 * Document:    `javadoc GreenHouse/GreenHouse` 
 * 
 * <H3>Test Plan</H3>
 * 
 * <p>
 * 1. Run the program.
 * EXPECTED:
 * Console should display:
 * <pre>
 *    *******************************************
 *    *********** Parsing Commands ************** 
 * </pre>
 * 
 * Then each Event will start according to the formatted commands <b>greenhouse_plan.txt<b> file.
 * 
 * ACTUAL:
 * Console display as expected
 * </p>
 * 
 * <p>
 * 2. Good Data.
 * 
 * EXPECTED:
 * Console should display:
 * <pre>
 *    *******************************************
 *    *********** Parsing Commands ************** 
 * </pre>
 * 
 * Then each Event will start according to the formatted commands <b>greenhouse_plan.txt<b> file.
 * 
 * ACTUAL:
 * Console display as expected
 * </p>
 * 
 * 
 * <p>
 * 3. Bad Data.
 * 
 * EXPECTED:
 * Modify the greenhouse_plan.txt to an inconsistent pattern.
 * 
 * Greenhouse terminates with option to restart.
 * 
 * ACTUAL:
 * Greenhouse terminates stating the cause of the error.
 * </p>
 * 
 */

 /** GreenHouse Package */
package GreenHouse;

import GreenHouse.Events.*;

/** Java core packages */
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** primary (public) class for GreenHouse */
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
          } else {}
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
