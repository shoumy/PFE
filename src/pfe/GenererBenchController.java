package pfe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abderrahmane
 */
public class GenererBenchController implements Initializable {

    @FXML
    private Label lerreur;
    @FXML
    private Label bname;
    @FXML
    private TextField NF;
    @FXML
    private TextField NbTrxMin;
    @FXML
    private TextField NbTrxMax;
    @FXML
    private TextField fpmin;
    @FXML
    private TextField fpmax;

    @FXML
    private Button sauv;
    @FXML
    private Button acceuil;
    @FXML
    private Button bcharger;
    @FXML
    ProgressIndicator pidicateur;
    @FXML
    private HBox hbox;
    
    File fileCol;

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
        fileChooser.setInitialDirectory(new File("./color03"));
        FileChooser.ExtensionFilter extFile = new FileChooser.ExtensionFilter("Fichiers col (*.col)", "*.col");
        fileChooser.getExtensionFilters().add(extFile);
        fileChooser.setTitle("Ouvrir le benchmark");
        Stage stage = (Stage) hbox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String fileName = selectedFile.getAbsolutePath();
            fileCol=selectedFile;
            bname.setText(selectedFile.getName() + " chargé");
            //lcharge.setTextFill(Color.web("#609000"));
            //bcal.setDisable(false);
        }
    }

    @FXML
    private void handleButtonSauv(ActionEvent event) throws IOException {
        String snf, strxmin, strxmax, sfpmin, sfpmax,data = "";
        int lnf, ltrxmin, ltrxmax, lfpmin, lfpmax,fmin,fmax,nbcel,ntrx;
        long nbarret=0;
        long ndi=0 ;
        lerreur.setText("");
        try {
            snf = NF.getText();
            strxmin = NbTrxMin.getText();
            strxmax = NbTrxMax.getText();
            sfpmin = fpmin.getText();
            sfpmax = fpmax.getText();

            lnf = Integer.parseInt(snf);
            ltrxmin = Integer.parseInt(strxmin);
            ltrxmax = Integer.parseInt(strxmax);
            lfpmin = Integer.parseInt(sfpmin);
            lfpmax = Integer.parseInt(sfpmax);
            
            BufferedReader br = null;
            if (lfpmin > lfpmax || ltrxmin > ltrxmax) {
                lerreur.setText("max doit etre >=min");
            } else {
               // pidicateur.setVisible(true);
               // pidicateur.setProgress(-1);                
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("./benchmarks"));
                fileChooser.setInitialFileName(fileCol.getName()+"_"+snf+"_"+strxmax+"_"+sfpmin+"_"+sfpmax);
                fileChooser.setTitle("Sauvgarder le benchmark");
                FileChooser.ExtensionFilter extFile = new FileChooser.ExtensionFilter("Fichiers fap (*.fap)", "*.fap");
                fileChooser.getExtensionFilters().add(extFile);
                Stage stage = (Stage) hbox.getScene().getWindow();
                Random randomGenerator = new Random();

                File selectedFile = fileChooser.showSaveDialog(stage);
                String tmp = "";
                if (selectedFile != null) {
                    FileWriter fileWriter = null;
                    BufferedWriter bw = null;
                    fileWriter = new FileWriter(selectedFile.getAbsolutePath());
                    bw = new BufferedWriter(fileWriter);
                    String content ="";
                    String sCurrentLine;
                    br = new BufferedReader(new FileReader(fileCol));
                    sCurrentLine = br.readLine();
                    while(sCurrentLine.charAt(0)=='c'){
                        bw.write(sCurrentLine+"\n");
                        sCurrentLine = br.readLine();
                    }
                    content="";
                    if(sCurrentLine.charAt(0)=='p'){                
                        String values[] = sCurrentLine.trim().split(" "); 
                        nbcel=Integer.parseInt(values[2]);
                        nbarret=Long.parseLong(values[3]);
                        content = content.concat("p "+nbcel+" "+nbarret+" "+30+" "+(30+lnf)+" "+ltrxmax+"\n\n");
                        bw.write(content);
                        for (long i = 1; i < nbcel+1; i++) {
                            content="";
                            ntrx=ltrxmin+randomGenerator.nextInt((ltrxmax-ltrxmin)+1);
                            ndi=ndi+ntrx;
                            int nbp=lfpmin+randomGenerator.nextInt((lfpmax-lfpmin));
                            content = content.concat(i+" "+ntrx);
                            for (int j = 0; j < nbp; j++) {
                                content = content.concat(" ");
                                int f=30+randomGenerator.nextInt((lnf));
                                content = content.concat(String.valueOf(f));
                            }                                
                            content = content.concat("\n");
                            bw.write(content);
                        }
                    }
                    bw.write("\n");
                    content="";
                    sCurrentLine = br.readLine();
                    for (long i = 0; i < nbarret; i++) {                 
                        bw.write(sCurrentLine+"\n");
                        sCurrentLine = br.readLine();
                    }
                    System.out.println(ndi);
                    bw.close();
                    fileWriter.close();
                    lerreur.setText("done");
                    //pidicateur.setProgress(1);
                }
            }
        } catch (IOException ex) {
           lerreur.setText(ex.getMessage());
        } catch (Exception e) {
            lerreur.setText(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {

    }
}
