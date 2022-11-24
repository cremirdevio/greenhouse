package GreenHouse;

import GreenHouse.Events.*;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GreenHouse {

  public static void main(String[] args) {
    System.out.println("Green House Initiated");

    Controller greenHouseController = new Controller();

    try {
      greenHouseController.parsePlan("/Users/user/software-projects/java/Greenhouse/greenhouse_plan.txt");
      greenHouseController.run();
    } catch (FileNotFoundException e) {
      System.out.printf("GreenHouse operation plan file not found : [%s]", e.getMessage());
    }
  }
}
