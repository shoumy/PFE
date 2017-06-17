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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abderrahmane
 */
public class LogResController implements Initializable {

    private File file;
    @FXML
    private TableView<ObjecBTS> tableView;
    @FXML
    private Label lexec;
    @FXML
    private Label lpoid;
    @FXML
    private Label lval;
    @FXML
    private Label lcalc;
    @FXML
    private Label lw;
    @FXML
    private Label ln;
    @FXML
    private Label lm;
    @FXML
    private Label lparam;
    @FXML
    private Label ldate;
    @FXML
    private Label lins;
    @FXML
    private HBox hbox;
    private String fileName;
    @FXML
    ProgressIndicator pidicateur;
    @FXML
    private Button acceuil;

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
    private void handleButtonPrecedent(ActionEvent event) throws IOException {
        URL url = getClass().getResource("VisualiserLog.fxml");
        Util.openNewWindow(acceuil, url);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        file = Util.result;
        if (file != null) {
            StringTokenizer st2 = new StringTokenizer(file.getName(), "-");

            String s1 = st2.nextToken();
            lins.setText("Instance: " + s1);
            String s2 = st2.nextToken();
            lm.setText("Methode: " + s2);
            String s3 = st2.nextToken().replace(' ', ':');
            String date = s3.substring(0, 10);
            date = date.replace(':', '/');
            String heure = s3.substring(11, s3.length() - 5);
            ldate.setText("Date: " + date + " " + heure);

            fileName = file.getAbsolutePath();
            String filen = file.getName();
            pidicateur.setVisible(true);
            pidicateur.setProgress(1);
            BufferedReader br = null;
            ObservableList<ObjecBTS> data = tableView.getItems();
            data.clear();
            try {
                String sCurrentLine;
                br = new BufferedReader(new FileReader(file));
                sCurrentLine = br.readLine();
                lparam.setText("params:"+sCurrentLine);
                sCurrentLine = br.readLine();
                StringTokenizer st3 = new StringTokenizer(sCurrentLine);
                
                ln.setText("nb cells:"+st3.nextToken()+" nb arrets:"+st3.nextToken());
                lw.setText("nb freq:"+st3.nextToken()+" nb trx max:"+st3.nextToken());
                sCurrentLine = br.readLine();
                lval.setText(sCurrentLine);
                sCurrentLine = br.readLine();
                lexec.setText(sCurrentLine + " secondes");               
                System.out.println("ici1");
                String id, d, f;
                
                while ((sCurrentLine = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(sCurrentLine);
                    id = st.nextToken();
                    d = st.nextToken();
                    f = st.nextToken("");
                    data.add(new ObjecBTS(id, d, f));
                }
                
                System.out.println("ici1");
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
