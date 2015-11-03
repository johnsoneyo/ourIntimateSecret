/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import java.util.Date;

/**
 *
 * @author johnson3yo
 */
public class ProductOrderDTO {

    private int order_id;
    private String customer_name;
    private int product_size;
     private String state;
    private String time_created;
    private Boolean is_delivered;
   
    public ProductOrderDTO() {
    }

    public ProductOrderDTO(int order_id, String customer_name, int product_size, String state, String time_created,Boolean is_delivered) {
        this.order_id = order_id;
        this.customer_name = customer_name;
        this.product_size = product_size;
        this.state = state;
        this.time_created = time_created;
        this.is_delivered= is_delivered;
    }

  
    
    
    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_size() {
        return product_size;
    }

    public void setProduct_size(int product_size) {
        this.product_size = product_size;
    }

  

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getIs_delivered() {
        return is_delivered;
    }

    public void setIs_delivered(Boolean is_delivered) {
        this.is_delivered = is_delivered;
    }
    
    
    
    
    
}
