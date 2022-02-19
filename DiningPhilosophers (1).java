/**
 * DiningPhilosophers.java
 *
 * This program starts the dining philosophers problem.
 *
 */


public class DiningPhilosophers
{  
   public static void main(String args[])
   {
      //Array of 5 philosophers
      final Philosopher[] philosophers = new Philosopher[5];
      // Set state of all philosophers to THINKING
      DiningServer diningServer = new DiningServerImpl();
      diningServer.initialize();

      //Run each philosopher at a time
      for (int i = 0; i < philosophers.length; i++) {
         philosophers[i] = new Philosopher(i, diningServer);
         Thread t = new Thread(philosophers[i], "Philosopher " + (i));
         t.start();
      }

   }
}
