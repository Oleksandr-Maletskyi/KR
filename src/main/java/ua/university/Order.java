package ua.university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {
    private final long orderId;
    private final List<OrderItem> items;

    public Order(long orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public long getOrderId() {
        return orderId;
    }

    public int getItemsCount() {
        return items.size();
    }

    public double calculateTotalSum() {
        double sum = 0;
        for (OrderItem item : items) {
            sum += item.getPrice();
        }
        return sum;
    }

    public int countUniqueCategories() {
        Set<ItemCategory> uniqueCats = new HashSet<>();
        for (OrderItem item : items) {
            uniqueCats.add(item.getCategory());
        }
        return uniqueCats.size();
    }
}
