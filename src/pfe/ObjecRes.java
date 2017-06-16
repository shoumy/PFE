/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfe;

import java.io.File;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Abderrahmane
 */
public class ObjecRes {

    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty nom = new SimpleStringProperty("");
    private SimpleStringProperty methode = new SimpleStringProperty("");
    private SimpleStringProperty dateExec = new SimpleStringProperty("");
    private File file ; 

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ObjecRes(String id, String x, String p, String v) {
        setId(id);
        setNom(x);
        setDateExec(v);
        setMethode(p);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setNom(String x) {
        this.nom.set(x);
    }

    public void setMethode(String p) {
        this.methode.set(p);
    }

    public void setDateExec(String v) {
        this.dateExec.set(v);
    }

    public String getId() {
        return id.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getMethode() {
        return methode.get();
    }

    public String getDateExec() {
        return dateExec.get();
    }

}
