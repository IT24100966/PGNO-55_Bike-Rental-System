package com.bikerental.pgno55_bikerentalsystem.service;

import com.bikerental.pgno55_bikerentalsystem.model.Payment;
import com.bikerental.pgno55_bikerentalsystem.model.CardPayment;
import com.bikerental.pgno55_bikerentalsystem.model.WalletPayment;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PaymentService {
    private Queue<Payment> paymentQueue = new ConcurrentLinkedQueue<>();
    private List<Payment> transactions = new ArrayList<>();
    private String transactionFilePath;
    private static final Object fileLock = new Object(); // For synchronized file access

    public PaymentService(ServletContext context) {
        this.transactionFilePath = context.getRealPath("/WEB-INF/transactions.txt");
        loadFromFile();
    }

    // Create: Process a new payment
    public void processPayment(Payment payment) {
        synchronized (fileLock) {
            loadFromFile(); // Reload to ensure we have the latest state
            paymentQueue.offer(payment);
            transactions.add(payment);
            saveToFile(payment);
            processPaymentQueue();
        }
    }

    // Read: View transaction history
    public List<Payment> getTransactionHistory(String userId) {
        synchronized (fileLock) {
            loadFromFile(); // Reload to ensure we have the latest state
            List<Payment> userTransactions = new ArrayList<>();
            for (Payment payment : transactions) {
                if (payment.getUserId().equals(userId)) {
                    userTransactions.add(payment);
                }
            }
            return userTransactions;
        }
    }

    // Read: Get all transactions
    public List<Payment> getAllTransactions() {
        synchronized (fileLock) {
            loadFromFile(); // Reload to ensure we have the latest state
            return new ArrayList<>(transactions); // Return a copy of all transactions
        }
    }

    // Update: Modify payment details
    public void updatePayment(String paymentId, double newAmount, String newStatus) {
        synchronized (fileLock) {
            loadFromFile();
            for (Payment payment : transactions) {
                if (payment.getPaymentId().equals(paymentId)) {
                    payment.setAmount(newAmount);
                    payment.setStatus(newStatus);
                    updateFile();
                    break;
                }
            }
        }
    }

    // Delete: Refund transaction
    public void refundTransaction(String paymentId) {
        synchronized (fileLock) {
            loadFromFile();
            transactions.removeIf(payment -> payment.getPaymentId().equals(paymentId));
            updateFile();
        }
    }

    // Process payments in queue
    public void processPaymentQueue() {
        synchronized (fileLock) {
            while (!paymentQueue.isEmpty()) {
                Payment payment = paymentQueue.poll();
                payment.setStatus("Completed");
            }
            updateFile();
        }
    }

    // Quick Sort implementation for sorting transactions by amount
    public List<Payment> sortTransactionsByAmount() {
        synchronized

        (fileLock) {
            loadFromFile();
            Payment[] transactionArray = transactions.toArray(new Payment[0]);
            quickSort(transactionArray, 0, transactionArray.length - 1);
            return new ArrayList<>(Arrays.asList(transactionArray));
        }
    }

    private void quickSort(Payment[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(Payment[] arr, int low, int high) {
        double pivot = arr[high].getAmount();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].getAmount() <= pivot) {
                i++;
                Payment temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Payment temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // File handling
    private void saveToFile(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFilePath, true))) {
            writer.write(payment.getPaymentId() + "," + payment.getAmount() + "," + payment.getUserId() + "," + payment.getStatus());
            if (payment instanceof CardPayment) {
                CardPayment card = (CardPayment) payment;
                writer.write(",Card," + card.getCardNumber() + "," + card.getCardType());
            } else if (payment instanceof WalletPayment) {
                WalletPayment wallet = (WalletPayment) payment;
                writer.write(",Wallet," + wallet.getWalletId());
            }
            writer.newLine();
            System.out.println("Saved payment to file: " + payment.getPaymentId());
        } catch (IOException e) {
            System.err.println("Error saving payment to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(transactionFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created new transactions file at: " + transactionFilePath);
            } catch (IOException e) {
                System.err.println("Error creating transactions file: " + e.getMessage());
                e.printStackTrace();
            }
        }

        transactions.clear(); // Clear the list before reloading
        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String paymentId = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String userId = parts[2];
                String status = parts[3];
                String type = parts[4];

                Payment payment;
                if (type.equals("Card")) {
                    if (parts.length < 7) continue;
                    payment = new CardPayment(paymentId, amount, userId, status, parts[5], parts[6]);
                } else {
                    payment = new WalletPayment(paymentId, amount, userId, status, parts[5]);
                }
                transactions.add(payment);
            }
            System.out.println("Loaded " + transactions.size() + " transactions from file.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading transactions from file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFilePath))) {
            for (Payment payment : transactions) {
                writer.write(payment.getPaymentId() + "," + payment.getAmount() + "," + payment.getUserId() + "," + payment.getStatus());
                if (payment instanceof CardPayment) {
                    CardPayment card = (CardPayment) payment;
                    writer.write(",Card," + card.getCardNumber() + "," + card.getCardType());
                } else if (payment instanceof WalletPayment) {
                    WalletPayment wallet = (WalletPayment) payment;
                    writer.write(",Wallet," + wallet.getWalletId());
                }
                writer.newLine();
            }
            System.out.println("Updated transactions file with " + transactions.size() + " records.");
        } catch (IOException e) {
            System.err.println("Error updating transactions file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}