class NumberThread extends Thread {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Number: " + i);
        }
    }
}

class SquareThread implements Runnable {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Square: " + (i * i));
        }
    }
}

public class Task1 {
    public static void main(String[] args) {
        NumberThread t1 = new NumberThread();
        Thread t2 = new Thread(new SquareThread());

        t1.start();
        t2.start();
    }
}
