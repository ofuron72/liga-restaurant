package antiInterface;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Нарушение принципа interface segregation
 *
 *Interface Operations, содержит методы, которые следует разделить по разным интерфейсам
 */
public class BankAccount implements Operations {
    private String firstname;
    private String lastname;
    private double balance;
    private String paymentType;

    @Override
    public void print() {
        System.out.println("Firstname: " + firstname
                + "\nLastname: " + lastname
                + "\nBalance: " + balance);
    }

    @Override
    public void pay(double amount) {
        balance = balance + amount;


    }

    @Override
    public void saveInfo() {
        try (FileWriter writer = new FileWriter("accounts.txt")) {
            writer.write(String.format("firstname: %s, lastname: %s, balance: %f", firstname, lastname, balance));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
