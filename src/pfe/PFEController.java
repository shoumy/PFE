package pfe;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.HostServices;
/**
 *
 * @author Abderrahmane
 */
public class PFEController implements Initializable {

    @FXML
    public BorderPane BorderPane = new BorderPane();
    public static Stage Dialog = new Stage();

    @FXML
    private void handleButtonResoudre(ActionEvent event) throws IOException {
        FXMLLoader f = openNewWindow("ChoixMethode.fxml");
    }

    @FXML
    private void handleButtonVisualiserRes(ActionEvent event) throws IOException {
        FXMLLoader f = openNewWindow("VisualiserFichierRes.fxml");
    }

    @FXML
    private void handleButtonapropos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("apropos.fxml"));
        Scene scene = new Scene(root);
        Dialog.setScene(scene);
        Dialog.centerOnScreen();
        Dialog.show();
        Dialog.setResizable(false);
    }
    
    @FXML
    private void handleButtonManuel(ActionEvent event) throws IOException {
       if (Desktop.isDesktopSupported()) {
    try {
            File myFile = new File("Manuel.pdf");
            Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
            // no application registered for PDFs
        }
    }
    }

    @FXML
    private void handleButtonGenerer(ActionEvent event) throws IOException {
        FXMLLoader f = openNewWindow("GenererBench.fxml");
    }

    @FXML
    private void handleButtonLog(ActionEvent event) throws IOException {
        FXMLLoader f = openNewWindow("VisualiserLog.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void closeButtonAction() {
        System.exit(0);
    }

    public FXMLLoader openNewWindow(String FXMLFile) {
        Node page;
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            URL url = getClass().getResource(FXMLFile);
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            page = (Pane) fxmlLoader.load(url.openStream());
            BorderPane.setCenter(page);
        } catch (IOException e) {
        }
        return fxmlLoader;
    }

}
