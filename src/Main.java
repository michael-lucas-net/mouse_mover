import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

    private static final int MIN_CNT = 5;

    public static void main(String[] args) throws InterruptedException {
        if (args.length > 1) {
            System.out.println("wrong number of args");
        }
        if (args.length == 1 && !isNumeric(args[0])) {
            System.out.println("command line arg is not an integer");
        }

        int intervall = args.length == 1 ? Integer.parseInt(args[0]) : MIN_CNT;
        System.out.println("First cursor movement in "
                + intervall + " minutes");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        while (true) {
            Thread.sleep(intervall * 60 * 1000);
            waitForCursor();
            moveCursor();

            System.out.println("Moved cursor at: "
                    + formatter.format(new Date()));
        }

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void waitForCursor() throws InterruptedException {
        Point beforeTimer, afterTimer;
        do {
            beforeTimer = MouseInfo.getPointerInfo().getLocation();
            Thread.sleep(100);
            afterTimer = MouseInfo.getPointerInfo().getLocation();
        } while (!beforeTimer.equals(afterTimer));
    }

    private static void moveCursor() {
        try {
            // These coordinates are screen coordinates
            int xCoord = (int) MouseInfo.getPointerInfo().getLocation().getX();
            int yCoord = (int) MouseInfo.getPointerInfo().getLocation().getY();
            ;

            xCoord++;
            yCoord++;

            // Move the cursor
            Robot robot = new Robot();
            robot.mouseMove(xCoord, yCoord);
        } catch (AWTException e) {
            System.err.println("cannot move cursor");
        }
    }
}
