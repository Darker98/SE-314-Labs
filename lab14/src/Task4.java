import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

class BankAccount {
    private AtomicInteger balance = new AtomicInteger(1000);

    public void deposit(int amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(int amount) {
        balance.addAndGet(-amount);
    }

    public int getBalance() {
        return balance.get();
    }
}

class Client extends Thread {
    private BankAccount account;
    private Random rand = new Random();

    Client(BankAccount account) {
        this.account = account;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            int amount = rand.nextInt(200);
            if (rand.nextBoolean()) {
                account.deposit(amount);
                System.out.println("Deposited: " + amount);
            } else {
                account.withdraw(amount);
                System.out.println("Withdrawn: " + amount);
            }
        }
    }
}

public class Task4 {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();

        Thread c1 = new Client(account);
        Thread c2 = new Client(account);
        Thread c3 = new Client(account);

        c1.start();
        c2.start();
        c3.start();

        c1.join();
        c2.join();
        c3.join();

        System.out.println("Final Account Balance: " + account.getBalance());
    }
}
