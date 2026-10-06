package com.mycompany.inventorytrackingproject;

import java.sql.*;
import java.util.ArrayList;

/**
 * UserDAO — Handles users and categories in the database.
 */
public class UserDAO {

    private Connection conn;

    public UserDAO() {
        this.conn = DBConnection.getInstance().getConn();
    }

    // ── USER ─────────────────────────────────────────────────────────────────

    /** Returns the User if credentials match, null otherwise. */
    public InventoryManager.User login(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                InventoryManager.User u = new InventoryManager.User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("full_name")
                );
                rs.close(); ps.close();
                return u;
            }
            rs.close(); ps.close();
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return null;
    }

    /** Returns false if username already exists. */
    public boolean register(String username, String password, String fullName) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (username,password,full_name) VALUES (?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullName);
            ps.execute();
            ps.close();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // username taken
        } catch (SQLException e) {
            System.err.println("Register error: " + e.getMessage());
            return false;
        }
    }

    // ── CATEGORY ─────────────────────────────────────────────────────────────

    public ArrayList<String> getAllCategories() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement()
                .executeQuery("SELECT name FROM categories ORDER BY name");
            while (rs.next()) list.add(rs.getString("name"));
            rs.close();
        } catch (SQLException e) {
            System.err.println("Get categories error: " + e.getMessage());
        }
        return list;
    }

    /** Returns false if category already exists. */
    public boolean addCategory(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO categories (name) VALUES (?)");
            ps.setString(1, name);
            ps.execute();
            ps.close();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            System.err.println("Add category error: " + e.getMessage());
            return false;
        }
    }

    /** Moves products in this category to 'General', then deletes the category. */
    public void removeCategory(String name) {
        if (name.equalsIgnoreCase("General")) return;
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                "UPDATE products SET category='General' WHERE category=?");
            ps1.setString(1, name);
            ps1.execute();
            ps1.close();

            PreparedStatement ps2 = conn.prepareStatement(
                "DELETE FROM categories WHERE name=?");
            ps2.setString(1, name);
            ps2.execute();
            ps2.close();
        } catch (SQLException e) {
            System.err.println("Remove category error: " + e.getMessage());
        }
    }
}
