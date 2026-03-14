package ua.university;

public class MyOrderProcessor {
    private final OrderValidator validator = new OrderValidator();

    public void process(Order order, Account account, PaymentMethod paymentMethod) {
        checkCategoryMix(order);

        double amountToPay = validator.validateAndCalculateFinalTotal(order, account);

        paymentMethod.pay(account, amountToPay);

        System.out.println("Замовлення №" + order.getOrderId() + " успішно оброблено!");
    }

    private void checkCategoryMix(Order order) {
        boolean hasElectronics = false;
        boolean hasGroceries = false;

        for (OrderItem item : order.getItems()) {
            if (item.getCategory() == ItemCategory.ELECTRONICS) hasElectronics = true;
            if (item.getCategory() == ItemCategory.GROCERIES) hasGroceries = true;
        }

        if (hasElectronics && hasGroceries) {
            throw new CategoryMixException("Заборонено змішувати електроніку та продукти!");
        }
    }
}