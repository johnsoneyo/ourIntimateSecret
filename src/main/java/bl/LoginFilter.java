/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;



import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mb.AdminController;

/**
 *
 * @author johnson3yo
 */
@WebFilter(urlPatterns = {"/w/pages/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

       AdminController loginBean = (AdminController) session.getAttribute("adminController");

        try {
            if (!loginBean.isLogged() || loginBean == null) {
                response.sendRedirect(request.getContextPath());
                
            } else {
                chain.doFilter(request, response);
            }
        } catch (NullPointerException no) {
          
            response.sendRedirect(request.getContextPath()+"/w/login.xhtml");
        }
    }

    @Override
    public void destroy() {
    }
}
