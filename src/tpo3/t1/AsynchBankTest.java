package tpo3.t1;

import java.util.Scanner;

public class AsynchBankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {
        int bankType = getBankType();
        BankInterface b = getBankByType(bankType);
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferThread t = new TransferThread(b, i, INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
        }
    }

    private static int getBankType() {
        Scanner scanner = new Scanner(System.in);
        int bankType;
        while (true) {
            System.out.print("Choose bank type (1 - Bank, 2 - BankSync, 3 - BankSyncBlock, 4 - BankLock): ");
            if (scanner.hasNextInt()) {
                bankType = scanner.nextInt();
                if (bankType >= 1 && bankType <= 4) {
                    break;
                } else {
                    System.out.println("Incorrect choice, it must by >= 1 and <= 4");
                }
            } else {
                System.out.println("Incorrect format");
                scanner.next();
            }
        }
        return bankType;
    }

    private static BankInterface getBankByType(int chosenNum) {
        BankInterface b;
        switch (chosenNum) {
            case 1:
                b = new Bank(NACCOUNTS, INITIAL_BALANCE);
                break;
            case 2:
                b = new BankSync(NACCOUNTS, INITIAL_BALANCE);
                break;
            case 3:
                b = new BankSyncBlock(NACCOUNTS, INITIAL_BALANCE);
                break;
            case 4:
                b = new BankLock(NACCOUNTS, INITIAL_BALANCE);
                break;
            default:
                System.out.println("Incorrect choice, Bank will be used");
                b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        }
        return b;
    }
}