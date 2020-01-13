import java.util.concurrent.Semaphore;

class Shared {
    static int count = 0;
}

class MyThread extends Thread {
    Semaphore sem;
    String threadName;

    public MyThread(Semaphore sem, String threadName) {
        super(threadName);
        this.sem = sem;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        if (this.getName().equals("A")) {
            System.out.println(threadName + " is waiting for a permit");
            try {
                sem.acquire();
                System.out.println(threadName + " gets a permit");
                for (int i=0; i<5; i++){
                    Shared.count++;
                    System.out.println(threadName + " : " + Shared.count );
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " releases a permit");
            sem.release();
        }
        else {
            System.out.println(threadName + " is waiting for a permit");
            try {
                sem.acquire();
                System.out.println(threadName + " gets a permit");
                for (int i=0; i<5; i++){
                    Shared.count++;
                    System.out.println(threadName + " : " + Shared.count );
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " releases a permit");
            sem.release();
        }
    }
}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(1);

        MyThread mt1 = new MyThread(sem, "A");
        MyThread mt2 = new MyThread(sem, "B");

        mt1.start();
        mt2.start();
        mt1.join();
        mt2.join();

        System.out.println("count: " + Shared.count);
    }

}

