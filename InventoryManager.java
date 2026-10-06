package com.mycompany.inventorytrackingproject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * InventoryManager — Business logic layer.
 * All data is stored/retrieved via DAO classes (no more ArrayLists).
 * Screens only talk to InventoryManager, never to DAOs directly.
 */
public class InventoryManager {

    // ── Inner classes (unchanged from midterm) ───────────────────────────────

    public static class User {
        private String username, password, fullName;

        public User(String username, String password, String fullName) {
            this.username = username;
            this.password = password;
            this.fullName = fullName;
        }

        public String getUsername()            { return username; }
        public String getFullName()            { return fullName; }
        public boolean checkPassword(String p) { return password.equals(p); }
    }

    public static class StockMovement {
        private String productName, barcode, type, note, date;
        private int amount;

        public StockMovement(String productName, String barcode, String type, int amount, String note) {
            this.productName = productName;
            this.barcode     = barcode;
            this.type        = type;
            this.amount      = amount;
            this.note        = note;
            this.date        = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        }

        public String getProductName() { return productName; }
        public String getBarcode()     { return barcode; }
        public String getType()        { return type; }
        public int    getAmount()      { return amount; }
        public String getNote()        { return note; }
        public String getDate()        { return date; }
    }

    // ── DAO references ───────────────────────────────────────────────────────

    private UserDAO    userDAO    = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();
    private StockDAO   stockDAO   = new StockDAO();

    private User loggedInUser = null;

    // ════════════════════════════════════════════════════════════════════════
    //  USER
    // ════════════════════════════════════════════════════════════════════════

    public boolean login(String username, String password) {
        User u = userDAO.login(username, password);
        if (u != null) {
            loggedInUser = u;
            return true;
        }
        return false;
    }

    public boolean register(String username, String password, String fullName) {
        return userDAO.register(username, password, fullName);
    }

    public void logout() {
        loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  CATEGORY
    // ════════════════════════════════════════════════════════════════════════

    public ArrayList<String> getCategories() {
        return userDAO.getAllCategories();
    }

    public boolean addCategory(String name) {
        return userDAO.addCategory(name);
    }

    public void removeCategory(String name) {
        userDAO.removeCategory(name);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  PRODUCT
    // ════════════════════════════════════════════════════════════════════════

    // Rule 1: Same barcode cannot be added twice (enforced by DB UNIQUE + DAO)
    public void addProduct(Product p) throws Exception {
        productDAO.addProduct(p);
    }

    public boolean removeProduct(int id) {
        return productDAO.deleteProduct(id);
    }

    public void updateProduct(Product p) throws Exception {
        productDAO.updateProduct(p);
    }

    public Product findById(int id) {
        return productDAO.findById(id);
    }

    public ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public ArrayList<Product> getByCategory(String category) {
        return productDAO.getByCategory(category);
    }

    public ArrayList<Product> searchByName(String keyword) {
        return productDAO.searchByName(keyword);
    }

    public ArrayList<Product> getCriticalProducts() {
        return productDAO.getCriticalProducts();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  STOCK
    // ════════════════════════════════════════════════════════════════════════

    // Rule 2: Stock cannot go below zero
    public void stockOut(int id, int amount, String note) throws Exception {
        Product p = productDAO.findById(id);
        if (p == null) throw new Exception("Product not found.");
        if (p.getQuantity() < amount)
            throw new Exception("Not enough stock!\nAvailable: " + p.getQuantity() + ", Requested: " + amount);

        int newQty = p.getQuantity() - amount;
        productDAO.updateQuantity(id, newQty);
        StockMovement smOut = new StockMovement(p.getName(), p.getBarcode(), "EXIT", amount, note);
        stockDAO.addMovement(smOut);
        FileManager.logStockMovement(smOut);
    }

    // Rule 3: Stock-in amount must be greater than zero
    public void stockIn(int id, int amount, String note) throws Exception {
        Product p = productDAO.findById(id);
        if (p == null) throw new Exception("Product not found.");
        if (amount <= 0) throw new Exception("Amount must be greater than 0.");

        int newQty = p.getQuantity() + amount;
        productDAO.updateQuantity(id, newQty);
        StockMovement smIn = new StockMovement(p.getName(), p.getBarcode(), "IN", amount, note);
        stockDAO.addMovement(smIn);
        FileManager.logStockMovement(smIn);
    }

    public ArrayList<StockMovement> getAllMovements() {
        return stockDAO.getAllMovements();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  STATS  (calculated by SQL in ProductDAO)
    // ════════════════════════════════════════════════════════════════════════

    public double getTotalValue()   { return productDAO.getTotalValue(); }
    public double getAveragePrice() { return productDAO.getAveragePrice(); }
    public int    getTotalStock()   { return productDAO.getTotalStock(); }
}