/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "product_remarks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductRemarks.findAll", query = "SELECT p FROM ProductRemarks p"),
    @NamedQuery(name = "ProductRemarks.findById", query = "SELECT p FROM ProductRemarks p WHERE p.id = :id"),
    @NamedQuery(name = "ProductRemarks.findByRemark", query = "SELECT p FROM ProductRemarks p WHERE p.remark = :remark"),
    @NamedQuery(name = "ProductRemarks.findByUser", query = "SELECT p FROM ProductRemarks p WHERE p.user = :user"),
    @NamedQuery(name = "ProductRemarks.findByTimeCreated", query = "SELECT p FROM ProductRemarks p WHERE p.timeCreated = :timeCreated")})
public class ProductRemarks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "remark")
    private String remark;
    @Size(max = 32)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne
    private Product productId;

    public ProductRemarks() {
    }

    public ProductRemarks(Integer id) {
        this.id = id;
    }

    public ProductRemarks(Integer id, Date timeCreated) {
        this.id = id;
        this.timeCreated = timeCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductRemarks)) {
            return false;
        }
        ProductRemarks other = (ProductRemarks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.ProductRemarks[ id=" + id + " ]";
    }
    
}
