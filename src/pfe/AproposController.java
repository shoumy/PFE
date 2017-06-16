package pfe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abderrahmane
 */
public class AproposController implements Initializable {
    

    @FXML
    private Button closeButton;
    
    @FXML
    public void Quitter(ActionEvent e) {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }
}
