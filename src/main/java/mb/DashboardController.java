/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import bl.EveEJB;
import com.crowninteractive.handlers.EmailHandler;
import ent.Category;
import ent.Comment;
import ent.Orders;
import ent.Post;
import ent.PostViews;
import ent.Product;
import ent.ProductOrderDTO;
import ent.ProductRemarks;
import ent.Section;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author johnson3yo
 */
@ManagedBean
@ViewScoped
public class DashboardController {

    private List<Post> postList = new ArrayList();
    private List<Product> productList = new ArrayList();
    private List<Post> sectionPostList = new ArrayList();
    private ent.PostViews pv = new ent.PostViews();
    private List<Section> sectionList = new ArrayList();
    private Product p = new Product();
    private Post po = new Post();
    private Part part;
    private Section sec = new Section();
    private Orders ord = new Orders();
    private String state;
    private String showAddress;
    private List<ProductOrderDTO> productOrders = new ArrayList();
    private List<ProductOrderDTO> dispatchList = new ArrayList();
    private Comment cm = new Comment();
    private List<Comment> cList = new ArrayList();
    private UIComponent component;
    private FacesContext context;
    private List<Category> categories = new ArrayList();
    private Boolean catedit;
    private ProductRemarks prm = new ProductRemarks();
    private int catid;
    @EJB
    EveEJB ev;
    private Boolean hideColumn;
    private int start = 0;
    private int end = 4;
    private int totalRows;
    private int currentRow;

    @PostConstruct
    public void getPosts() {
        showAddress = "Lagos";
        postList =  this.getPostList(start, end);
        sectionList = ev.getSections();
        productList = ev.getProductList();
        productOrders = ev.getProductOrders();
        categories = ev.getCategories();
    }
    @ManagedProperty(value = "#{adminController}")
    private AdminController logc;

