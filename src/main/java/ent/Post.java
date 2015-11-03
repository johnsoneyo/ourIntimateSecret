/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "post")
@XmlRootElement
public class Post implements Serializable {
    @Lob
    @Column(name = "post_image")
    private byte[] postImage;
    @Lob
    @Size(max = 65535)
    @Column(name = "post_message")
    private String postMessage;
    @OneToMany(mappedBy = "postId")
    private List<PostViews> postViewsList;
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @ManyToOne
    private Section sectionId;
    @Column(name = "is_active")
    private Boolean isActive;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "post_title")
    private String postTitle;
    @Size(max = 256)
    @Column(name = "post_intro")
    private String postIntro;
   
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @OneToMany(mappedBy = "postId")
    private List<Comment> commentList;

    public Post() {
    }

    public Post(Integer id) {
        this.id = id;
    }

    public Post(Integer id, Date timeCreated) {
        this.id = id;
        this.timeCreated = timeCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostIntro() {
        return postIntro;
    }

    public void setPostIntro(String postIntro) {
        this.postIntro = postIntro;
    }

  

    public byte[] getPostImage() {
        return postImage;
    }

    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
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
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.Post[ id=" + id + " ]";
    }

   

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    

    public Section getSectionId() {
        return sectionId;
    }

    public void setSectionId(Section sectionId) {
        this.sectionId = sectionId;
    }

   

    @XmlTransient
    public List<PostViews> getPostViewsList() {
        return postViewsList;
    }

    public void setPostViewsList(List<PostViews> postViewsList) {
        this.postViewsList = postViewsList;
    }

  
    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

 
 
  
    
}
