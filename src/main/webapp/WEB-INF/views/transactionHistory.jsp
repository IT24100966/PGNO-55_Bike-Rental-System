<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Transaction History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Transaction History</h2>
    <div class="mb-4">
        <!-- Updated to reload all transactions without sorting -->
        <form action="transactionHistory" method="post" class="d-inline">
            <input type="hidden" name="action" value="all">
            <button type="submit" class="btn btn-secondary">Show All Transactions</button>
        </form>
    </div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Payment ID</th>
            <th>Amount</th>
            <th>User ID</th>
            <th>Status</th>
            <th>Details</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="payment" items="${transactions}">
            <tr>
                <td>${payment.paymentId}</td>
                <td>${payment.amount}</td>
                <td>${payment.userId}</td>
                <td>${payment.status}</td>
                <td>${payment.paymentDetails}</td>
                <td>
                    <form action="transactionHistory" method="post" class="d-inline">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="paymentId" value="${payment.paymentId}">
                        <input type="hidden" name="userId" value="${param.userId}">
                        <input type="number" step="0.01" name="newAmount" placeholder="New Amount" required>
                        <select name="newStatus">
                            <option value="Pending">Pending</option>
                            <option value="Completed">Completed</option>
                            <option value="Failed">Failed</option>
                        </select>
                        <button type="submit" class="btn btn-sm btn-warning">Update</button>
                    </form>
                    <form action="transactionHistory" method="post" class="d-inline">
                        <input type="hidden" name="action" value="refund">
                        <input type="hidden" name="paymentId" value="${payment.paymentId}">
                        <input type="hidden" name="userId" value="${param.userId}">
                        <button type="submit" class="btn btn-sm btn-danger">Refund</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="payment" class="btn btn-primary">Make Another Payment</a>
</div>
</body>
</html>