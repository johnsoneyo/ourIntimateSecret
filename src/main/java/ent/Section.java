/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "section")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Section.findAll", query = "SELECT s FROM Section s"),
    @NamedQuery(name = "Section.findById", query = "SELECT s FROM Section s WHERE s.id = :id"),
    @NamedQuery(name = "Section.findBySectionName", query = "SELECT s FROM Section s WHERE s.sectionName = :sectionName")})
public class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 32)
    @Column(name = "section_name")
    private String sectionName;
    @OneToMany(mappedBy = "sectionId")
    private List<Post> postList;
    
    @Transient
    private boolean editable;

    public Section() {
    }

    public Section(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @XmlTransient
    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
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
        if (!(object instanceof Section)) {
            return false;
        }
        Section other = (Section) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

   
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    
    
}
