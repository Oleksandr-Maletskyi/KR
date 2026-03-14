package ua.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private MyOrderProcessor processor;
    private Account account;
    private Order order;

    @BeforeEach
    void setUp() {
        processor = new MyOrderProcessor();
        account = new Account(1L, 10000.0);
        order = new Order(101L);
    }

    @Test
    void successfulOrderWithoutDiscount() {
        order.addItem(new OrderItem("Shirt", 500.0, ItemCategory.CLOTHING));
        order.addItem(new OrderItem("Pants", 1000.0, ItemCategory.CLOTHING));

        processor.process(order, account, new CardPayment());

        assertEquals(8500.0, account.getAmount());
    }

    @Test
    void successfulOrderWithDiscount() {
        order.addItem(new OrderItem("Shirt", 500.0, ItemCategory.CLOTHING));
        order.addItem(new OrderItem("Delivery", 300.0, ItemCategory.SERVICES));
        order.addItem(new OrderItem("Gift Wrap", 200.0, ItemCategory.OTHER));

        processor.process(order, account, new PayPalPayment());

        assertEquals(9100.0, account.getAmount());
    }

    @Test
    void bankTransferAppliesCommission() {
        order.addItem(new OrderItem("Consultation", 500.0, ItemCategory.SERVICES));
        order.addItem(new OrderItem("Support", 500.0, ItemCategory.SERVICES));

        processor.process(order, account, new BankTransferPayment());

        assertEquals(8985.0, account.getAmount());
    }

    @Test
    void accountCreationSetsCorrectValues() {
        Account newAccount = new Account(99L, 500.0);
        assertEquals(99L, newAccount.getId());
        assertEquals(500.0, newAccount.getAmount());
    }

    @Test
    void lessThanTwoItemsThrowsException() {
        order.addItem(new OrderItem("Shirt", 500.0, ItemCategory.CLOTHING));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            processor.process(order, account, new CardPayment());
        });
        assertTrue(exception.getMessage().contains("мінімум 2"));
    }

    @Test
    void insufficientFundsThrowsException() {
        order.addItem(new OrderItem("Laptop", 15000.0, ItemCategory.ELECTRONICS));
        order.addItem(new OrderItem("Mouse", 1000.0, ItemCategory.ELECTRONICS));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            processor.process(order, account, new CardPayment());
        });
        assertTrue(exception.getMessage().contains("Недостатньо коштів"));
    }

    @Test
    void categoryMixThrowsException() {
        order.addItem(new OrderItem("Phone", 5000.0, ItemCategory.ELECTRONICS));
        order.addItem(new OrderItem("Apple", 50.0, ItemCategory.GROCERIES));

        assertThrows(CategoryMixException.class, () -> {
            processor.process(order, account, new CardPayment());
        });
    }

    @Test
    void cardLimitExceededThrowsException() {
        account.setAmount(50000.0);
        order.addItem(new OrderItem("MacBook", 26000.0, ItemCategory.ELECTRONICS));
        order.addItem(new OrderItem("Cable", 100.0, ItemCategory.ELECTRONICS));

        assertThrows(IllegalArgumentException.class, () -> {
            processor.process(order, account, new CardPayment());
        });
    }

    @Test
    void paypalMinimumLimitThrowsException() {
        order.addItem(new OrderItem("Box", 50.0, ItemCategory.OTHER));
        order.addItem(new OrderItem("Sticker", 30.0, ItemCategory.OTHER));

        assertThrows(IllegalArgumentException.class, () -> {
            processor.process(order, account, new PayPalPayment());
        });
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -50.5, -999.99})
    void negativeBalanceNotAllowed(double invalidBalance) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Account(2L, invalidBalance);
        });
    }
}