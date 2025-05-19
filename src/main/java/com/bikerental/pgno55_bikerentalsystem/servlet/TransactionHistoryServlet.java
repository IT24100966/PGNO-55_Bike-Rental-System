package com.bikerental.pgno55_bikerentalsystem.servlet;

import com.bikerental.pgno55_bikerentalsystem.model.Payment;
import com.bikerental.pgno55_bikerentalsystem.service.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/transactionHistory")
public class TransactionHistoryServlet extends HttpServlet {
    private PaymentService paymentService; //Dependency attribute temporary

    @Override
    public void init() throws ServletException {
        synchronized (this) {
            paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
            if (paymentService == null) {
                paymentService = new PaymentService(getServletContext());
                getServletContext().setAttribute("paymentService", paymentService);
                System.out.println("Initialized PaymentService in TransactionHistoryServlet");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        List<Payment> transactions;

        if (userId == null || userId.isEmpty()) {
            // Show all transactions if no userId is provided (e.g., from payment.jsp)
            transactions = paymentService.getAllTransactions(); //Dependency
        } else {
            // Show transactions for the specific userId
            transactions = paymentService.getTransactionHistory(userId);
        }

        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/WEB-INF/views/transactionHistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//saves updated  the changes to txt file(admin)
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");

        if ("update".equals(action)) {
            String paymentId = request.getParameter("paymentId");
            double newAmount = Double.parseDouble(request.getParameter("newAmount"));
            String newStatus = request.getParameter("newStatus");
            paymentService.updatePayment(paymentId, newAmount, newStatus);
            response.sendRedirect("transactionHistory?userId=" + userId);
        } else if ("refund".equals(action)) {
            String paymentId = request.getParameter("paymentId");
            paymentService.refundTransaction(paymentId);
            response.sendRedirect("transactionHistory?userId=" + userId);
        } else if ("all".equals(action)) {
            // Reload all transactions
            List<Payment> transactions = paymentService.getAllTransactions();
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("/WEB-INF/views/transactionHistory.jsp").forward(request, response);
        } else if ("sort".equals(action)) {
            List<Payment> sortedTransactions = paymentService.sortTransactionsByAmount();
            request.setAttribute("transactions", sortedTransactions);
            request.getRequestDispatcher("/WEB-INF/views/transactionHistory.jsp").forward(request, response);
        } else {
            response.sendRedirect("transactionHistory?userId=" + userId);
        }
    }
}