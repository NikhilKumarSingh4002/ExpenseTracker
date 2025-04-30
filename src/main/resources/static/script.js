const balance = document.getElementById("balance");
const moneyPlus = document.getElementById("money-plus");
const moneyMinus = document.getElementById("money-minus");
const list = document.getElementById("list");
const form = document.getElementById("form");
const text = document.getElementById("text");
const amount = document.getElementById("amount");
const notification = document.getElementById("notification");

let transactions = [];

// Set your Render service's public URL
const apiBaseUrl = "https://expensetracker-o237.onrender.com/api/expenses";  // Replace with your actual Render URL

// Show alert for empty input
function showNotification() {
  notification.classList.add("show");
  setTimeout(() => {
    notification.classList.remove("show");
  }, 2000);
}

// Generate a temporary frontend ID (optional)
function generateID() {
  return Math.floor(Math.random() * 100000000);
}

// Add transaction via form
function addTransaction(e) {
  e.preventDefault();

  if (text.value.trim() === "" || amount.value.trim() === "") {
    showNotification();
  } else {
    const transaction = {
      text: text.value,
      amount: +amount.value,
    };

    // Send to backend (Render URL)
    fetch(apiBaseUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(transaction),
    })
      .then((response) => response.json())
      .then((data) => {
        transactions.push(data); // Add response with real ID
        addTransactionDOM(data);
        updateValues();
      })
      .catch((error) => console.error("Error:", error));

    text.value = "";
    amount.value = "";
  }
}

// Display one transaction
function addTransactionDOM(transaction) {
  const sign = transaction.amount < 0 ? "-" : "+";
  const item = document.createElement("li");
  item.classList.add(sign === "+" ? "plus" : "minus");

  item.innerHTML = `
    ${transaction.text} 
    <span>${sign}${Math.abs(transaction.amount)}</span>
    <button class="delete-btn" onclick="removeTransaction('${transaction.id}')">
      <i class="fa fa-times"></i>
    </button>
  `;

  list.appendChild(item);
}

// Update balance, income, and expense
function updateValues() {
  const amounts = transactions.map((transaction) => transaction.amount);
  const total = amounts.reduce((acc, val) => acc + val, 0).toFixed(2);
  const income = amounts
    .filter((val) => val > 0)
    .reduce((acc, val) => acc + val, 0)
    .toFixed(2);
  const expense = (
    amounts
      .filter((val) => val < 0)
      .reduce((acc, val) => acc + val, 0) * -1
  ).toFixed(2);

  balance.innerText = `$${total}`;
  moneyPlus.innerText = `$${income}`;
  moneyMinus.innerText = `$${expense}`;
}

// Delete transaction from backend + UI
function removeTransaction(id) {
  fetch(`${apiBaseUrl}/${id}`, {
    method: "DELETE",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to delete transaction");
      }
      transactions = transactions.filter((transaction) => transaction.id !== id);
      init();
    })
    .catch((error) => {
      console.error("Error deleting transaction:", error);
    });
}

// Fetch all transactions from backend
function fetchTransactions() {
  fetch(apiBaseUrl)
    .then((response) => response.json())
    .then((data) => {
      transactions = data;
      init();
    })
    .catch((error) => console.error("Error fetching transactions:", error));
}

// Render app
function init() {
  list.innerHTML = "";
  transactions.forEach(addTransactionDOM);
  updateValues();
}

// Initialize app with backend data
fetchTransactions();
form.addEventListener("submit", addTransaction);
