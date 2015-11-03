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
@Table(name = "comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findById", query = "SELECT c FROM Comment c WHERE c.id = :id"),
    @NamedQuery(name = "Comment.findByUser", query = "SELECT c FROM Comment c WHERE c.user = :user"),
    @NamedQuery(name = "Comment.findByMessage", query = "SELECT c FROM Comment c WHERE c.message = :message"),
    @NamedQuery(name = "Comment.findByTimeCreated", query = "SELECT c FROM Comment c WHERE c.timeCreated = :timeCreated")})
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 32)
    @Column(name = "user")
    private String user;
    @Size(max = 256)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @ManyToOne
    private Post postId;

    public Comment() {
    }

    public Comment(Integer id) {
        this.id = id;
    }

    public Comment(Integer id, Date timeCreated) {
        this.id = id;
        this.timeCreated = timeCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.Comment[ id=" + id + " ]";
    }
    
}
