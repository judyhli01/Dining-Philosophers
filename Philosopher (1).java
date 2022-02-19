import java.util.concurrent.ThreadLocalRandom;

/**
 * Philosopher.java
 *
 * This class represents each philosopher thread.
 * Philosophers alternate between eating and thinking.
 *
 */


public class Philosopher implements Runnable
{
    private DiningServer diningServer;

    // stores philosophers number
    public Philosopher(int number, DiningServer diningServer) {
        this.diningServer = diningServer;
    }

    // Creates a thread, starting thread will call run()
    @Override
    public synchronized void run() {
        // Gets philosopher name
        String philosopherName = Thread.currentThread().getName();
        // Gets philosopher number
        int number =  Character.getNumericValue(Thread.currentThread().getName().charAt(philosopherName.length()-1));

        try {
            while (true) {

                // thread sleeps 1-3 seconds to simulate thinking
                System.out.println(philosopherName + " is " + diningServer.getCurrentState(number));
                Thread.sleep(((int) (Math.random() * 3000)+1));

                // philosopher picks up fork
                diningServer.takeForks(number);
                System.out.println(philosopherName + " is " + diningServer.getCurrentState(number));

                // philosopher finishes eating and puts down fork
                diningServer.returnForks(number);
                System.out.println(philosopherName + " is " + diningServer.getCurrentState(number));
                Thread.sleep(((int) (Math.random() * 3000)+1));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
