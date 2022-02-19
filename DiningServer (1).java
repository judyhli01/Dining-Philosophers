import java.util.concurrent.locks.Condition;

/**
 * DiningServer.java
 *
 * This class contains the methods called by the  philosophers.
 * You are flexible to change it, here only display a sample
 */

public interface DiningServer 
{
   // collection of the states philosophers can be in
   public enum state { THINKING, HUNGRY, EATING }
   // Array of each philosopher's states
   state[] currentState = new state[5];
   // Array of Condition variable
   Condition[] taken = new Condition[5];

   // returns current state of philosopher
   public DiningServerImpl.state getCurrentState (int philNum);

   // initializes state and Condition arrays
   public void initialize();

   // called by a philosopher when they wish to eat
   public void takeForks(int philNum) throws InterruptedException;

   // called by a philosopher when they are finished eating
   public void returnForks(int philNum);

   public void test (int philNum);
}
