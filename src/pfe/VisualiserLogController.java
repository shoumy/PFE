package pfe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import static pfe.PFEController.Dialog;

/**
 * FXML Controller class
 *
 * @author Abderrahmane
 */
public class VisualiserLogController implements Initializable {

    @FXML
    private TableView<ObjecRes> tableView;
    @FXML
    private Button acceuil;
    File[] listOfFiles;
    ObjecRes rowData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            File folder = new File("Historique");
            listOfFiles = folder.listFiles();
            tableView.setRowFactory(tv -> {
                TableRow<ObjecRes> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty()) {
                        rowData = row.getItem();
                        Util.result = rowData.getFile();
                        URL url2 = getClass().getResource("LogRes.fxml");
                        Util.openNewWindow(acceuil, url2);

                    }
                });
                return row;
            });

            ObservableList<ObjecRes> data = tableView.getItems();
            data.clear();
            String s1, s2, s3, date, heure;
            for (int i = 0; i < listOfFiles.length; i++) {
                StringTokenizer st = new StringTokenizer(listOfFiles[i].getName(), "-");
                s1 = st.nextToken();
                s2 = st.nextToken();
                s3 = st.nextToken().replace(' ', ':');
                date = s3.substring(0, 10);
                date = date.replace(':', '/');
                heure = s3.substring(11, s3.length() - 5);
                ObjecRes ob = new ObjecRes(Integer.toString(i + 1), s1, s2, date + " " + heure);
                ob.setFile(listOfFiles[i]);
                data.add(ob);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
