package ua.university;

import java.util.logging.Logger;

public class BankTransferPayment implements PaymentMethod {
    private static final Logger logger = Logger.getLogger(BankTransferPayment.class.getName());

    @Override
    public void pay(Account account, double sum) {
        double commission = sum * 0.015;
        double totalToPay = sum + commission;

        logger.info("Розрахунок комісії банку: " + commission + " для суми " + sum);

        if (account.getAmount() < totalToPay) {
            logger.warning("Банківський переказ відхилено: баланс замалий для суми з комісією (" + totalToPay + ")");
            throw new IllegalStateException("Недостатньо коштів для оплати з урахуванням комісії 1.5%");
        }

        account.setAmount(account.getAmount() - totalToPay);
        logger.info("Успішний банківський переказ. Списано разом з комісією: " + totalToPay);
    }
}
