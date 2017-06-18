package pfe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abderrahmane
 */
public class Str2Controller implements Initializable {

    @FXML
    private TableView<ObjecBTS> tableView;
    @FXML
    private Label lexec;
    @FXML
    private Label lpoid;
    @FXML
    private Label lval;
    @FXML
    private Label lcharge;
    @FXML
    private Label lcalc;
    @FXML
    private Label lw;
    @FXML
    private Label ln;
    @FXML
    private HBox hbox;
    @FXML
    private Button bcal;
    @FXML
    private Button acceuil;
    private String fileName;
    @FXML
    ProgressIndicator pidicateur;
    @FXML
    TextField NBE;
    @FXML
    TextField NBO;
    @FXML
    TextField MAX;
    @FXML
    TextField CA;
    @FXML
    TextField P_A_MIN;
    @FXML
    TextField P_E_MIN;
    @FXML
    TextField P_A_MAX;
    @FXML
    TextField P_E_MAX;
    @FXML
    TextField NBB;
    @FXML
    TextField NBI;

    @FXML
    private void handleButtonCharger(ActionEvent event) throws IOException {
        fileName = Util.handleButtonCharger(hbox, lcharge, pidicateur, bcal);
        ln.setText("");
        lw.setText("");
        lexec.setText("");
        lval.setText("");
        for ( int i = 0; i<tableView.getItems().size(); i++) {
            tableView.getItems().clear();
        }
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);
        
    }

    @FXML
    private void handleButtonAcceuil(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage) acceuil.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("PFE.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pidicateur.setVisible(false);

    }

    @FXML
    private void handleButtonLancer(ActionEvent event) throws IOException {
        try {
            ln.setTextFill(Color.web("#ffffff"));            
            ln.setText("");
            
            String SNBB=NBB.getText(),SNBI=NBI.getText(), SMAX = MAX.getText(), SPAMIN = P_A_MIN.getText(), SPEMIN = P_E_MIN.getText(), SPAMAX = P_A_MAX.getText(), SPEMAX = P_E_MAX.getText(), SCA = CA.getText();
            int inbe;
            inbe=Integer.parseInt(SNBB)*Integer.parseInt(SNBI);
            String SNBO=Integer.toString(inbe) ,SNBE=SNBO;
            if (SNBB.equals("") ||SNBI.equals("") ||SNBE.equals("") || SNBO.equals("") || SMAX.equals("") || SCA.equals("") ||SPAMIN.equals("") || SPEMIN.equals("")||SPAMAX.equals("") || SPEMAX.equals("")) {
                ln.setTextFill(Color.web("#FF0000"));
                ln.setText("Erreur champ vide ");
            }else if (Integer.parseInt(SNBB)<0||Integer.parseInt(SNBI)<0|| Integer.parseInt(SNBO) > Integer.parseInt(SNBE)|| Integer.parseInt(SNBE) < 0 || Integer.parseInt(SNBO) < 0|| Integer.parseInt(SMAX) < 0 ||Integer.parseInt(SCA) < 0 || Double.parseDouble(SPAMIN) < 0 || Double.parseDouble(SPAMIN) > 1|| Double.parseDouble(SPEMIN) < 0 || Double.parseDouble(SPEMIN) > 1|| Double.parseDouble(SPAMAX) < 0 || Double.parseDouble(SPAMAX) > 1|| Double.parseDouble(SPEMAX) < 0 || Double.parseDouble(SPEMAX) > 1|| Double.parseDouble(SPEMAX) > 1|| Double.parseDouble(SPEMIN) > Double.parseDouble(SPEMAX)|| Double.parseDouble(SPAMIN) > Double.parseDouble(SPAMAX)) {
                ln.setTextFill(Color.web("#FF0000"));
                ln.setText("Erreur valeurs incohérentes ");
            }else if(Integer.parseInt(SNBB)>8||Integer.parseInt(SNBO)>256||Integer.parseInt(SNBE)>256){
                ln.setTextFill(Color.web("#FF0000"));
                ln.setText("Erreur valeurs depassent les limites ");
            }
            else{
                Util.handleButtonLancerSt2(SNBE,SNBO,SMAX,SCA,SNBB,SNBI,SPEMIN,SPEMAX,SPAMIN,SPAMAX,tableView, lexec, lval,ln,lw, fileName, pidicateur);
            }
        } catch (Exception e) {
            ln.setTextFill(Color.web("#FF0000"));
            ln.setText("Erreur valeurs non numérique");
        }
    }

    @FXML
    private void handleButtonPrecedent(ActionEvent event) throws IOException {
        URL url = getClass().getResource("ChoixMethode.fxml");
        Util.openNewWindow(acceuil, url);
    }
}
