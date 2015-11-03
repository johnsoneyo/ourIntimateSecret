/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import com.crowninteractive.anote.EmailValidator;

/**
 *
 * @author johnson3yo
 */
public class EmailDTO {

    @EmailValidator
    private String email;
    public EmailDTO(String email) {
        this.email= email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
