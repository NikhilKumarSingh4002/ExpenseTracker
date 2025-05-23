package com.example.expenseTracker.controller;

import com.example.expenseTracker.model.Expense;
import com.example.expenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        expense.setUsername("admin");
        return expenseService.addExpense(expense);
    }

    @GetMapping
    public List<Expense> getExpenses() {
        return expenseService.getExpensesByUser("admin");
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }
}
