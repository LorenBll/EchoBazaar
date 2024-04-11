package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;
import main.java.controllers.*;






public class Main extends Application {
    
    private Stage primaryStage;   // finestra principale
    private BorderPane mainLayout;  // layout principale (cosa viene visualizzato nella finestra principale)






    // usato di default all'inizio del programma
    public void start ( Stage primaryStage ) {
        //! metodo che inizializza la finestra principale, ne setta le impostazioni e visualizza la pagina di login

        this.primaryStage = primaryStage;
        
        //. settaggio impostazioni della finestra principale
        this.primaryStage.setTitle("EchoBazaar");
        this.primaryStage.setResizable(false);
        this.primaryStage.initStyle(StageStyle.UNDECORATED); // rimuove i bordi della finestra (barra del titolo, pulsanti di chiusura, ecc.) >>> non si può spostare la finestra
        this.primaryStage.getIcons().add(new Image("/main/resources/images/iconOf_echoBazaar.png")); // icona della finestra (mostrata nella barra delle applicazioni)
        
        show_loginPage();
    
    }

    public void show_loginPage () {
        //! metodo che visualizza la pagina di login
        try {
            
            start_defaultFocusCancelerListener(); // annulla il focus di default riassegnandolo alla finestra principale

            // caricamento del file fxml del login
            URL locationOf_fxml = getClass().getResource("/main/resources/fxml/Login.fxml");
            FXMLLoader loader = new FXMLLoader(locationOf_fxml);
            this.mainLayout = loader.load(); // caricamento del layout del login da visualizzare nella finestra principale

            // visualizzazione della scena contenente il layout del login
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            
            // settaggio del controller della pagina di login
            LoginPageController controller = loader.getController();
            controller.set_main(this);

            primaryStage.show();


            
            set_keybinds(); // setta i vari keybinds

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void show_registrationPage () {
        //! metodo che visualizza la pagina di registrazione
        try {

            start_defaultFocusCancelerListener(); // annulla il focus di default riassegnandolo alla finestra principale

            // caricamento del file fxml della registrazione
            URL locationOf_fxml = getClass().getResource("/main/resources/fxml/Registration.fxml");
            FXMLLoader loader = new FXMLLoader(locationOf_fxml);
            this.mainLayout = loader.load(); // caricamento del layout della registrazione da visualizzare nella finestra principale

            // visualizzazione della scena contenente il layout della registrazione
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);

            // settaggio del controller della pagina di registrazione
            RegistrationPageController controller = loader.getController();
            controller.set_main(this);

            primaryStage.show();



            set_keybinds(); // setta i vari keybinds

        } catch (Exception e) { e.printStackTrace(); }
    }






    // === METODI DI UTILITÀ AKA DA NON MODIFICARE ===
    public Stage get_primaryStage () {
        return this.primaryStage;
    }
    
    public BorderPane get_mainLayout () {
        return this.mainLayout;
    }
    
    public void start_defaultFocusCancelerListener () {
        //! metodo che annulla il focus di default riassegnandolo alla finestra principale
        primaryStage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getRoot().requestFocus();
            }
        });
    }

    public void set_keybinds () {
        //! metodo che setta i vari keybinds

        //. key listener
        mainLayout.setOnKeyPressed(event -> {
        
            // per annullare il focus quando si preme il tasto ESC
            if (event.getCode() == KeyCode.ESCAPE) {
                Node focusedNode = mainLayout.getScene().getFocusOwner();
                if (focusedNode != mainLayout)
                    focusedNode.getParent().requestFocus(); // annulla il focus
            }
        
        });
    }

    public static void main ( String[] args ) { launch(args); }
    
}