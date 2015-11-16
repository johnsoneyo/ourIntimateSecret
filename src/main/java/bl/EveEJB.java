/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import ent.Admin;
import ent.BillDTO;
import ent.Category;
import ent.Comment;
import ent.InvoiceDTO;
import ent.Orders;
import ent.Post;
import ent.PostViews;
import ent.Product;
import ent.ProductOrder;
import ent.ProductOrderDTO;
import ent.ProductRemarks;
import ent.Section;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author johnson3yo
 */
@Stateless
public class EveEJB {

    @PersistenceContext
    EntityManager em;
    Map sectionMap = new HashMap<String, Section>();

    public List<Post> getPostList() {

        return em.createQuery("select p from Post p where p.isActive = true order by p.timeCreated desc").getResultList();
    }

    public List<Post> getPosts() {

        return em.createQuery("select p from Post p  order by p.timeCreated desc").getResultList();
    }

    public void createPost(Post po, Part part, Section s) {

        try {
            InputStream is = part.getInputStream();
            byte[] value = IOUtils.toByteArray(is);
            po.setTimeCreated(new Date());
            po.setPostImage(value);
            po.setIsActive(Boolean.TRUE);
            po.setSectionId(s);
            em.persist(po);
        } catch (IOException no) {
            no.printStackTrace();
        }

    }

    public byte[] getPostImage(String post_id) {

        return em.find(Post.class, Integer.parseInt(post_id)).getPostImage();
    }

    public void suspendPost(Post p) {
        if (p.getIsActive()) {
            p.setIsActive(Boolean.FALSE);
            em.merge(p);
        } else {
            p.setIsActive(Boolean.TRUE);
            em.merge(p);
        }
    }

    public void editPost(Post p) {

        em.merge(p);
    }

    public List<Section> getSections() {

        return em.createQuery("select s from Section s").getResultList();
    }

    public void createSection(Section sec) {
        em.persist(sec);
    }

    public void updateSection(Section s) {
        em.merge(s);
    }

    public Section getSection(String value) {
        List<Section> sList = this.getSections();
        for (Section s : sList) {
            sectionMap.put(s.getId().toString(), s);
        }

        return (Section) sectionMap.get(value);


    }

    public void recordView(PostViews pv) {
        pv.setLocation("TEST");
        pv.setTimeCreated(new Date());
        em.persist(pv);
    }

    public List<Product> getProductList() {
        return em.createQuery("select p from Product p order by p.timeCreated desc").getResultList();
    }

    public byte[] getProductImage(String product_id) {
        return em.find(Product.class, Integer.parseInt(product_id)).getImage();
    }

    public void createProduct(Product p, Part part, Category c) {

        try {
            InputStream is = part.getInputStream();
            byte[] value = IOUtils.toByteArray(is);
            p.setTimeCreated(new Date());
            p.setIsActive(Boolean.TRUE);
            p.setImage(value);
            p.setCategoryId(c);

            em.persist(p);
        } catch (IOException no) {
            no.printStackTrace();
        }

    }

    public void editProduct(Product p) {

        em.merge(p);
    }

    public void checkout(Map<Product, Integer> cartItems, Orders ord) {

        ord.setTimeEntered(new Date());
        ord.setDayOfDelivery(new Date());
        ord.setIsDelivered(Boolean.FALSE);

        em.persist(ord);

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {

            em.merge(new ProductOrder(entry.getKey(), ord, entry.getValue(), new Date()));
        }

    }

    public List<ProductOrderDTO> getProductOrders() {
        List<Object[]> ordList = em.createNativeQuery("select order_id,concat(ord.title,space(1),ord.first_name,space(1)"
                + ",ord.last_name)as name,count(product_id),state,time_created,ord.is_delivered from product_order "
                + "join (select id from product)as pee on pee.id = product_id join orders ord on ord.id"
                + " = product_order.order_id group by order_id  order by time_created desc").getResultList();

        List<ProductOrderDTO> oList = new ArrayList();

        for (Object[] o : ordList) {
            oList.add(new ProductOrderDTO(Integer.parseInt(String.valueOf(o[0])), String.valueOf(o[1]), Integer.parseInt(String.valueOf(o[2])), String.valueOf(o[3]), String.valueOf(o[4]), Boolean.valueOf(String.valueOf(o[5]))));
        }

        return oList;
    }

