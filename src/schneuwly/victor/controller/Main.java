package schneuwly.victor.controller;

import schneuwly.victor.model.SleepCalculator;
import schneuwly.victor.model.Time;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    //TODO: Write txt
    private static final String welcome = "\n       ---- WELCOME TO SLEEPYTIME ----\n\n";

    private static final String infosStr =
            "Type 'w' if you want to know when to wake up.\n" +
                    "Type 'b' if you want to know when to go to bed.\n" +
                    "Type 'now' if you are going to bed now.\n" +
                    "(Type 'exit' to quit at any moment)";

    public static void main(String[] args) {
        System.out.println(welcome + infosStr);

        try {
            String commandType = askCommand();

            Time chosenTime;

            if (commandType.equals("now")) {
                chosenTime = Time.now();

            } else {
                System.out.printf("Type the %s (hh:mm) to know when to %s.%n",
                        (commandType.equals("w"))
                                ? "bed time"
                                : "wake up time",
                        (commandType.equals("w"))
                                ? "wake up"
                                : "go to bed"
                );

                chosenTime = pickTime();
            }


            System.out.println();
            printTimeInfo(commandType);
            switch (commandType) {
                case "now", "w" -> {
                    printList(SleepCalculator.wakeUpTimes(chosenTime));
                }
                case "b" -> {
                    printList(SleepCalculator.bedTimes(chosenTime));
                }
            }
        } catch (Exit e) {
            e.print();
        } finally {
            SCANNER.close();
        }
    }

    private static String askCommand() throws Exit {
        String commandType;
        do {
            commandType = SCANNER.next();
            if(commandType.equals("exit"))
                throw new Exit();

            if (!(commandType.equals("w") || commandType.equals("b") || commandType.equals("now"))) {
                System.out.println("This is not a valid entry. Retry.");
                System.out.println(infosStr);
            }

        } while (!(commandType.equals("w") || commandType.equals("b") || commandType.equals("now")));

        return commandType;
    }

    private static Time pickTime() throws Exit {
        boolean error = false;
        String request = "";
        Time chosenTime = new Time();

        do {
            request = SCANNER.next();
            if(request.equals("exit"))
                throw new Exit();

            try {
                chosenTime = timeFromStr(request);

                error = false;
            } catch (Exception e) {
                error = true;
                System.out.printf("%s is not a valid time. (The time should be in format hh:mm)%n", request);
            }
        } while (error);

        return chosenTime;
    }

    private static Time timeFromStr(String str) throws NumberFormatException {
        String[] values = str.split(":");
        return new Time(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    private static <E> void printList(List<E> l) {
        for (E e : l) {
            System.out.println(e);
        }
    }

    private static void printTimeInfo(String type) {
        System.out.printf("Since it takes an average of %d minutes to fall asleep, you should %s at one of the following time.%n",
                SleepCalculator.ASLEEP_TIME,
                (type.equals("b"))
                        ? "go to bed"
                        : "wake up"
        );
    }


    private static class Exit extends Exception{
        public void print() {
            System.out.println("Goodbye !");
        }
    }
}
