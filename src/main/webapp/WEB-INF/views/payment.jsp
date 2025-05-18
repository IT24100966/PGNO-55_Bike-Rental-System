<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Process Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Process Payment</h2>
    <form action="payment" method="post" class="border p-4 rounded shadow">
        <input type="hidden" name="action" value="process">
        <div class="mb-3">
            <label for="userId" class="form-label">User ID</label>
            <input type="text" class="form-control" id="userId" name="userId" required>
        </div>
        <div class="mb-3">
            <label for="amount" class="form-label">Amount</label>
            <input type="number" step="0.01" class="form-control" id="amount" name="amount" required>
        </div>
        <div class="mb-3">
            <label for="paymentType" class="form-label">Payment Type</label>
            <select class="form-select" id="paymentType" name="paymentType" onchange="togglePaymentFields()">
                <option value="card">Card</option>
                <option value="wallet">Wallet</option>
            </select>
        </div>
        <div id="cardFields" class="mb-3">
            <label for="cardNumber" class="form-label">Card Number</label>
            <input type="text" class="form-control" id="cardNumber" name="cardNumber">
            <label for="cardType" class="form-label mt-2">Card Type</label>
            <select class="form-select" id="cardType" name="cardType">
                <option value="Visa">Visa</option>
                <option value="MasterCard">MasterCard</option>
            </select>
        </div>
        <div id="walletFields" class="mb-3" style="display: none;">
            <label for="walletId" class="form-label">Wallet ID</label>
            <input type="text" class="form-control" id="walletId" name="walletId">
        </div>
        <button type="submit" class="btn btn-primary">Process Payment</button>
    </form>
    <!-- Button to view all transactions, matching the "Show All Transactions" path -->
    <a href="transactionHistory" class="btn btn-secondary mt-3">View All Transactions</a>
</div>
<script>
    function togglePaymentFields() {
        var paymentType = document.getElementById("paymentType").value;
        document.getElementById("cardFields").style.display = paymentType === "card" ? "block" : "none";
        document.getElementById("walletFields").style.display = paymentType === "wallet" ? "block" : "none";
    }
</script>
</body>
</html>