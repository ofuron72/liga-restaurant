package antiLiskov;

/**
 * Демонстрация нарушения принципа LSP
 */
public class CryptoAccount extends BankAccount {
    @Override
    public void pay(double amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
