package com.mycompany.inventorytrackingproject;

// This class represents a product in the system
public class Product implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private int id; // Set by DBManager after loading from database
    private String barcode, name, category;
    private int quantity, criticalLevel;
    private double price;

    // Constructor: runs when a new product is created
    public Product(String barcode, String name, String category, int quantity, double price, int criticalLevel){
        this.id = 0; // Will be set by DBManager after INSERT
        this.barcode = barcode; 
        this.name = name; 
        this.category = category;
        this.quantity = quantity; 
        this.price = price; 
        this.criticalLevel = criticalLevel;
    }
    
    // get and set methods
    public String getBarcode(){
    return barcode;
    }
    public int getId(){
    return id;
    }
    // Used by DBManager to set the real database ID after loading from DB
    public void setId(int id){
    this.id = id;
    }
    public String getCategory(){
    return category;
    }
    public void setCategory(String category){
    this.category = category;
    }
    public int getQuantity(){
    return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
    return price;
    }
    public int getCriticalLevel(){
    return criticalLevel;
    }
    public void setName(String name){
    this.name = name;
    }

    public void setPrice(double price){
    this.price = price;
    }

    public void setCriticalLevel(int criticalLevel){
    this.criticalLevel = criticalLevel;
    }
    // Check if stock is below the critical Level
    public boolean isBelowCritical(){
        return quantity <= criticalLevel; 
      }
    // Return product type
    public String getType(){
        return "Genel"; 
    }
    
    public String getExtraInfo(){
        return "";
    }
     // This method shows product as text
    @Override
    public String toString(){
        return "[" + id + "] " + name + " | Stock: " + quantity + (isBelowCritical() ? "(critical Stock!) " : "");
    }
}