/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import bl.EveEJB;
import ent.Section;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author johnson3yo
 */
@FacesConverter(value="sect")
public class SectionConverter implements Converter {

    @EJB EveEJB ev;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return  (Section)ev.getSection(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      return value.toString();
    }
    
}
