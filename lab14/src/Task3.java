import java.util.concurrent.CopyOnWriteArrayList;

class ListWorker extends Thread {
    private CopyOnWriteArrayList<Integer> list;

    ListWorker(CopyOnWriteArrayList<Integer> list) {
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            list.add(i);
            System.out.println(Thread.currentThread().getName() + " added " + i);
        }
    }
}

public class Task3 {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        Thread t1 = new ListWorker(sharedList);
        Thread t2 = new ListWorker(sharedList);
        Thread t3 = new ListWorker(sharedList);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final List: " + sharedList);
    }
}