    public int getStart() {
        System.out.println("Start is "+start);
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void nextPage() {
        start = start + end;

        postList = ev.getPostList(start, end);


    }

    public void previousPage() {
        start = start - end;
        postList = ev.getPostList(start, end);


    }

    private List<Post> getPostList(int start, int end) {

        return ev.getPostList(start, end);
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public Post getPo() {
        return po;
    }

    public void setPo(Post po) {
        this.po = po;
    }

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }

    public ProductRemarks getPrm() {
        return prm;
    }

    public void setPrm(ProductRemarks prm) {
        this.prm = prm;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getHideColumn() {
        return hideColumn;
    }

    public void setHideColumn(Boolean hideColumn) {
        this.hideColumn = hideColumn;
    }

    public String createPost() {

        ev.createPost(po, part, sec);

        return "manage_post";
    }

    public String trimPost(String value) {

        return value.substring(0, 40).concat("....");
    }

    public AdminController getLogc() {
        return logc;
    }

    public void setLogc(AdminController logc) {
        this.logc = logc;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public Boolean getCatedit() {
        return catedit;
    }

    public void setCatedit(Boolean catedit) {
        this.catedit = catedit;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public Section getSec() {
        return sec;
    }

    public void setSec(Section sec) {
        this.sec = sec;
    }

    public List<Post> getSectionPostList() {
        return sectionPostList;
    }

    public void setSectionPostList(List<Post> sectionPostList) {
        this.sectionPostList = sectionPostList;
    }

    public Comment getCm() {
        return cm;
    }

    public void setCm(Comment cm) {
        this.cm = cm;
    }

    public List<Comment> getcList() {
        return cList;
    }

    public void setcList(List<Comment> cList) {
        this.cList = cList;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public PostViews getPv() {
        return pv;
    }

    public void setPv(PostViews pv) {
        this.pv = pv;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShowAddress() {
        return showAddress;
    }

    public void setShowAddress(String showAddress) {
        this.showAddress = showAddress;
    }

    public List<ProductOrderDTO> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(List<ProductOrderDTO> dispatchList) {
        this.dispatchList = dispatchList;
    }

    public Orders getOrd() {
        return ord;
    }

    public void setOrd(Orders ord) {
        this.ord = ord;
    }

    public List<ProductOrderDTO> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrderDTO> productOrders) {
        this.productOrders = productOrders;
    }

    public String blogDate(Date value) {

        SimpleDateFormat s = new SimpleDateFormat("MMMMM dd, yyyy");
        SimpleDateFormat sd = new SimpleDateFormat("hh:mm aaa");
        String time = sd.format(value);
        String date = s.format(value);
        return date.concat(" at ").concat(time);
    }

    public String suspend(Post p) {

        ev.suspendPost(p);

        return "manage_post";
    }

    public String editPost() {

        ev.editPost(logc.getP());
        return "manage_post";
    }

    public String createSection() {
        ev.createSection(sec);
        return "sections";
    }

    public String editAction(Section s) {
        s.setEditable(true);

        return null;
    }

    public String saveAction(Section s) {
        ev.updateSection(s);

        return "sections";
    }

    public String viewPost(Post p) {
        pv.setPostId(p);
        ev.recordView(pv);

        return "/pages/view_post";
    }

    public String createProduct() {
        Category c = this.ev.findCat(catid);
        ev.createProduct(p, part, c);

        return "manage_product";
    }

    public String editAct(Product p) {
        hideColumn = Boolean.TRUE;
        p.setEditable(Boolean.TRUE);
        return null;
    }

    public String saveAct(Product p) {

        ev.editProduct(p);
        return "manage_product";
    }

    public String viewItems() {

        return "place_order?faces-redirect=true";
    }

    public void stateListener() {

        showAddress = state;
    }

    public String checkout() {

        try{
         EmailHandler eh = new EmailHandler(new EmailDTO(ord.getEmail()));
        
       
       
        
        Map<Product, Integer> cartItems = logc.getCartItems();
        ord.setState(state);
        this.ev.checkout(cartItems, ord);

        this.logc.cartItems.clear();

        context = FacesContext.getCurrentInstance();
        context.addMessage(component.getClientId(), new FacesMessage("Your order has been received ,a mail will be sent to you shortly"));
        return "";
        }catch(Exception no){
           
            context = FacesContext.getCurrentInstance();
        context.addMessage(component.getClientId(), new FacesMessage(no.getMessage()));
        return "";
        }
    }

    public void downloadInvoice(ProductOrderDTO p) {
        try {
            byte[] inv = ev.downloadInvoice(p.getOrder_id());
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse resp = (HttpServletResponse) fc.getExternalContext().getResponse();
            resp.setContentType("application/force-download");
            resp.setHeader("Content-Transfer-Encoding", "binary");
            resp.setHeader("Content-Disposition", "attachment; filename=eves_inv" + p.getOrder_id() + new Date().getTime() + ".pdf");;
            ServletOutputStream os = resp.getOutputStream();
            os.write(inv);
            os.flush();

            fc.responseComplete();
        } catch (IOException no) {
            no.printStackTrace();

        }

    }

    public List comments(Post p) {

        return ev.getComments(p);
    }

    public String changeStatus(ProductOrderDTO po) {
        this.ev.changeStatus(po.getOrder_id());
        return "orders";
    }

    public String comment() {
        Post p = this.logc.getP();
        ev.comment(p, cm);
        return "view_post";
    }

    public void createCat() {

        categories = ev.createCat();

    }

    public void showEditCat(Category c) {

        c.setEditable(true);
    }

    public String saveCat(Category c) {
        ev.modifyCat(c);

        return "manage_product";
    }

    public String leaveComment() {
        Product pr = this.logc.getProduct();
        this.ev.leaveComment(prm, pr);
        return "pr_item";
    }

    public List<ProductRemarks> remarkList(Product p) {


        return ev.getRemarkList(p);

    }

    public List<Comment> getAllComments() {
        return ev.getAllComments();
    }

    public List<ProductRemarks> getAllRemarks() {
        return ev.getAllRemarks();
    }

    public String deletePost(Comment c) {
        ev.deleteComment(c);
        return "";
    }

    public String deleteRemark(ProductRemarks p) {
        ev.deleteRemark(p);
        return "";
    }
    
    
    public int getTotalActivePost(){
        return ev.getTotalActivePosts().intValue();
    }
    
    public List<Post>getSimilarPost(Post p){
        
        return ev.getSimilarPost(p);
    }
    
    
}
