package pfe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class ResultatController implements Initializable {

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
    TextField P_A;
    @FXML
    TextField P_E;

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
            ln.setTextFill(Color.web("#000000"));            
            ln.setText("");
            String SNBE = NBE.getText(), SNBO = NBO.getText(), SMAX = MAX.getText(), SPA = P_A.getText(), SCA = CA.getText(), SPE = P_E.getText();
            if (SNBE.equals("") || SNBO.equals("") || SMAX.equals("") || SPA.equals("") || SCA.equals("") || SPE.equals("")) {
                ln.setTextFill(Color.web("#FF0000"));
                ln.setText("Erreur champ vide ");
            }else if ( Integer.parseInt(SNBO) > Integer.parseInt(SNBE)|| Integer.parseInt(SNBE) < 0 || Integer.parseInt(SNBO) < 0|| Integer.parseInt(SMAX) < 0 ||Integer.parseInt(SCA) < 0 || Double.parseDouble(SPA) < 0 || Double.parseDouble(SPA) > 1|| Double.parseDouble(SPE) < 0 || Double.parseDouble(SPE) > 1) {
                ln.setTextFill(Color.web("#FF0000"));
                ln.setText("valeurs incohérentes ");
            }else if(Integer.parseInt(SNBO)>256||Integer.parseInt(SNBE)>256){
                ln.setTextFill(Color.web("#FF0000"));
                ln.setText("valeurs depassent les limites ");
            }
            else{
                Util.handleButtonLancerSequentiel(SNBE,SNBO,SMAX,SCA,SPA,SPE, tableView, lexec, lval,ln,lw, fileName, pidicateur);
            }
        } catch (Exception e) {
            ln.setTextFill(Color.web("#FF0000"));
            ln.setText("valeurs non numérique");
        }
    }

    @FXML
    private void handleButtonPrecedent(ActionEvent event) throws IOException {
        URL url = getClass().getResource("ChoixMethode.fxml");
        Util.openNewWindow(acceuil, url);
    }
}
