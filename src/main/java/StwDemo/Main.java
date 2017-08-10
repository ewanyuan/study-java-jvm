package StwDemo;

/**
 * Created by ewan on 06/08/2017.
 * Demo of the phenomenon of "Stop the world"
 */
public class Main {
    public static void main(String args[]) throws InterruptedException {
        Thread t1 = new WorkerThread();
        Thread t2 = new PrintThread();

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
