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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abderrahmane
 */
public class ChoixMethodeController implements Initializable {

    @FXML
    private Button acceuil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleButtonSequentiel(ActionEvent event) throws IOException {
        URL url = getClass().getResource("Resultat.fxml");
        Util.openNewWindow(acceuil, url);
    }

    @FXML
    private void handleButtonResoudreStr1(ActionEvent event) throws IOException {
        URL url = getClass().getResource("Str1.fxml");
        Util.openNewWindow(acceuil, url);
    }

    @FXML
    private void handleButtonResoudreStr2(ActionEvent event) throws IOException {
        URL url = getClass().getResource("Str2.fxml");
        Util.openNewWindow(acceuil, url);
    }
    
        @FXML
    private void handleButtonResoudreStr3(ActionEvent event) throws IOException {
        URL url = getClass().getResource("Str3.fxml");
        Util.openNewWindow(acceuil, url);
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

}
