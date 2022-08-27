package firsthomework;

public class task01 {
    public static void main(String[] args) {
        Account hubby = new Account("张三");
        Account wife = new Account("张嫂");

        hubby.setMoney(800);
        wife.setMoney(900);

        hubby.start();
        wife.start();

    }
}

class money{}

class Account extends Thread {
    private static int balance = 1000;
    private int money;

    public Account(String s) {
        this.setName(s);

    }

    public static int getBalance() {
        return balance;
    }

    public int getMoney() {
        return money;
    }

    public static void setBalance(int balance) {
        Account.balance = balance;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (Account.class) {
                if (money > balance) {
                    System.out.println("余额不足" + getName() + "想取出" + money + "元 ， 但是现在只有 " + balance + " 元。");
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance = balance - money;
                System.out.println(getName() + " 取出 " + money + "元，余额 " + balance + " 元!");
            }
        }

    }
}
