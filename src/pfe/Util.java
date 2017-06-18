package pfe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Abderrahmane
 */
public class Util {

    static public File result;

    static public FXMLLoader openNewWindow(Button acceuil, URL url) {
        Node page;
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            page = (Pane) fxmlLoader.load(url.openStream());
            BorderPane b = (BorderPane) acceuil.getScene().getRoot();
            b.setCenter(page);
        } catch (IOException e) {
        }
        return fxmlLoader;
    }

    static public void handleButtonLancer(String methode, TableView<ObjecBTS> tableView, Label lexec, Label lpoid, Label lval, Label lw, Label ln, String fileName, ProgressIndicator pidicateur) {
        Path path = Paths.get(fileName);
        StringTokenizer st2 = new StringTokenizer(path.getFileName().toString(), ".");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date();
        String fichierRes = "Historique/" + st2.nextToken() + "-" + methode + "-" + dateFormat.format(date) + ".ukpr";
        File file = new File(fichierRes);
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    Process process = new ProcessBuilder("Executable/" + methode + ".exe", fileName, fichierRes).start();
                } catch (IOException ex) {
                    Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean exists = file.exists();
        while (!exists) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
            exists = file.exists();
        }
        pidicateur.setProgress(1);
        BufferedReader br = null;
        ObservableList<ObjecBTS> data = tableView.getItems();
        data.clear();
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(fichierRes));
            sCurrentLine = br.readLine();
            ln.setText("Nombre d'objets: " + sCurrentLine);
            sCurrentLine = br.readLine();
            lw.setText("Capacité du sac: " + sCurrentLine);
            sCurrentLine = br.readLine();
            lexec.setText(sCurrentLine + " secondes");
            sCurrentLine = br.readLine();
            lval.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            lpoid.setText(sCurrentLine);
            String id, x, p, v;
            while ((sCurrentLine = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(sCurrentLine);
                id = st.nextToken();
                x = st.nextToken();
                p = st.nextToken();
                v = st.nextToken();
               // data.add(new ObjecBTS(id, x, p, v));
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

    static public void handleButtonLancer2(String methode, String p3, String p4, String p5, String p6, String p7, String p8, String p9, String p10, TableView<ObjecBTS> tableView, Label lexec, Label lpoid, Label lval, Label lw, Label ln, Label ln2, Label ln3, String fileName, ProgressIndicator pidicateur) {
        Path path = Paths.get(fileName);
        StringTokenizer st2 = new StringTokenizer(path.getFileName().toString(), ".");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date();
        String fichierRes = "Historique/" + st2.nextToken() + "-" + methode + "-" + dateFormat.format(date) + ".ukpr";
        File file = new File(fichierRes);
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    Process process = new ProcessBuilder("Executable/" + methode + ".exe", fileName, fichierRes, p3, p4, p5, p6, p7, p8, p9, p10).start();
                } catch (IOException ex) {
                    Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread2.start();
        boolean exists = file.exists();
        while (!exists) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ex) {
            }
            exists = file.exists();
        }
        pidicateur.setProgress(1);
        BufferedReader br = null;
        ObservableList<ObjecBTS> data = tableView.getItems();
        data.clear();
        try {
            String sCurrentLine;
            String tmp = "P : " + p3 + " genThreshold : " + p4 + " R : " + p5 + " appearThreshold : " + p6 + " initRunThreshold : " + p7 + " PC : "  + p8 + " PM : " + p9 + " s : " + p10 + "\n";
            File mFile = new File(fichierRes);
            BufferedReader br2 = new BufferedReader(new FileReader(fichierRes));
            String result = "";
            String line = "";
            while ((line = br2.readLine()) != null) {
                result = result + line + "\n";
            }

            result = tmp + result;

            mFile.delete();
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(result.getBytes());
            fos.flush();

            br = new BufferedReader(new FileReader(fichierRes));
            sCurrentLine = br.readLine();
            sCurrentLine = br.readLine();
            ln.setText("Nombre d'objets: " + sCurrentLine);
            sCurrentLine = br.readLine();
            lw.setText("Capacité du sac: " + sCurrentLine);
            sCurrentLine = br.readLine();
            lexec.setText(sCurrentLine + " secondes");
            sCurrentLine = br.readLine();
            lval.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            lpoid.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            ln2.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            ln3.setText(sCurrentLine);
            String id, x, p, v;
            while ((sCurrentLine = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(sCurrentLine);
                id = st.nextToken();
                x = st.nextToken();
                p = st.nextToken();
                v = st.nextToken();
               // data.add(new ObjecSac(id, x, p, v));
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

    static public String handleButtonCharger(Pane hbox, Label lcharge, ProgressIndicator pidicateur, Button bcal) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./benchmarks"));
        FileChooser.ExtensionFilter extFile = new FileChooser.ExtensionFilter("Fichiers ukp (*.fap)", "*.fap");
        fileChooser.getExtensionFilters().add(extFile);
        fileChooser.setTitle("Ouvrir le benchmark");
        Stage stage = (Stage) hbox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String fileName = selectedFile.getAbsolutePath();
            String filen = selectedFile.getName();
            lcharge.setText(filen + " chargé");
            lcharge.setTextFill(Color.web("#00FF00"));
            bcal.setDisable(false);
            return fileName;
        }
        return null;
    }

    static void handleButtonLancer(String rechercheTabou, String snmax, String snbitmax, String snbimprouve, String stailleL, TableView<ObjecBTS> tableView, Label lexec, Label lpoid, Label lval, Label lw, Label ln, String fileName, ProgressIndicator pidicateur) {
        Path path = Paths.get(fileName);
        StringTokenizer st2 = new StringTokenizer(path.getFileName().toString(), ".");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date();
        String fichierRes = "Historique/" + st2.nextToken() + "-" + rechercheTabou + "-" + dateFormat.format(date) + ".ukpr";
        File file = new File(fichierRes);
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    Process process = new ProcessBuilder("Executable/" + rechercheTabou + ".exe", fileName, fichierRes, snmax, snbitmax, snbimprouve, stailleL).start();
                } catch (IOException ex) {
                    Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread2.start();
        boolean exists = file.exists();
        while (!exists) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
            exists = file.exists();
        }
        pidicateur.setProgress(1);
        BufferedReader br = null;
        ObservableList<ObjecBTS> data = tableView.getItems();
        data.clear();
        try {

            String sCurrentLine;
            String tmp = "Nmax : " +snmax + " NbitMax : " +snbitmax + " NbImprouv : " + snbimprouve + " TailleL :" + stailleL+"\n";
            File mFile = new File(fichierRes);
            BufferedReader br2 = new BufferedReader(new FileReader(fichierRes));
            String result = "";
            String line = "";
            while ((line = br2.readLine()) != null) {
                result = result + line + "\n";
            }

            result = tmp + result;

            mFile.delete();
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(result.getBytes());
            fos.flush();

            br = new BufferedReader(new FileReader(fichierRes));
            sCurrentLine = br.readLine();
            sCurrentLine = br.readLine();
            ln.setText("Nombre d'objets: " + sCurrentLine);
            sCurrentLine = br.readLine();
            lw.setText("Capacité du sac: " + sCurrentLine);
            sCurrentLine = br.readLine();
            lexec.setText(sCurrentLine + " secondes");
            sCurrentLine = br.readLine();
            lval.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            lpoid.setText(sCurrentLine);
            String id, x, p, v;
            while ((sCurrentLine = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(sCurrentLine);
                id = st.nextToken();
                x = st.nextToken();
                p = st.nextToken();
                v = st.nextToken();
              //  data.add(new ObjecBTS(id, x, p));
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
    
     static void handleButtonLancerSequentiel(String SNBE,String SNBO,String SMAX,String SCA,String SPA,String SPE,TableView<ObjecBTS> tableView, Label lexec, Label lval,Label ln,Label lw,String fileName, ProgressIndicator pidicateur) {
        Path path = Paths.get(fileName);
        StringTokenizer st2 = new StringTokenizer(path.getFileName().toString(), ".");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date();
        String fichierRes = "Historique/" + st2.nextToken()+"."+st2.nextToken()+"."+st2.nextToken() + "-" + "Séquentiele" + "-" + dateFormat.format(date) + ".fapr";
        File file = new File(fichierRes);
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    Process process = new ProcessBuilder("Executable/VersionSequentiel.exe", fileName,SCA,SNBE,SNBO,SMAX,SPE,SPA,fichierRes).start();
                } catch (IOException ex) {
                    Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread2.start();
        try {
            thread2.join();
            System.out.println("join");
        } catch (InterruptedException ex) {
            System.out.println("ici");
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean exists = file.exists();
        System.out.println(exists);
        
        while (!exists) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
            exists = file.exists();
        }
        pidicateur.setProgress(1);
        
        BufferedReader br = null;
        ObservableList<ObjecBTS> data = tableView.getItems();
        data.clear();
        try {
            String sCurrentLine;
            String tmp = SNBE + " " +SNBO + " " + SCA + " " +SMAX+" "+SPE+" "+SPA+"\n";
            File mFile = new File(fichierRes);
            BufferedReader br2 = new BufferedReader(new FileReader(fichierRes));
            String result = "";
            String line = "";
            while ((line = br2.readLine()) != null) {
                result = result + line + "\n";
            }

            result = tmp + result;

            mFile.delete();
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(result.getBytes());
            fos.flush();
            
            br = new BufferedReader(new FileReader(fichierRes));
            br.readLine();
            sCurrentLine = br.readLine();
            StringTokenizer st = new StringTokenizer(sCurrentLine);
            ln.setText("nb cells:"+st.nextToken()+" nb arrets:"+st.nextToken());
            lw.setText("nb freq:"+st.nextToken()+" nb trx max:"+st.nextToken());
            sCurrentLine = br.readLine();
            lval.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            lexec.setText(sCurrentLine + " secondes");
            
            String id, d, f;
            
            while ((sCurrentLine = br.readLine()) != null) {
                st = new StringTokenizer(sCurrentLine);
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

    static void handleButtonLancerStr3(String SNBE, String SNBO, String SMAX, String SCA, String SPA, String SPE, String SNBT, TableView<ObjecBTS> tableView, Label lexec, Label lval,Label ln,Label lw, String fileName, ProgressIndicator pidicateur) {
       Path path = Paths.get(fileName);
        StringTokenizer st2 = new StringTokenizer(path.getFileName().toString(), ".");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date();
        String fichierRes = "Historique/" + st2.nextToken()+"."+st2.nextToken()+"."+st2.nextToken() + "-" + "strategie hybride" + "-" + dateFormat.format(date) + ".fapr";
        File file = new File(fichierRes);
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    Process process = new ProcessBuilder("Executable/StrPopGpuCpuSolTh.exe", fileName,SCA,SNBE,SNBO,SMAX,SPE,SPA,fichierRes,SNBT).start();
                } catch (IOException ex) {
                    Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread2.start();
        try {
            thread2.join();
            System.out.println("join");
        } catch (InterruptedException ex) {
            System.out.println("ici");
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean exists = file.exists();
        System.out.println(exists);
        
        while (!exists) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
            exists = file.exists();
        }
        pidicateur.setProgress(1);
        
        BufferedReader br = null;
        ObservableList<ObjecBTS> data = tableView.getItems();
        data.clear();
        try {
            String sCurrentLine;
            String tmp = SNBE + " " +SNBO + " " + SCA + " " +SMAX+" "+SPE+" "+SPA+"\n";
            File mFile = new File(fichierRes);
            BufferedReader br2 = new BufferedReader(new FileReader(fichierRes));
            String result = "";
            String line = "";
            while ((line = br2.readLine()) != null) {
                result = result + line + "\n";
            }

            result = tmp + result;

            mFile.delete();
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(result.getBytes());
            fos.flush();
            
            br = new BufferedReader(new FileReader(fichierRes));
            br.readLine();
            sCurrentLine = br.readLine();
            StringTokenizer st = new StringTokenizer(sCurrentLine);
            ln.setText("nb cells:"+st.nextToken()+" nb arrets:"+st.nextToken());
            lw.setText("nb freq:"+st.nextToken()+" nb trx max:"+st.nextToken());
            sCurrentLine = br.readLine();
            lval.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            lexec.setText(sCurrentLine + " secondes");
            
            String id, d, f;
            
            while ((sCurrentLine = br.readLine()) != null) {
                st = new StringTokenizer(sCurrentLine);
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

    static void handleButtonLancerSt2(String SNBE, String SNBO, String SMAX, String SCA, String SNBB, String SNBI, String SPEMIN, String SPEMAX, String SPAMIN, String SPAMAX, TableView<ObjecBTS> tableView, Label lexec, Label lval, Label ln, Label lw, String fileName, ProgressIndicator pidicateur) {
        Path path = Paths.get(fileName);
        StringTokenizer st2 = new StringTokenizer(path.getFileName().toString(), ".");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date();
        String fichierRes = "Historique/" + st2.nextToken()+"."+st2.nextToken()+"."+st2.nextToken() + "-" + "Colonie bl Abeille th" + "-" + dateFormat.format(date) + ".fapr";
        File file = new File(fichierRes);
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    Process process = new ProcessBuilder("Executable/StrPopBlockSolth.exe", fileName,SCA,SNBE,SNBO,SMAX,SPEMIN,SPEMAX,SPAMIN,SPAMAX,fichierRes,SNBB,SNBI).start();
                } catch (IOException ex) {
                    Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread2.start();
        try {
            thread2.join();
            System.out.println("join");
        } catch (InterruptedException ex) {
            System.out.println("ici");
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean exists = file.exists();
        System.out.println(exists);
        
        while (!exists) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
            exists = file.exists();
        }
        pidicateur.setProgress(1);
        
        BufferedReader br = null;
        ObservableList<ObjecBTS> data = tableView.getItems();
        data.clear();
        try {
            String sCurrentLine;
            String tmp = SNBE + " " +SNBO + " " + SCA + " " +SMAX+" "+SPEMIN+" "+SPEMAX+" "+SPAMIN+" "+SPAMAX+" "+SNBB+" "+SNBI+"\n";
            File mFile = new File(fichierRes);
            BufferedReader br2 = new BufferedReader(new FileReader(fichierRes));
            String result = "";
            String line = "";
            while ((line = br2.readLine()) != null) {
                result = result + line + "\n";
            }

            result = tmp + result;

            mFile.delete();
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(result.getBytes());
            fos.flush();
            
            br = new BufferedReader(new FileReader(fichierRes));
            br.readLine();
            sCurrentLine = br.readLine();
            StringTokenizer st = new StringTokenizer(sCurrentLine);
            ln.setText("nb cells:"+st.nextToken()+" nb arrets:"+st.nextToken());
            lw.setText("nb freq:"+st.nextToken()+" nb trx max:"+st.nextToken());
            sCurrentLine = br.readLine();
            lval.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            lexec.setText(sCurrentLine + " secondes");
            
            String id, d, f;
            
            while ((sCurrentLine = br.readLine()) != null) {
                st = new StringTokenizer(sCurrentLine);
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

    static void handleButtonLancerSt1(String SNBE, String SNBO, String SMAX, String SCA, String SPA, String SPE, String SNBB, String SNBI, TableView<ObjecBTS> tableView, Label lexec, Label lval, Label ln, Label lw, String fileName, ProgressIndicator pidicateur) {
        Path path = Paths.get(fileName);
        StringTokenizer st2 = new StringTokenizer(path.getFileName().toString(), ".");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date();
        String fichierRes = "Historique/" + st2.nextToken()+"."+st2.nextToken()+"."+st2.nextToken() + "-" + "Colonie gpu Abeille th" + "-" + dateFormat.format(date) + ".fapr";
        File file = new File(fichierRes);
        pidicateur.setVisible(true);
        pidicateur.setProgress(-1);

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    Process process = new ProcessBuilder("Executable/StrPopGpuSolth.exe", fileName,SCA,SNBE,SNBO,SMAX,SPE,SPE,SPA,SPA,fichierRes,SNBB,SNBI).start();
                } catch (IOException ex) {
                    Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread2.start();
        try {
            thread2.join();
            System.out.println("join");
        } catch (InterruptedException ex) {
            System.out.println("ici");
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean exists = file.exists();
        System.out.println(exists);
        
        while (!exists) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
            exists = file.exists();
        }
        pidicateur.setProgress(1);
        
        BufferedReader br = null;
        ObservableList<ObjecBTS> data = tableView.getItems();
        data.clear();
        try {
            String sCurrentLine;
            String tmp = SNBE + " " +SNBO + " " + SCA + " " +SMAX+" "+SPE+" "+SPA+" "+" "+SNBB+" "+SNBI+"\n";
            File mFile = new File(fichierRes);
            BufferedReader br2 = new BufferedReader(new FileReader(fichierRes));
            String result = "";
            String line = "";
            while ((line = br2.readLine()) != null) {
                result = result + line + "\n";
            }

            result = tmp + result;

            mFile.delete();
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(result.getBytes());
            fos.flush();
            
            br = new BufferedReader(new FileReader(fichierRes));
            br.readLine();
            sCurrentLine = br.readLine();
            StringTokenizer st = new StringTokenizer(sCurrentLine);
            ln.setText("nb cells:"+st.nextToken()+" nb arrets:"+st.nextToken());
            lw.setText("nb freq:"+st.nextToken()+" nb trx max:"+st.nextToken());
            sCurrentLine = br.readLine();
            lval.setText(sCurrentLine);
            sCurrentLine = br.readLine();
            lexec.setText(sCurrentLine + " secondes");
            
            String id, d, f;
            
            while ((sCurrentLine = br.readLine()) != null) {
                st = new StringTokenizer(sCurrentLine);
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
