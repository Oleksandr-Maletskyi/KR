package ua.university;

import java.util.logging.Logger;

public class PayPalPayment implements PaymentMethod {
    private static final Logger logger = Logger.getLogger(PayPalPayment.class.getName());

    @Override
    public void pay(Account account, double sum) {
        if (sum < 200) {
            logger.warning("Спроба оплати PayPal на суму " + sum + " - Сума менша за мінімальні 200");
            throw new IllegalArgumentException("PayPal error: мінімальна сума оплати 200");
        }

        if (account.getAmount() < sum) {
            logger.warning("PayPal: недостатньо коштів на рахунку ID " + account.getId());
            throw new IllegalStateException("Недостатньо коштів на рахунку");
        }

        account.setAmount(account.getAmount() - sum);
        logger.info("Успішна оплата PayPal: " + sum + " грн.");
    }
}