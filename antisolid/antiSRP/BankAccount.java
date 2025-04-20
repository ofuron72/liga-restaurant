package antiSRP;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Нарушение SRP
 * В одном классе сочетаются и методы сохранения в файл, и логика пополнения баланса
 */
public class BankAccount {
    private String firstname;
    private String lastname;
    private double balance;

    public void saveAccount() {
        try (FileWriter writer = new FileWriter("accounts.txt")) {
            writer.write(String.format("firstname: %s, lastname: %s, balance: %f", firstname, lastname, balance));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void topUpBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        if (amount > 100) {
            balance += amount * 0.95;
        }
        if (amount > 1000) {
            balance += amount * 0.9;
        }

    }

    public void displayAccount() {
        System.out.printf("firstname: %s, lastname: %s, balance: %f", firstname, lastname, balance);
    }

}
