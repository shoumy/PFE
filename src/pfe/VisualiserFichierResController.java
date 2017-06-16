package pfe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abderrahmane
 */
public class VisualiserFichierResController implements Initializable {

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
    private Label lp;
    @FXML
    private HBox hbox;
    @FXML
    private Button acceuil;
    private String fileName;
    @FXML
    ProgressIndicator pidicateur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    @FXML
    private void handleButtonCharger(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFile = new FileChooser.ExtensionFilter("Fichiers ukp (*.fapr)", "*.fapr");
        fileChooser.getExtensionFilters().add(extFile);
        fileChooser.setInitialDirectory(new File("./Historique"));
        fileChooser.setTitle("Ouvrir le benchmark");
        Stage stage = (Stage) hbox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            fileName = selectedFile.getAbsolutePath();
            String filen = selectedFile.getName();
            lcharge.setText(filen + " chargé");
            lcharge.setTextFill(Color.web("#609000"));
            pidicateur.setVisible(true);
            pidicateur.setProgress(1);
            BufferedReader br = null;
            ObservableList<ObjecBTS> data = tableView.getItems();
            data.clear();
            try {
                String sCurrentLine;
                br = new BufferedReader(new FileReader(selectedFile));
                sCurrentLine = br.readLine();
                lp.setText(sCurrentLine);
                sCurrentLine = br.readLine();
                StringTokenizer st2 = new StringTokenizer(sCurrentLine);
                ln.setText("nb cells:"+st2.nextToken()+" nb arrets:"+st2.nextToken());
                lw.setText("nb freq:"+st2.nextToken()+" nb trx max:"+st2.nextToken());
                sCurrentLine = br.readLine();
                lval.setText(sCurrentLine);                
                sCurrentLine = br.readLine();
                lexec.setText(sCurrentLine + " secondes");                
                String id, d, f;
                while ((sCurrentLine = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(sCurrentLine);
                    id = st.nextToken();
                    d = st.nextToken();
                    f = st.nextToken("");
                    data.add(new ObjecBTS(id, d, f));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
