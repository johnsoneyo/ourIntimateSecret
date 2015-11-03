/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johnson3yo
 */
public class InvoiceDTO {
    
    private String name;
    private int order_id;
    private String address;
    private List<BillDTO> bill = new ArrayList();
    private double total;

    public InvoiceDTO(double total,String name, int order_id, String address,List<BillDTO>bl) {
        this.name = name;
        this.order_id = order_id;
        this.bill=bl;
        this.address = address;
        this.total = total;
        
        
    }

    
    
    
    
    public List<BillDTO> getBill() {
        return bill;
    }

    public void setBill(List<BillDTO> bill) {
        this.bill = bill;
    }

  
   
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

  
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
}
