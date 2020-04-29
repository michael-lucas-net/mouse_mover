import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

    private static final int MIN_CNT = 4;
    private static final int MIN_OFFSET = 1;
    private static final int TEST_OFFSET = 100;
    private static final String TEST_FLAG_1 = "--test";
    private static final String TEST_FLAG_2 = "-t";

    private static final String INTRO_TXT = "Mouse mover v1.0\n" +
            "repo:https://github.com/derMacon/mouse_mover\n" +
            "Ctrl + c to close program\n";
    private static final String USAGE = "java -jar ./mouse_mover.jar [ ARG ]\n" +
            "  ARG: [ INT ]: number of minutes between cursor movement\n" +
            "       --test : run test iteration\n" +
            "       -t     : run test iteration\n";


    /**
     * Moves cursor every n minutes. Useful to prevent pc from shutdown
     * or when active work time is being tracked.
     *
     * @param args command line args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(INTRO_TXT);
        int intervall = MIN_CNT;

        if (args.length == 1 && isNumeric(args[0])) {
            intervall = Integer.parseInt(args[0]);
            normalRun(intervall);
        } else if (args.length == 1
                && (args[0].equals(TEST_FLAG_1)
                || args[0].equals(TEST_FLAG_2))) {
            testRun();
        } else if (args.length == 0) {
            normalRun(intervall);
        } else {
            System.out.println(USAGE);
        }
    }

    private static void normalRun(int intervall) throws InterruptedException {
        System.out.println("First cursor movement in "
                + intervall + " minutes");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        while (true) {
            Thread.sleep(intervall * 1000);
            moveCursor(MIN_OFFSET);

            System.out.println("Moved cursor at: "
                    + formatter.format(new Date()));
        }

    }


    private static void testRun() throws InterruptedException {
        System.out.println("Starting test run: large offset");
        moveCursor(TEST_OFFSET);

        Thread.sleep(1000); // wait one sec

        System.out.print("Starting test run: small offset (3x)");
        moveCursor(MIN_OFFSET);
        System.out.print(" [1");
        Thread.sleep(1000); // wait one sec
        moveCursor(MIN_OFFSET);
        System.out.print(",2");
        Thread.sleep(1000); // wait one sec
        System.out.print(",3]\n\n");
        moveCursor(MIN_OFFSET);

        System.out.println("normal run will use the small offset");
    }


    private static boolean isNumeric(String strNum) {
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


    private static void moveCursor(int offset) {
        waitForCursor();
        try {
            // These coordinates are screen coordinates
            int xCoord = (int) MouseInfo.getPointerInfo().getLocation().getX();
            int yCoord = (int) MouseInfo.getPointerInfo().getLocation().getY();
            ;

            xCoord += offset;
            yCoord += offset;

            // Move the cursor
            Robot robot = new Robot();
            robot.mouseMove(xCoord, yCoord);
        } catch (AWTException e) {
            System.err.println("cannot move cursor");
            e.printStackTrace();
        }
    }


    private static void waitForCursor() {
        try {
            Point beforeTimer, afterTimer;
            do {
                beforeTimer = MouseInfo.getPointerInfo().getLocation();
                Thread.sleep(100);
                afterTimer = MouseInfo.getPointerInfo().getLocation();
            } while (!beforeTimer.equals(afterTimer));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
