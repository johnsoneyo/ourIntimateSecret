/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;


/**
 *
 * @author johnson3yo
 */
public class BillDTO {
    
    private int product_id;
    private String product_name; 
    private int quantity;
    private double subtotal;

    public BillDTO(int product_id, String product_name, int quantity, double subtotal) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    
    
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
}
