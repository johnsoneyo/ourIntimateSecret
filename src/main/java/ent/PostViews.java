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
@Table(name = "post_views")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostViews.findAll", query = "SELECT p FROM PostViews p"),
    @NamedQuery(name = "PostViews.findById", query = "SELECT p FROM PostViews p WHERE p.id = :id"),
    @NamedQuery(name = "PostViews.findByLocation", query = "SELECT p FROM PostViews p WHERE p.location = :location"),
    @NamedQuery(name = "PostViews.findByTimeCreated", query = "SELECT p FROM PostViews p WHERE p.timeCreated = :timeCreated")})
public class PostViews implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 32)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @ManyToOne
    private Post postId;

    public PostViews() {
    }

    public PostViews(Integer id) {
        this.id = id;
    }

    public PostViews(Integer id, Date timeCreated) {
        this.id = id;
        this.timeCreated = timeCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
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
        if (!(object instanceof PostViews)) {
            return false;
        }
        PostViews other = (PostViews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.PostViews[ id=" + id + " ]";
    }
    
}
