package antiOpenClose;

/**
 * Нарушение принципа open-closed
 */
public class BankAccount {
    private String firstname;
    private String lastname;
    private double balance;
    private String paymentType;

    private void pay(double amount, String paymentType) {
        if (paymentType.equals("CASH")) {
            balance = balance + amount;
        }
        if (paymentType.equals("DEBIT")) {
            balance = balance + amount * 0.99;
        }
        if (paymentType.equals("CREDIT")) {
            balance = balance + amount * 0.95;
        }
        if (paymentType.equals("CRYPTO")) {
            balance = balance + amount * 0.9;
        }
    }
}
