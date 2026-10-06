package com.mycompany.inventorytrackingproject;

// This class is used for electronic products
public class ElectronicProduct extends Product{
    private String brand;
    private int warrantyMonths;
       // Constructor: runs when we create a new ElectronicProduct
    public ElectronicProduct(String barcode, String name, String category, int quantity, double price,
                                           int criticalLevel, String brand, int warrantyMonths){
        super(barcode, name, category, quantity, price, criticalLevel);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }
    // get and set methods
    public String getBrand(){ 
        return brand; 
    }   
    public void setBrand(String b){
        this.brand = b; 
    }
    public int getWarrantyMonths(){ 
        return warrantyMonths; 
    }
    public void setWarrantyMonths(int w){ 
        this.warrantyMonths = w;
    }
    // changing Type
    @Override
    public String getType(){ 
        return "Elektronik"; 
    }
    // changing extraInfo
    @Override
    public String getExtraInfo(){
        return "Brand: " + brand + " | warranty: " + warrantyMonths + " Months";
    }
}
