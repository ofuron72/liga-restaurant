package antiLiskov;

public class BankAccount {
    private String firstname;
    private String lastname;
    private double balance;
    private String paymentType;

    public void pay(double amount) {
        this.balance += amount;
    }


}
