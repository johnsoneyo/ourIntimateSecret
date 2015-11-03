/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "feeds")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feeds.findAll", query = "SELECT f FROM Feeds f"),
    @NamedQuery(name = "Feeds.findById", query = "SELECT f FROM Feeds f WHERE f.id = :id"),
    @NamedQuery(name = "Feeds.findByPage", query = "SELECT f FROM Feeds f WHERE f.page = :page"),
    @NamedQuery(name = "Feeds.findByIframeVal", query = "SELECT f FROM Feeds f WHERE f.iframeVal = :iframeVal")})
public class Feeds implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 32)
    @Column(name = "page")
    private String page;
    @Size(max = 256)
    @Column(name = "iframe_val")
    private String iframeVal;

    public Feeds() {
    }

    public Feeds(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIframeVal() {
        return iframeVal;
    }

    public void setIframeVal(String iframeVal) {
        this.iframeVal = iframeVal;
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
        if (!(object instanceof Feeds)) {
            return false;
        }
        Feeds other = (Feeds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bl.Feeds[ id=" + id + " ]";
    }
    
}
