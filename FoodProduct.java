package com.mycompany.inventorytrackingproject;
import java.util.Date;
import java.text.SimpleDateFormat;
// // This class is for food products
public class FoodProduct extends Product{
    private Date expiryDate;
    private String supplier;
    // Constructor: runs when we create a new FoodProduct
    public FoodProduct(String barcode, String name, String category, int quantity, double price, 
                                int criticalLevel, Date expiryDate, String supplier){
        super(barcode, name, category, quantity, price, criticalLevel);
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }
    // get and set methods
    public Date getExpiryDate(){
        return expiryDate; 
    }
    public void setExpiryDate(Date d){ 
        this.expiryDate = d; 
    }
    public String getSupplier(){ 
        return supplier;
    }
    public void setSupplier(String s){
        this.supplier = s; 
    }
    // Check if product is expired
    public boolean isExpired(){
        return expiryDate != null && expiryDate.before(new Date());
    }
    // changing product type
    @Override
    public String getType(){
        return "Food"; 
    }
    // changing extraInfo
    @Override
    public String getExtraInfo(){
    String exp;

    if(expiryDate != null){
        exp = expiryDate.toString();
    }else{
        exp = "?";
    }
    String result = "Tedarikçi: " + supplier + " | Expiry Date: " + exp;
    if(isExpired()){
        result += " SÜRESI DOLDU";
    }
    return result;
        }
    }
