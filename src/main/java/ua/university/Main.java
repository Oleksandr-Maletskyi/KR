package ua.university;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        Account myAccount = new Account(123L, 10000.0);
        System.out.println("Початковий стан: " + myAccount);

        Order order = new Order(1L);
        order.addItem(new OrderItem("Laptop", 5000.0, ItemCategory.ELECTRONICS));
        order.addItem(new OrderItem("Mouse", 500.0, ItemCategory.ELECTRONICS));
        order.addItem(new OrderItem("Delivery", 100.0, ItemCategory.SERVICES));

        OrderRepository repository = new OrderRepository();
        repository.save(order);

        System.out.println("--- Пошук замовлення в БД ---");
        Optional<Order> foundOrder = repository.findById(1L);

        foundOrder.ifPresent(o -> {
            try {
                MyOrderProcessor processor = new MyOrderProcessor();

                PaymentMethod payment = new PayPalPayment();

                System.out.println("--- Початок обробки ---");
                processor.process(o, myAccount, payment);

                System.out.println("--- Результат ---");
                System.out.println("Залишок на рахунку: " + myAccount.getAmount() + " грн.");

            } catch (Exception e) {
                System.err.println("Помилка при обробці замовлення: " + e.getMessage());
            }
        });

        Optional<Order> missingOrder = repository.findById(2L);
        if (missingOrder.isEmpty()) {
            System.out.println("\nЗамовлення №1 не знайдено в системі");
        }
    }
}
