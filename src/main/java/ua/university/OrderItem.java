package ua.university;

import java.time.LocalDate;
import java.time.Period;

public abstract class OrderItem {
    protected String name;
    protected double price;
    protected ItemCategory category;

    public OrderItem(String name, double price, ItemCategory category){
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName(){
        return name;
    }

    public String getPrice(){
        return name;
    }

    public ItemCategory getCategory(){
        return category;
    }
}
