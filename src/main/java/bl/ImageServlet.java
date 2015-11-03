/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author johnson3yo
 */
@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    @EJB EveEJB ev;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String post_id = req.getParameter("post_id");
    
        byte[]val =  ev.getPostImage(post_id);
          ServletOutputStream os = resp.getOutputStream();
        
        os.write(val);
        os.flush();
    }
    
    
}
