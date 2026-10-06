package com.mycompany.inventorytrackingproject;

import java.sql.*;
import java.util.ArrayList;

/**
 * ProductDAO — Handles all product database operations.
 * Works with products, electronic_products, and food_products tables.
 */
public class ProductDAO {

    private Connection conn;

    public ProductDAO() {
        this.conn = DBConnection.getInstance().getConn();
    }

    // ── CREATE ────────────────────────────────────────────────────────────────

    /** Inserts a product. Throws Exception if barcode already exists. */
    public void addProduct(Product p) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO products (barcode,name,category,product_type,quantity,price,critical_level) " +
                "VALUES (?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getBarcode());
            ps.setString(2, p.getName());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getType());
            ps.setInt(5, p.getQuantity());
            ps.setDouble(6, p.getPrice());
            ps.setInt(7, p.getCriticalLevel());
            ps.execute();

            ResultSet keys = ps.getGeneratedKeys();
            int newId = keys.next() ? keys.getInt(1) : -1;
            keys.close(); ps.close();

            if (p instanceof ElectronicProduct && newId != -1) {
                ElectronicProduct ep = (ElectronicProduct) p;
                PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO electronic_products (product_id,brand,warranty_months) VALUES (?,?,?)");
                ps2.setInt(1, newId);
                ps2.setString(2, ep.getBrand());
                ps2.setInt(3, ep.getWarrantyMonths());
                ps2.execute(); ps2.close();

            } else if (p instanceof FoodProduct && newId != -1) {
                FoodProduct fp = (FoodProduct) p;
                PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO food_products (product_id,expiry_date,supplier) VALUES (?,?,?)");
                ps2.setInt(1, newId);
                ps2.setDate(2, fp.getExpiryDate() != null
                    ? new java.sql.Date(fp.getExpiryDate().getTime()) : null);
                ps2.setString(3, fp.getSupplier());
                ps2.execute(); ps2.close();
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new Exception("This barcode already exists: " + p.getBarcode());
        } catch (SQLException e) {
            throw new Exception("DB error while adding product: " + e.getMessage());
        }
    }

    // ── READ ──────────────────────────────────────────────────────────────────

    public ArrayList<Product> getAllProducts() {
        return query("SELECT p.*, ep.brand, ep.warranty_months, fp.expiry_date, fp.supplier " +
                     "FROM products p " +
                     "LEFT JOIN electronic_products ep ON p.id=ep.product_id " +
                     "LEFT JOIN food_products fp ON p.id=fp.product_id " +
                     "ORDER BY p.id", null);
    }

    public ArrayList<Product> getByCategory(String category) {
        return query("SELECT p.*, ep.brand, ep.warranty_months, fp.expiry_date, fp.supplier " +
                     "FROM products p " +
                     "LEFT JOIN electronic_products ep ON p.id=ep.product_id " +
                     "LEFT JOIN food_products fp ON p.id=fp.product_id " +
                     "WHERE p.category=? ORDER BY p.id", category);
    }

    public ArrayList<Product> searchByName(String keyword) {
        return query("SELECT p.*, ep.brand, ep.warranty_months, fp.expiry_date, fp.supplier " +
                     "FROM products p " +
                     "LEFT JOIN electronic_products ep ON p.id=ep.product_id " +
                     "LEFT JOIN food_products fp ON p.id=fp.product_id " +
                     "WHERE p.name LIKE ? ORDER BY p.id", "%" + keyword + "%");
    }

    public Product findById(int id) {
        ArrayList<Product> result = query(
            "SELECT p.*, ep.brand, ep.warranty_months, fp.expiry_date, fp.supplier " +
            "FROM products p " +
            "LEFT JOIN electronic_products ep ON p.id=ep.product_id " +
            "LEFT JOIN food_products fp ON p.id=fp.product_id " +
            "WHERE p.id=?", String.valueOf(id));
        return result.isEmpty() ? null : result.get(0);
    }

    public ArrayList<Product> getCriticalProducts() {
        return query("SELECT p.*, ep.brand, ep.warranty_months, fp.expiry_date, fp.supplier " +
                     "FROM products p " +
                     "LEFT JOIN electronic_products ep ON p.id=ep.product_id " +
                     "LEFT JOIN food_products fp ON p.id=fp.product_id " +
                     "WHERE p.quantity <= p.critical_level", null);
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    public void updateProduct(Product p) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE products SET name=?,category=?,quantity=?,price=?,critical_level=? WHERE barcode=?");
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setInt(3, p.getQuantity());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getCriticalLevel());
            ps.setString(6, p.getBarcode());
            ps.execute(); ps.close();

            if (p instanceof ElectronicProduct) {
                ElectronicProduct ep = (ElectronicProduct) p;
                PreparedStatement ps2 = conn.prepareStatement(
                    "UPDATE electronic_products SET brand=?,warranty_months=? " +
                    "WHERE product_id=(SELECT id FROM products WHERE barcode=?)");
                ps2.setString(1, ep.getBrand());
                ps2.setInt(2, ep.getWarrantyMonths());
                ps2.setString(3, p.getBarcode());
                ps2.execute(); ps2.close();

            } else if (p instanceof FoodProduct) {
                FoodProduct fp = (FoodProduct) p;
                PreparedStatement ps2 = conn.prepareStatement(
                    "UPDATE food_products SET expiry_date=?,supplier=? " +
                    "WHERE product_id=(SELECT id FROM products WHERE barcode=?)");
                ps2.setDate(1, fp.getExpiryDate() != null
                    ? new java.sql.Date(fp.getExpiryDate().getTime()) : null);
                ps2.setString(2, fp.getSupplier());
                ps2.setString(3, p.getBarcode());
                ps2.execute(); ps2.close();
            }
        } catch (SQLException e) {
            throw new Exception("DB error while updating: " + e.getMessage());
        }
    }

    public void updateQuantity(int productId, int newQuantity) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE products SET quantity=? WHERE id=?");
            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);
            ps.execute(); ps.close();
        } catch (SQLException e) {
            throw new Exception("Error updating quantity: " + e.getMessage());
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    public boolean deleteProduct(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM products WHERE id=?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    // ── STATS ─────────────────────────────────────────────────────────────────

    public double getTotalValue() {
        return getSingleDouble("SELECT SUM(price*quantity) FROM products");
    }

    public double getAveragePrice() {
        return getSingleDouble("SELECT AVG(price) FROM products");
    }

    public int getTotalStock() {
        return (int) getSingleDouble("SELECT SUM(quantity) FROM products");
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────

    /** Runs a SELECT query with an optional single parameter and builds the product list. */
    private ArrayList<Product> query(String sql, String param) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (param != null) ps.setString(1, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = buildProduct(rs);
                if (p != null) list.add(p);
            }
            rs.close(); ps.close();
        } catch (SQLException e) {
            System.err.println("Query error: " + e.getMessage());
        }
        return list;
    }

    private double getSingleDouble(String sql) {
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) return rs.getDouble(1);
            rs.close();
        } catch (SQLException e) {
            System.err.println("Stat query error: " + e.getMessage());
        }
        return 0;
    }

    /** Builds the correct Product subtype from a ResultSet row. */
    private Product buildProduct(ResultSet rs) throws SQLException {
        int    id            = rs.getInt("id");
        String barcode       = rs.getString("barcode");
        String name          = rs.getString("name");
        String category      = rs.getString("category");
        String type          = rs.getString("product_type");
        int    quantity      = rs.getInt("quantity");
        double price         = rs.getDouble("price");
        int    criticalLevel = rs.getInt("critical_level");

        Product p;
        if ("Electronic".equals(type)) {
            p = new ElectronicProduct(barcode, name, category, quantity, price,
                criticalLevel, rs.getString("brand"), rs.getInt("warranty_months"));
        } else if ("Food".equals(type)) {
            java.sql.Date d = rs.getDate("expiry_date");
            p = new FoodProduct(barcode, name, category, quantity, price,
                criticalLevel, d != null ? new java.util.Date(d.getTime()) : null,
                rs.getString("supplier"));
        } else {
            p = new Product(barcode, name, category, quantity, price, criticalLevel);
        }
        p.setId(id);
        return p;
    }
}
