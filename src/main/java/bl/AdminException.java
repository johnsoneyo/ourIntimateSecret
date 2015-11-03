/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

/**
 *
 * @author johnson3yo
 */
public class AdminException extends Exception {

    private String string;
    
    public AdminException(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
    
  
    
}
