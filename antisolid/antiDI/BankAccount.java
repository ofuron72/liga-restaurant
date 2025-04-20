package antiDI;

/**
 * Демонстрация нарушения DIP
 */
public class BankAccount {
    private String firstname;
    private String lastname;
    private double balance;
    private String paymentType;

    public void sendMessage(String message) {
        Email sender = new Email();
        sender.sendEmail();
    }


}