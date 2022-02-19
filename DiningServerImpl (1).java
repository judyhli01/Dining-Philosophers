/**
 * DiningServer.java
 *
 * This class contains the methods called by the  philosophers.
 *
 */

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DiningServerImpl  implements DiningServer
{
    // collection of the states philosophers can be in
    public enum state { THINKING, HUNGRY, EATING }
    // Array of each philosopher's states
    private state[] currentState = new state[5];
    // Array of Condition variable
    private Condition[] taken = new Condition[5];
    Lock lock = new ReentrantLock();


    // returns current state of philosopher
    public state getCurrentState (int philNum) {
        return currentState[philNum];
    }

    // initializes state and Condition arrays
    public void initialize() {
        // All philosophers are thinking
        Arrays.fill(currentState, state.THINKING);
        // initializing array of Conditions
        Arrays.fill(taken, lock.newCondition());
    }

    // called by a philosopher when they wish to eat
    public void takeForks(int philNum) throws InterruptedException {
        lock.lock();
        currentState[philNum] = state.HUNGRY;
        // test whether philosopher can pick up both forks and eat
        test(philNum);
        // if the forks aren't available, philosopher waits
        if (currentState[philNum] != state.EATING) {
            taken[philNum].await();
        }
        lock.unlock();
    }

    // called by a philosopher when they are finished eating (unlocks the lock on the fork)
    public void returnForks(int philNum) {
        currentState[philNum] = state.THINKING;
        // testing neighbors to see if they want to eat
        test((philNum+4)%5);
        test((philNum+1)%5);
        System.out.println("Philosopher " + philNum + " puts down forks");
    }

    // tests to see if neighbors are eating. If neighbors are eating, current philosopher can't eat. If not, philosopher can eat.
    public void test (int philNum) {
        // If neighbors aren't eating, current philosopher will eat
        // checks the left fork to see if its being used
        if (currentState[(philNum+4)%5] != state.EATING) {
            System.out.println("Philosopher " + philNum + " picks up right fork");
        }
        // If right fork isn't being used, then philosopher can eat
        if ((currentState[(philNum+4)%5] != state.EATING) && (currentState[philNum] == state.HUNGRY) && (currentState[(philNum+1)%5] != state.EATING)) {
            currentState[philNum] = state.EATING;
            // signal to other threads that this philosopher is currently eating
            taken[philNum].signal();
            System.out.println("Philosopher " + philNum + " picks up left fork");
        }
    }

}
