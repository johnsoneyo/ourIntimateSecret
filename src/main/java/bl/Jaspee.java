/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import ent.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author johnson3yo
 */
@WebServlet("/jp")
public class Jaspee extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Product> pl = new ArrayList();
        pl.add(new Product(34, "johnson eyo"));
        pl.add(new Product(32, "evioghene omorobe"));
        pl.add(new Product(31, "cyril eyo"));

        try {
            JasperReport jasperReport = null;
            JasperPrint jasperPrint = null;
            JasperDesign jasperDesign = null;
            Map parameters = new HashMap();
            jasperDesign = JRXmlLoader.load("/home/johnson3yo/Desktop/shop/report1.jrxml");
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(pl));
            //  JasperExportManager.exportReportToPdfFile(jasperPrint,"/home/johnson3yo/Desktop/shop/report1.pdf");
            byte[] val = JasperExportManager.exportReportToPdf(jasperPrint);
            resp.setContentType("application/force-download");
            resp.setHeader("Content-Transfer-Encoding", "binary");
            resp.setHeader("Content-Disposition", "attachment; filename=rec.pdf");;
            ServletOutputStream os = resp.getOutputStream();
            os.write(val);
            os.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
