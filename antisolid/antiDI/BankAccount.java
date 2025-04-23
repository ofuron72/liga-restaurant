package antiDI;

/**
 * Демонстрация нарушения DIP
 * BankAccount зависит от конкретной реализации Email,
 * верхний уровень зависит от нижнего
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