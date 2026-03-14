package ua.university;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private final List<Order> database = new ArrayList<>();

    public void save(Order order) {
        database.add(order);
    }

    public Optional<Order> findById(long id) {
        for (Order order : database) {
            if (order.getOrderId() == id) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }
}