    public byte[] downloadInvoice(int order_id) {

        // InvoiceDTO inv = new InvoiceDTO();

        List<Object[]> orList = em.createNativeQuery(" select p.id,p.name,po.quantity,po.quantity*p.price as subtotal from product_order po left join orders rr  on rr.id=po.order_id  join product p on"
                + " p.id=po.product_id where po.order_id = " + order_id).getResultList();



        List<BillDTO> bList = new ArrayList();

        for (Object[] b : orList) {
            bList.add(new BillDTO(Integer.parseInt(String.valueOf(b[0])), String.valueOf(b[1]), Integer.parseInt(String.valueOf(b[2])), Double.parseDouble(String.valueOf(b[3]))));
        }

        List<Object[]> values = em.createNativeQuery("select order_id, sum(p.price*quantity) as total,concat(o.title,space(1),o.first_name,space(1),o.last_name) as name,o.addr1 from product_order po join product p on p.id ="
                + " po.product_id join orders o on o.id = po.order_id where order_id = " + order_id + " group by order_id ").getResultList();

        InvoiceDTO i = null;
        for (Object[] v : values) {
            i = new InvoiceDTO(Double.parseDouble(String.valueOf(v[1])), String.valueOf(v[2]), Integer.parseInt(String.valueOf(v[0])), String.valueOf(v[3]), bList);
        }

        List<InvoiceDTO> iList = new ArrayList();


        iList.add(i);

        try {

            //Test environment
            //   String masterReportFileName = "/home/johnson3yo/Desktop/shop/evesi.jrxml";
            //  String subReportFileName = "/home/johnson3yo/Desktop/shop/evis_sub.jrxml";

            //Production environment
            String masterReportFileName = "/home/ubuntu/jpr/evesi.jrxml";
            String subReportFileName = "/home/ubuntu/jpr/evis_sub.jrxml";
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(iList);

            JasperReport jasperMasterReport = JasperCompileManager.compileReport(masterReportFileName);
            JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportFileName);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("subreportParameter", jasperSubReport);
            JasperPrint jp = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);
            byte[] val = JasperExportManager.exportReportToPdf(jp);
            return val;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }



    }

    public void changeStatus(int order_id) {

        Orders d = em.find(Orders.class, order_id);
        if (d.getIsDelivered()) {
            d.setIsDelivered(Boolean.FALSE);
            em.merge(d);
        } else {
            d.setIsDelivered(Boolean.TRUE);
            em.merge(d);
        }
    }

    public byte[] downloadDispatch(List<ProductOrderDTO> dispatchList) {
        List<Orders> oList = new ArrayList();
        for (ProductOrderDTO o : dispatchList) {
            Orders or = em.find(Orders.class, o.getOrder_id());
            oList.add(or);
        }
        try {
            JasperReport jasperReport = null;
            JasperPrint jasperPrint = null;
            JasperDesign jasperDesign = null;
            Map parameters = new HashMap();
            //Test environment
            //  jasperDesign = JRXmlLoader.load("/home/johnson3yo/Desktop/shop/report2.jrxml");
            //Production environment
            jasperDesign = JRXmlLoader.load("/home/ubuntu/jpr/report2.jrxml");
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(oList));
            byte[] val = JasperExportManager.exportReportToPdf(jasperPrint);
            return val;
        } catch (Exception n) {
            n.printStackTrace();
            return null;
        }

    }

    public void comment(Post p, Comment cm) {

        String word = wordChecker(cm.getMessage());
        cm.setTimeCreated(new Date());
        cm.setPostId(p);
        cm.setMessage(word);
        cm.setUser(String.valueOf(new Date().getTime()));
        em.persist(cm);

    }

    public List getComments(Post p) {

        Query q = em.createQuery("select c from Comment c where c.postId =:postId");
        q.setParameter("postId", p);
        return q.getResultList();
    }

    private String wordChecker(String message) {
        String[] split = message.split("\\s+");
        String new_word = "";

        
        for (int i = 0; i < split.length; i++) {
            if (split[i].equalsIgnoreCase("fuck")) {
               
                new_word = new_word.concat("f**k").concat(" ");
             
            }else if (split[i].equalsIgnoreCase("shit")) {
              
                new_word = new_word.concat("s**t").concat(" ");
                
            }else if (split[i].equalsIgnoreCase("dick")) {
              
                new_word = new_word.concat("d**k").concat(" ");
              
            }else 
            if (split[i].equalsIgnoreCase("pussy")) {
                new_word = new_word.concat("p***y").concat(" ");
             
            }else if (split[i].equalsIgnoreCase("bitch")) {
              
                new_word = new_word.concat("b***h").concat(" ");
               
            }
            
            else {
              
                new_word = new_word.concat(split[i]).concat(" ");
             
            }


        }
       
        return new_word;
    }

    
    public void login(Admin adm) throws AdminException {
        Query q = em.createQuery("select a from Admin a where a.username = :username and a.password =:password");
        q.setParameter("username", adm.getUsername());
        q.setParameter("password", adm.getPassword());
        try {
            Admin a = (Admin) q.getSingleResult();
        } catch (NoResultException n) {
            throw new AdminException("Invalid credentials, please contact the administrator on 0708881265");
        }
    }

    public List<Category> getCategories() {
        return em.createQuery("select c from Category c ").getResultList();
    }

    public List<Category> createCat() {
        em.persist(new Category("New Category"));
        return em.createQuery("select c from Category c").getResultList();
    }

    public void modifyCat(Category c) {
        em.merge(c);
    }

    public Category findCat(int catid) {
        return em.find(Category.class, catid);
    }

    public void leaveComment(ProductRemarks prm, Product pr) {
        prm.setTimeCreated(new Date());
        prm.setProductId(pr);
        prm.setUser(String.valueOf(new Date().getTime()));
        em.persist(prm);
    }

    public List<ProductRemarks> getRemarkList(Product product) {
        Query q = em.createQuery("select pr from ProductRemarks pr where pr.productId =:productId");
        q.setParameter("productId", product);
        return q.getResultList();
    }

    public List getAllComments() {
        return em.createQuery("select c from Comment c order by c.timeCreated desc ").getResultList();
    }

    public void deleteComment(Comment c) {
        Comment cm = em.merge(c);
        em.remove(cm);
    }

    public List<ProductRemarks> getAllRemarks() {
        return em.createQuery("select r from ProductRemarks r order by r.timeCreated desc ").getResultList();

    }

    public void deleteRemark(ProductRemarks p) {
        ProductRemarks pr = em.merge(p);
        em.remove(pr);
    }

    public List<Post> getPostList(int start, int end) {

        return em.createQuery(String.format("select p from Post p where p.isActive = true order by p.timeCreated desc ")).setFirstResult(start).setMaxResults(end).getResultList();

    }

    public Long getTotalActivePosts() {
        return (Long) em.createQuery("select count(p) from Post p where p.isActive = true  ").getSingleResult();

    }

    public List<Post> getSimilarPost(Post p) {

        String[] post_title = p.getPostTitle().split("\\s+");
        Set<Post> similarPost = new HashSet();

        for (int i = 0; i < post_title.length; i++) {
            String d = post_title[i];
            int word_length = String.valueOf(d).length();
            if (word_length >= 4) {
                Query q = em.createQuery("select p from Post p where p.postTitle like '%" + String.valueOf(d) + "%' ");
                List<Post> pList = q.getResultList();
                similarPost.addAll(pList);
            }
        }
        List<Post> pr = new ArrayList(similarPost);
        return pr;
    }

    public List<Post> getPopularPost() {
        List<Post> pList = em.createQuery("select p.postId from PostViews p group by "
                + "p.postId order by count(p.id) desc ").setMaxResults(5).getResultList();
        return pList;
    }

    public Post findPost(int post_id) {
       
     return em.find(Post.class, post_id);
    }

    public void removeProduct(Product p) {
      Product po = em.merge(p);
      em.remove(po);
    }
}
