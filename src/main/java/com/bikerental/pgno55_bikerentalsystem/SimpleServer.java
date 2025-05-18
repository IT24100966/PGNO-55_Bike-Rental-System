package com.bikerental.pgno55_bikerentalsystem;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;

public class SimpleServer {
    private BikeShareSystem system;

    public SimpleServer(BikeShareSystem system) {
        this.system = system;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/reviews", new ReviewsHandler());
        server.createContext("/api/stats", new StatsHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    class ReviewsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            if (method.equals("POST") && path.equals("/api/reviews")) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String[] params = br.readLine().split("&");
                String bikeId = params[0].split("=")[1];
                int rating = Integer.parseInt(params[1].split("=")[1]);
                String text = java.net.URLDecoder.decode(params[2].split("=")[1], "UTF-8");

                Review review = system.submitReview(bikeId, rating, text);
                String response = String.format("{\"reviewId\":%d,\"bikeId\":\"%s\",\"rating\":%d,\"text\":\"%s\"}",
                        review.getReviewId(), review.getBikeId(), review.getRating(), review.getText());
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else if (method.equals("GET") && path.equals("/api/reviews")) {
                List<Review> reviews = system.getReviews();
                StringBuilder response = new StringBuilder("[");
                for (int i = 0; i < reviews.size(); i++) {
                    Review r = reviews.get(i);
                    response.append(String.format("{\"reviewId\":%d,\"bikeId\":\"%s\",\"rating\":%d,\"text\":\"%s\"}",
                            r.getReviewId(), r.getBikeId(), r.getRating(), r.getText().replace("\"", "\\\"")));
                    if (i < reviews.size() - 1) response.append(",");
                }
                response.append("]");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();
            } else if (method.equals("PUT") && path.matches("/api/reviews/\\d+")) {
                int reviewId = Integer.parseInt(path.split("/")[3]);
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String[] params = br.readLine().split("&");
                int rating = Integer.parseInt(params[0].split("=")[1]);
                String text = java.net.URLDecoder.decode(params[1].split("=")[1], "UTF-8");

                try {
                    Review review = system.editReview(reviewId, rating, text);
                    String response = String.format("{\"reviewId\":%d,\"bikeId\":\"%s\",\"rating\":%d,\"text\":\"%s\"}",
                            review.getReviewId(), review.getBikeId(), review.getRating(), review.getText());
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (IllegalArgumentException e) {
                    String response = "{\"error\":\"Review not found\"}";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else if (method.equals("DELETE") && path.matches("/api/reviews/\\d+")) {
                int reviewId = Integer.parseInt(path.split("/")[3]);
                boolean deleted = system.deleteReview(reviewId);
                if (deleted) {
                    String response = "{\"message\":\"Review deleted\"}";
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    String response = "{\"error\":\"Review not found\"}";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    class StatsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                Map<String, Object> stats = system.getStats();
                int[] distribution = (int[]) stats.get("ratingDistribution");
                String response = String.format("{\"averageRating\":%.1f,\"totalReviews\":%d,\"ratingDistribution\":[%d,%d,%d,%d,%d]}",
                        stats.get("averageRating"), stats.get("totalReviews"),
                        distribution[0], distribution[1], distribution[2], distribution[3], distribution[4]);
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BikeShareSystem system = new BikeShareSystem();
        SimpleServer server = new SimpleServer(system);
        server.start();
    }
}