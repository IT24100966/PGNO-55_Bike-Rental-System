package com.bikerental.pgno55_bikerentalsystem.servlet;

import com.bikerental.pgno55_bikerentalsystem.model.CardPayment;
import com.bikerental.pgno55_bikerentalsystem.model.WalletPayment;
import com.bikerental.pgno55_bikerentalsystem.service.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet({"/payment", "/"}) // Handles root URL
public class PaymentServlet extends HttpServlet {
    private PaymentService paymentService;  //Dependency (uses payment attribute temporary)

    @Override
    public void init() throws ServletException {
        synchronized (this) {
            paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
            if (paymentService == null) {
                paymentService = new PaymentService(getServletContext());
                getServletContext().setAttribute("paymentService", paymentService);
                System.out.println("Initialized PaymentService in PaymentServlet");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//user submits a payment form(save)
        String action = request.getParameter("action");

        if ("process".equals(action)) {
            String paymentType = request.getParameter("paymentType");
            String userId = request.getParameter("userId");
            double amount = Double.parseDouble(request.getParameter("amount"));
            String paymentId = UUID.randomUUID().toString();

            if ("card".equals(paymentType)) {
                String cardNumber = request.getParameter("cardNumber");
                String cardType = request.getParameter("cardType");
                CardPayment payment = new CardPayment(paymentId, amount, userId, "Pending", cardNumber, cardType);
                paymentService.processPayment(payment);     //Dependency (uses payment attribute temporary)
            } else {
                String walletId = request.getParameter("walletId");
                WalletPayment payment = new WalletPayment(paymentId, amount, userId, "Pending", walletId);
                paymentService.processPayment(payment);
            }
            response.sendRedirect("transactionHistory?userId=" + userId);
        }
    }
}