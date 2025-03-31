package com.example.expenseTracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Expense {
    @Id
    private String id;
    private String username;
    private String text;
    private double amount;

    public Expense() {}

    public Expense(String username, String text, double amount) {
        this.username = username;
        this.text = text;
        this.amount = amount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
