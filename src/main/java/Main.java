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
import javafx.scene.input.KeyCombination;
import javafx.scene.Node;
import main.java.controllers.*;
import main.java.model.DataHandler;
import main.java.model.users.Customer;
import main.java.model.users.Vendor;






public class Main extends Application {
    
    private Stage primaryStage;   // finestra principale
    private BorderPane mainLayout;  // layout principale (cosa viene visualizzato nella finestra principale). tutte le pagine hanno come base questo layout

    public DataHandler dataHandler; // gestore dei dati (salvataggio, caricamento, ecc.)
    public Customer loggedInCustomer = null; // cliente loggato (null se nessuno è loggato)
    public Vendor loggedInVendor = null; // venditore loggato (null se nessuno è loggato)






    // usato di default all'inizio del programma
    public void start ( Stage primaryStage ) {
        //! metodo che inizializza la finestra principale, ne setta le impostazioni e visualizza la pagina di login

        DataHandler dataHandler = new DataHandler(); // inizializzazione del data manager
        this.dataHandler = dataHandler;

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

    public void show_passwordResetPage () {
        //! metodo che visualizza la pagina di recupero password
        try {
            
            start_defaultFocusCancelerListener(); // annulla il focus di default riassegnandolo alla finestra principale

            // caricamento del file fxml del recupero password
            URL locationOf_fxml = getClass().getResource("/main/resources/fxml/PasswordReset.fxml");
            FXMLLoader loader = new FXMLLoader(locationOf_fxml);
            this.mainLayout = loader.load(); // caricamento del layout del recupero password da visualizzare nella finestra principale

            // visualizzazione della scena contenente il layout del recupero password
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);

            // settaggio del controller della pagina di recupero password
            PasswordResetPageController controller = loader.getController();
            controller.set_main(this);

            primaryStage.show();



            set_keybinds(); // setta i vari keybinds

        } catch (Exception e) { e.printStackTrace(); }
    }

    // funzione che mostra la pagina principale in base al tipo di utente loggato
    public void show_userPage () {
        //! metodo che visualizza la pagina principale
        if (loggedInCustomer != null) {
            show_customerPage();
            return;
        }
        show_vendorPage();
    }

    public void show_customerPage () {
        //! metodo che visualizza la pagina principale del cliente
        try {
            
            start_defaultFocusCancelerListener(); // annulla il focus di default riassegnandolo alla finestra principale

            // caricamento del file fxml della pagina principale
            URL locationOf_fxml = getClass().getResource("/main/resources/fxml/CustomerPage.fxml");
            FXMLLoader loader = new FXMLLoader(locationOf_fxml);
            this.mainLayout = loader.load(); // caricamento del layout della pagina principale da visualizzare nella finestra principale

            // visualizzazione della scena contenente il layout della pagina principale
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);

            // settaggio del controller della pagina principale
            CustomerPageController controller = loader.getController();
            controller.set_main(this);
            controller.setup();

            primaryStage.show();



            set_keybinds(); // setta i vari keybinds

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void show_vendorPage () {
        //! metodo che visualizza la pagina principale del venditore
        try {
            
            start_defaultFocusCancelerListener(); // annulla il focus di default riassegnandolo alla finestra principale

            // caricamento del file fxml della pagina principale
            URL locationOf_fxml = getClass().getResource("/main/resources/fxml/VendorPage.fxml");
            FXMLLoader loader = new FXMLLoader(locationOf_fxml);
            this.mainLayout = loader.load(); // caricamento del layout della pagina principale da visualizzare nella finestra principale

            // visualizzazione della scena contenente il layout della pagina principale
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);

            // settaggio del controller della pagina principale
            VendorPageController controller = loader.getController();
            controller.set_main(this);
            controller.setup();

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

    public void minimize_window () {
        //! metodo che minimizza la finestra
        primaryStage.setIconified(true);
    }

    public void enter_fullscreen () {
        //! metodo che massimizza la finestra
        primaryStage.setFullScreenExitHint(""); // rimuove il messaggio di uscita dalla modalità fullscreen (che altrimenti appare in mezzo allo schermo, e per di più non funziona)
        primaryStage.setFullScreen(true);
    }

    public void exit_fullscreen () {
        //! metodo che minimizza la finestra
        primaryStage.setFullScreen(false);
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

        // se premo ESC annulla il focus
        mainLayout.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Node focusedNode = mainLayout.getScene().getFocusOwner();
                if (focusedNode != mainLayout)
                    focusedNode.getParent().requestFocus();
            }
        });

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

    }



    public static void main ( String[] args ) { launch(args); }
    
}