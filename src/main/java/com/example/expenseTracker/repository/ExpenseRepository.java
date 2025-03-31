package com.example.expenseTracker.repository;

import com.example.expenseTracker.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByUsername(String username);
}
