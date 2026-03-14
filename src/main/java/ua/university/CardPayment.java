package ua.university;

import java.util.logging.Logger;

public class CardPayment implements PaymentMethod {

    private static final Logger logger = Logger.getLogger(CardPayment.class.getName());

    @Override
    public void pay(Account account, double sum) {
        if (sum > 25000) {
            logger.warning("Спроба оплати карткою на суму " + sum + " - Перевищено ліміт 25 000");
            throw new IllegalArgumentException("CardPayment error: сума перевищує ліміт 25 000");
        }

        if (account.getAmount() < sum) {
            logger.warning("Відмова в оплаті: недостатньо коштів на рахунку ID " + account.getId());
            throw new IllegalStateException("Недостатньо коштів на рахунку");
        }

        account.setAmount(account.getAmount() - sum);
        logger.info("Успішна оплата карткою: " + sum + " грн. Новий баланс: " + account.getAmount());
    }
}
