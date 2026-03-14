package ua.university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {
    private final long orderId;
    private final List<OrderItem> items;

    public Order(long orderId, List<OrderItem> items) {
        this.orderId = orderId;
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = new ArrayList<>(items);
        }
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    public int countUniqueCategories() {
        Set<ItemCategory> uniqueCategories = new HashSet<>();
        for (OrderItem item : items) {
            uniqueCategories.add(item.getCategory());
        }
        return uniqueCategories.size();
    }

    public long getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
