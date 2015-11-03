/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import bl.AdminException;
import bl.EveEJB;
import ent.Admin;
import ent.Category;
import ent.Post;
import ent.PostViews;
import ent.Product;
import ent.ProductOrderDTO;
import ent.Section;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author johnson3yo
 */
@ManagedBean
@SessionScoped
public class AdminController {

    @EJB
    EveEJB ev;
    private List<Post> sectionPostList = new ArrayList();
    private ent.PostViews pv = new ent.PostViews();
    private List<Entry<Product, Integer>> entries;
    private double total = 0.0;
    private String state;
    private boolean showAddress;
    private List<ProductOrderDTO> dispatchList = new ArrayList();
    private boolean logged;
    private FacesContext context;
    private UIComponent component;
    private Admin adm = new Admin();
    Map<Product, Integer> cartItems = new HashMap<Product, Integer>();
    private int item;
    private Post p = new Post();
    private Product product = new Product();
    private Category c = new Category();
    
    public Post getP() {
        return p;
    }

    public void setP(Post p) {
        this.p = p;
    }

    public Category getC() {
        return c;
    }

    public void setC(Category c) {
        this.c = c;
    }

    
    
    public String editPostScreen(Post p) {
        this.p = p;
        return "edit_post";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    

    public List<Post> getSectionPostList() {
        return sectionPostList;
    }

    public void setSectionPostList(List<Post> sectionPostList) {
        this.sectionPostList = sectionPostList;
    }

    public PostViews getPv() {
        return pv;
    }

    public void setPv(PostViews pv) {
        this.pv = pv;
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Product, Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ProductOrderDTO> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(List<ProductOrderDTO> dispatchList) {
        this.dispatchList = dispatchList;
    }

    public Admin getAdm() {
        return adm;
    }

    public void setAdm(Admin adm) {
        this.adm = adm;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public String viewPost(Post p) {
        
        PostViews pv = new PostViews();
        pv.setPostId(p);
        ev.recordView(pv);
        this.p = p;
        return "view_post";
    }

    public String viewSection(Section s) {

        sectionPostList = s.getPostList();


        return "section_post?faces-redirect=true";
    }

    public List<Entry<Product, Integer>> getEntries() {

        return entries = new ArrayList<Entry<Product, Integer>>(cartItems.entrySet());
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String addToCart(Product p, int item) {

        cartItems.put(p, item);
        total = getPriceUpdate(cartItems);

        return "products";
    }

    public String addItemToCart(Product p, int item) {


        cartItems.put(p, item);
        total = getPriceUpdate(cartItems);
        return "";
    }

    public String removeFromCart(Product p) {
        int get = cartItems.get(p);

        cartItems.remove(p);

        total = getPriceUpdate(cartItems);

        return "place_order";
    }

    public String updateCart(Product p) {
        cartItems.put(p, item);

        total = getPriceUpdate(cartItems);

        return "";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double subTotal(Product p) {

        Integer i = (Integer) cartItems.get(p);
        return p.getPrice() * i;
    }

    public double getPriceUpdate(Map<Product, Integer> m) {
        double total = 0.0;

        for (Map.Entry<Product, Integer> entry : m.entrySet()) {
            total = total + entry.getKey().getPrice() * entry.getValue();
        }

        return total;
    }

    public boolean isShowAddress() {
        return showAddress;
    }

    public void setShowAddress(boolean showAddress) {
        this.showAddress = showAddress;
    }

    public String checkout() {


        return "new_order";
    }

    public String addToDispatch(ProductOrderDTO po) {
        dispatchList.add(po);

        System.out.println("S added " + po.getCustomer_name());
        return "orders";
    }

    public void downloadDispatch() {

        byte[] val = ev.downloadDispatch(dispatchList);
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse resp = (HttpServletResponse) fc.getExternalContext().getResponse();
            resp.setContentType("application/force-download");
            resp.setHeader("Content-Transfer-Encoding", "binary");
            resp.setHeader("Content-Disposition", "attachment; filename=dispatch_eve_intimate" + new Date().getTime() + ".pdf");;
            ServletOutputStream os = resp.getOutputStream();
            os.write(val);
            os.flush();

            fc.responseComplete();
        } catch (IOException no) {
            no.printStackTrace();

        }


    }

    public String refreshDispatch() {

        dispatchList.clear();
        return "orders";
    }

    public String login() {
        try {
            ev.login(adm);
            logged = Boolean.TRUE;
            return "/pages/admin_home";
        } catch (AdminException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            context = FacesContext.getCurrentInstance();
            context.addMessage(component.getClientId(), new FacesMessage(ex.getString()));
            logged = Boolean.FALSE;
            return "";
        }

    }
    
    public boolean catEquals(Category cat){
        
       if(cat.equals(c)){
           return true;
       }else return false;
    }

    public String logout() {
        context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        logged = Boolean.FALSE;
        System.out.println("CAlled bitch");
        return "/w/index.xhtml?faces-redirect=true";
    }

    public String viewItems(Product p) {
       this.product = p;
        return "pr_item";
    }
    
    public String viewcat(Category c){
        this.c = c;
        System.out.println("Category "+c.getName());
        return "product_cat?faces-redirect=true";
    }
}
