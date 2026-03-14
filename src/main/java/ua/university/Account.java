package ua.university;

import java.util.Objects;

public class Account {
    private Long id;
    private double amount;

    public Account(Long id) {
        this(id, 0.0);
    }

    public Account(Long id, double amount) {
        super();
        this.id = id;
        setAmount(amount);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getAmount() { return amount; }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сума не може бути від'ємною!");
        }
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", amount=" + amount + "}";
    }
}
