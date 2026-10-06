package com.mycompany.inventorytrackingproject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * FileManager — Handles all file read/write operations.
 *
 * Two file types used (as required):
 *   1. Binary file  : products_backup.dat  (ObjectOutputStream / ObjectInputStream)
 *   2. Text file    : stock_log.txt        (FileWriter / BufferedReader)
 */
public class FileManager {

    private static final String BINARY_FILE = "products_backup.dat";
    private static final String TEXT_FILE   = "stock_log.txt";

    // ════════════════════════════════════════════════════════════════════════
    //  BINARY FILE — Product backup
    //  Serializes the full product list into a binary .dat file.
    //  Used for backup; main storage is always the database.
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Writes all products to the binary backup file.
     * Call this after any add/update/delete operation.
     */
    public static void saveProductsBinary(ArrayList<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(BINARY_FILE))) {
            oos.writeObject(products);
            System.out.println("Binary backup saved: " + BINARY_FILE);
        } catch (IOException e) {
            System.err.println("Binary write error: " + e.getMessage());
        }
    }

    /**
     * Reads the binary backup file and returns the product list.
     * Returns empty list if file does not exist yet.
     */
    public static ArrayList<Product> loadProductsBinary() {
        File file = new File(BINARY_FILE);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(BINARY_FILE))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                System.out.println("Binary backup loaded: " + BINARY_FILE);
                return (ArrayList<Product>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Binary read error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  TEXT FILE — Stock movement log
    //  Appends every stock-in / stock-out event to a human-readable .txt file.
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Appends a single stock movement line to the text log file.
     * Called automatically from InventoryManager.stockIn() and stockOut().
     */
    public static void logStockMovement(InventoryManager.StockMovement sm) {
        try (FileWriter fw = new FileWriter(TEXT_FILE, true); // true = append mode
             BufferedWriter bw = new BufferedWriter(fw)) {

            String line = String.format("[%s] %s | Product: %s | Barcode: %s | Amount: %d | Note: %s",
                sm.getDate(),
                sm.getType(),
                sm.getProductName(),
                sm.getBarcode(),
                sm.getAmount(),
                sm.getNote()
            );
            bw.write(line);
            bw.newLine();

        } catch (IOException e) {
            System.err.println("Text log write error: " + e.getMessage());
        }
    }

    /**
     * Reads all lines from the text log file and returns them as a list.
     * Used by the stock screen to display the log history.
     */
    public static ArrayList<String> readStockLog() {
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(TEXT_FILE);
        if (!file.exists()) return lines;

        try (BufferedReader br = new BufferedReader(new FileReader(TEXT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Text log read error: " + e.getMessage());
        }
        return lines;
    }

    /**
     * Writes a summary report to a text file.
     * Includes total value, average price, total stock, and critical products.
     */
    public static void saveReportText(InventoryManager manager) {
        String reportFile = "inventory_report.txt";
        try (FileWriter fw = new FileWriter(reportFile);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

            bw.write("===== INVENTORY REPORT =====");
            bw.newLine();
            bw.write("Generated: " + timestamp);
            bw.newLine();
            bw.write("----------------------------");
            bw.newLine();
            bw.write("Total Value   : " + String.format("%.2f", manager.getTotalValue()));
            bw.newLine();
            bw.write("Average Price : " + String.format("%.2f", manager.getAveragePrice()));
            bw.newLine();
            bw.write("Total Stock   : " + manager.getTotalStock());
            bw.newLine();
            bw.write("----------------------------");
            bw.newLine();
            bw.write("CRITICAL STOCK PRODUCTS:");
            bw.newLine();

            ArrayList<Product> critical = manager.getCriticalProducts();
            if (critical.isEmpty()) {
                bw.write("  None");
                bw.newLine();
            } else {
                for (int i = 0; i < critical.size(); i++) {
                    Product p = critical.get(i);
                    bw.write("  - " + p.getName() + " | Stock: " + p.getQuantity()
                             + " | Critical Level: " + p.getCriticalLevel());
                    bw.newLine();
                }
            }

            bw.write("============================");
            bw.newLine();
            System.out.println("Report saved: " + reportFile);

        } catch (IOException e) {
            System.err.println("Report write error: " + e.getMessage());
        }
    }
}
