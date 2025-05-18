package com.bikerental.pgno55_bikerentalsystem.dao;

import com.bikerental.model.ActivityLog;

import jakarta.servlet.ServletContext;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogDAO {
    private final String filePath;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ActivityLogDAO(ServletContext context) {
        this.filePath = context.getRealPath("/WEB-INF/data/activity_log.txt");
    }

    public void addLog(ActivityLog log) throws IOException {
        List<ActivityLog> logs = getAllLogs();
        log.setId(logs.isEmpty() ? 1 : logs.get(logs.size() - 1).getId() + 1);
        log.setCreatedAt(LocalDateTime.now());
        logs.add(log);
        saveLogs(logs);
    }

    public List<ActivityLog> getAllLogs() throws IOException {
        List<ActivityLog> logs = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return logs;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    logs.add(new ActivityLog(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            parts[3],
                            LocalDateTime.parse(parts[4], formatter)
                    ));
                }
            }
        }
        return logs;
    }

    private void saveLogs(List<ActivityLog> logs) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (ActivityLog log : logs) {
                writer.write(String.format("%d,%s,%s,%s,%s",
                        log.getId(),
                        log.getAction(),
                        log.getDetails(),
                        log.getPerformedBy(),
                        log.getCreatedAt().format(formatter)));
                writer.newLine();
            }
        }
    }
}