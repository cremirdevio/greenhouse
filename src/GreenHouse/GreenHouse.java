package GreenHouse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GreenHouse {

  public static void main(String[] args) {
    // Controller greenHouseController = new Controller();

    // Instead of hard-coding, we could parse
    // configuration information from text file here.
    // Logic would be parse the file and create/Set_priority_of Event instance based
    // on command (here we can check the class name but I would recommend using
    // ENUMS. That would be correct programming paradigms ) and then return the
    // event list.
    System.out.println("Running");
    
  }

  public void parsePlan(String filePath) throws FileNotFoundException {
    Scanner s = new Scanner(new BufferedReader(new FileReader(filePath)));

    ArrayList<String> list = new ArrayList<String>();
    while (s.hasNext()) {
      list.add(s.next());
    }

    // Debuggin purpose
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }

    // Parse the commands
    for (int i = 0; i < list.size(); i++) {
      String currentCmd = list.get(i);
      String delimiter = "[=]";
      String[] tokens = currentCmd.split(delimiter);

      // I want to see the content of tokens
      System.out.println(tokens.toString());

      parseCommand(tokens[0], tokens[1]);
    }
  }

  public void parseCommand(String type, String cmd) {
    String delimiter = "[=,]";
    String[] activity = cmd.split(delimiter);

    switch (type) {
      case "priority":
        break;
      case "event":
        break;
      case "test":
        break;
      case "failed":
        break;
      default:
        break;
    }
  }
}
