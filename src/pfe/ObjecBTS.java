    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfe;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Abderrahmane
 */
public class ObjecBTS {

    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty d = new SimpleStringProperty("");
    private SimpleStringProperty f = new SimpleStringProperty("");

    public ObjecBTS(String id, String d, String f) {
        setId(id);
        setD(d);
        setF(f);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setD(String d) {
        this.d.set(d);
    }

    public void setF(String f) {
        this.f.set(f);
    }

    public String getId() {
        return id.get();
    }

    public String getD() {
        return d.get();
    }

    public String getF() {
        return f.get();
    }

}
