package com.mycompany.inventorytrackingproject;

import java.sql.*;
import java.util.ArrayList;

/**
 * StockDAO — Handles stock_movements table operations.
 */
public class StockDAO {

    private Connection conn;

    public StockDAO() {
        this.conn = DBConnection.getInstance().getConn();
    }

    public void addMovement(InventoryManager.StockMovement sm) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO stock_movements (product_name,barcode,type,amount,note,move_date) " +
                "VALUES (?,?,?,?,?,?)");
            ps.setString(1, sm.getProductName());
            ps.setString(2, sm.getBarcode());
            ps.setString(3, sm.getType());
            ps.setInt(4, sm.getAmount());
            ps.setString(5, sm.getNote());
            ps.setString(6, sm.getDate());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Add movement error: " + e.getMessage());
        }
    }

    public ArrayList<InventoryManager.StockMovement> getAllMovements() {
        ArrayList<InventoryManager.StockMovement> list = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT * FROM stock_movements ORDER BY id DESC");
            while (rs.next()) {
                list.add(new InventoryManager.StockMovement(
                    rs.getString("product_name"),
                    rs.getString("barcode"),
                    rs.getString("type"),
                    rs.getInt("amount"),
                    rs.getString("note")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Get movements error: " + e.getMessage());
        }
        return list;
    }
}
