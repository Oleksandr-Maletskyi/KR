package ua.university;

import java.util.logging.Logger;

public abstract class OrderProcessorTemplate {
    protected static final Logger logger = Logger.getLogger(OrderProcessorTemplate.class.getName());
    private final OrderValidator validator = new OrderValidator();

    public final void process(Order order, Account account, PaymentMethod paymentMethod) {
        logger.info("Початок обробки замовлення №" + order.getOrderId());
        validateCategoryMix(order);
        double amountToPay = validator.validateAndCalculateFinalTotal(order, account);
        paymentMethod.pay(account, amountToPay);
        logger.info("Замовлення успішно завершено!");
    }

    protected abstract void validateCategoryMix(Order order);
}
