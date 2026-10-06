package com.mycompany.inventorytrackingproject;
 
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
 
public class MainScreen extends javax.swing.JFrame {
    
    private InventoryManager manager;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainScreen.class.getName());
 
   // Main screen constructor
  public MainScreen(InventoryManager manager){
    this.manager = manager;
    initComponents();
    setTitle("Main Screen - Inventory Tracking System");
 
    // Add Query and CategoryDetail buttons (outside NetBeans generated code)
    javax.swing.JButton btnQuery = new javax.swing.JButton("Search Products");
    btnQuery.setBackground(new java.awt.Color(204, 204, 204));
    btnQuery.addActionListener(e -> {
        QueryScreen qs = new QueryScreen(manager, this);
        qs.setVisible(true);
        this.setVisible(false);
    });
 
    javax.swing.JButton btnCatDetail = new javax.swing.JButton("Category Detail");
    btnCatDetail.setBackground(new java.awt.Color(204, 204, 204));
    btnCatDetail.addActionListener(e -> {
        CategoryDetailScreen cd = new CategoryDetailScreen(manager, this);
        cd.setVisible(true);
        this.setVisible(false);
    });
 
    javax.swing.JPanel extraPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    extraPanel.add(btnQuery);
    extraPanel.add(btnCatDetail);
    getContentPane().add(extraPanel, java.awt.BorderLayout.AFTER_LAST_LINE);
    pack();
 
    refreshScreen();
    }
  
  public void refreshScreen(){
    // Show logged in user
    InventoryManager.User u = manager.getLoggedInUser();
    if(u != null){
        lblWelcome.setText("Welcome: " + u.getFullName());
    }
    // adding categories to comboBox
    jComboBox1.removeAllItems();
 
    for(int i = 0; i < manager.getCategories().size(); i++){
        String c = manager.getCategories().get(i);
        jComboBox1.addItem(c);
    }
    
    loadTable(manager.getAllProducts());
    updateStats();
    }
    // Fills the table with products
    private void loadTable(ArrayList<Product> list){
 
    String[] columns = {"ID", "Barcode", "Product Name", "Category", "Type", "Stock", "Price ", "Extra Info"};
 
    DefaultTableModel dtm = new DefaultTableModel(columns, 0){
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
 
    for(int i = 0; i < list.size(); i++){
        Product p = list.get(i);
 
        dtm.addRow(new Object[]{p.getId(), p.getBarcode(), p.getName(), p.getCategory(), p.getType(),
                                 p.getQuantity(), p.getPrice(),p.getExtraInfo()});
        }
 
        jTable1.setModel(dtm);
    }
    // Updates the statistic labels on the screen
    private void updateStats(){
        lblTotalValue.setText("Total Value: " + manager.getTotalValue());
        lblAvgPrice.setText("Average Price: " + manager.getAveragePrice());
        lblTotalStock.setText("Total Stock: " + manager.getTotalStock());
        int crit = manager.getCriticalProducts().size();
        lblCritical.setText("Critical Product: " + crit);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        btnProducts = new javax.swing.JButton();
        btnStock = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        btnShowAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTotalValue = new javax.swing.JLabel();
        lblAvgPrice = new javax.swing.JLabel();
        lblTotalStock = new javax.swing.JLabel();
        lblCritical = new javax.swing.JLabel();
        Category = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblWelcome.setText("WELCOME");

        btnProducts.setBackground(new java.awt.Color(204, 204, 204));
        btnProducts.setText("Product Management");
        btnProducts.addActionListener(this::btnProductsActionPerformed);

        btnStock.setBackground(new java.awt.Color(204, 204, 204));
        btnStock.setText("Stock Movements");
        btnStock.addActionListener(this::btnStockActionPerformed);

        btnLogout.setBackground(new java.awt.Color(204, 204, 204));
        btnLogout.setText("EXIT");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnFilter.setBackground(new java.awt.Color(204, 204, 204));
        btnFilter.setText("Filter");
        btnFilter.addActionListener(this::btnFilterActionPerformed);

        btnShowAll.setBackground(new java.awt.Color(204, 204, 204));
        btnShowAll.setText("Show All");
        btnShowAll.addActionListener(this::btnShowAllActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        lblTotalValue.setText("Total Value: ");

        lblAvgPrice.setText("Avg. Price:");

        lblTotalStock.setText("Total Stock: ");

        lblCritical.setText("Critical Product:");

        Category.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Category.setText("Category:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(lblWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnProducts)
                                .addGap(32, 32, 32)
                                .addComponent(btnStock))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(Category)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(38, 38, 38)
                        .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(lblAvgPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTotalStock, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblCritical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblWelcome))
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnShowAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Category))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalValue)
                    .addComponent(lblAvgPrice)
                    .addComponent(lblTotalStock)
                    .addComponent(lblCritical))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        // TODO add your handling code here:
        ProductScreen ps = new ProductScreen(manager, this);
        ps.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnProductsActionPerformed

    private void btnStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStockActionPerformed
        // TODO add your handling code here:
        StockScreen ss = new StockScreen(manager, this);
        ss.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnStockActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        manager.logout();
        LoginScreen ls = new LoginScreen(manager);
        ls.setVisible(true);
        this.dispose();      
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        String selected = (String)jComboBox1.getSelectedItem();
        if(selected != null){
        loadTable(manager.getByCategory(selected));
    }
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllActionPerformed
        // TODO add your handling code here:
        loadTable(manager.getAllProducts());
    }//GEN-LAST:event_btnShowAllActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Category;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnShowAll;
    private javax.swing.JButton btnStock;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAvgPrice;
    private javax.swing.JLabel lblCritical;
    private javax.swing.JLabel lblTotalStock;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JLabel lblWelcome;
    // End of variables declaration//GEN-END:variables
}
