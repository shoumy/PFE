package pfe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 *
 * @author Abderrahmane
 */
public class main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PFE.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("logocuba.png"));
        stage.getIcons().add(icon);
        stage.setTitle("CuBa");
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
