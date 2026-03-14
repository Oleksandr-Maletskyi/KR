package ua.university;

import java.util.logging.Logger;

public class OrderValidator {
    private static final Logger logger = Logger.getLogger(OrderValidator.class.getName());

    public double validateAndCalculateFinalTotal(Order order, Account account) {

        if (order.getItemsCount() < 2) {
            logger.warning("Помилка: у замовленні " + order.getItemsCount() + " товарів (мінімум 2)");
            throw new IllegalArgumentException("У замовленні має бути мінімум 2 товари!");
        }

        double finalTotal = order.calculateTotalSum();
        logger.info("Базова сума замовлення: " + finalTotal);

        if (order.countUniqueCategories() >= 3) {
            double discount = finalTotal * 0.10;
            finalTotal = finalTotal - discount;
            logger.info("Застосовано знижку 10% за 3+ категорії! Розмір знижки: " + discount);
        }

        if (account.getAmount() < finalTotal) {
            logger.warning("Помилка: на рахунку " + account.getAmount() + ", а треба " + finalTotal);
            throw new IllegalStateException("Недостатньо коштів на рахунку!");
        }

        logger.info("Валідацію пройдено. До сплати: " + finalTotal);
        return finalTotal;
    }
}
