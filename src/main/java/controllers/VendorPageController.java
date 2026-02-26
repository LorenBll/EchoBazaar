package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import main.java.auth.CryptingEngine;
import main.java.model.Product;
import java.io.File;
import java.util.ArrayList;
import javafx.stage.FileChooser;
import javafx.scene.layout.AnchorPane;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;






public class VendorPageController extends Controller {

    //. barra di gestione della finestra (minimizza, chiudi e fullscreen)
    public boolean isFullscreen = false; // indica se la finestra è in modalità fullscreen o no
    @FXML private ImageView imageviewOf_fullscreenOperations;

    //. riquadro delle informazioni relative al vendor loggato e delle funzioni basilari che può svolgere
    @FXML private Label labelOf_username;
    @FXML private Label labelOf_balance;

    
    //. riquadro in cui verrano mostrate le varie viste
    @FXML private StackPane stackpaneOf_vendorViews;
    
    //. vista di benvenuto
    @FXML private FlowPane flowpaneOf_welcomeInterface;

    //. vista delle impostazioni
    boolean isVendorPasswordShown = false;
    int counterOfDeleteAccount = 0;
    @FXML private FlowPane flowpaneOf_settingsInterface;
    @FXML private Label labelOf_vendorID;
    @FXML private TextField textfieldOf_vendorUsername;
    @FXML private PasswordField passwordfieldOf_vendorPassword;
    @FXML private ImageView imageOf_showOrHide_vendorPassword;
    @FXML private Label labelOf_vendorPasswordShower;
    @FXML private Button buttonOf_settingsDeleteAccount;
    
    @FXML private Label labelOf_settingsWarning;
    
    //. vista della ricarica del saldo
    @FXML private FlowPane flowpaneOf_depositInterface;
    @FXML private TextField textfieldOf_moneyQuantity;

    @FXML private Label labelOf_depositInterfaceWarning;

    //. vista della registrazione di un nuovo prodotto
    @FXML private FlowPane flowpaneOf_newProductInterface;
    @FXML private TextField textfieldOf_nameOf_newProduct;
    @FXML private TextField textfiledOf_pathOfImageOf_newProduct;
    @FXML private TextField textfieldOf_priceOf_newProduct;
    @FXML private TextField textfiledOf_currentStockOf_newProduct;
    @FXML private TextArea textAreaOf_descriptionOf_newProduct;
    @FXML private CheckBox checkboxOf_autoRestockOf_newProduct;
    
    @FXML private FlowPane flowpaneOf_minimumQuantityOf_newProduct; 
    @FXML private TextField textfieldOf_minStockOf_newProduct;
    @FXML private FlowPane flowpaneOf_restockAmountOf_newProduct;
    @FXML private TextField textfieldOf_restockAmountOf_newProduct;
    @FXML private FlowPane flowpaneOf_sourceIDOf_newProduct;
    @FXML private TextField textfieldOf_sourceIDOf_newProduct;

    @FXML private Label labelOf_registrationOf_newProduct_warning;

    //. vista del magazzino
    @FXML private FlowPane flowpaneOf_storageInterface;
    @FXML private GridPane gridpaneOf_storageProducts;
    
    @FXML private TextField textfieldOf_storageSearchBar;
    @FXML private CheckBox checkboxOf_storageFinishing;
    @FXML private CheckBox checkboxOf_storageAutoRestock;
    
    @FXML private Label labelOf_storageWarning;

    //. vista di update di un prodotto
    @FXML private FlowPane flowpaneOf_productEditPage;
    @FXML private Label labelOf_productID_productEdit;
    @FXML private TextField textfieldOf_nameOf_productEdit;
    @FXML private TextField textfiledOf_pathOfImageOf_productEdit;
    @FXML private TextField textfiledOf_currentStockOf_productEdit;
    @FXML private TextField textfieldOf_priceOf_productEdit;
    @FXML private TextArea textAreaOf_descriptionOf_productEdit;
    @FXML private CheckBox checkboxOf_autoRestockOf_productEdit;
    
    @FXML private FlowPane flowpaneOf_minimumQuantityOf_productEdit;
    @FXML private TextField textfieldOf_minStockOf_productEdit;
    @FXML private FlowPane flowpaneOf_restockAmountOf_productEdit;
    @FXML private TextField textfieldOf_restockAmountOf_productEdit;
    @FXML private FlowPane flowpaneOf_sourceIDOf_productEdit;
    @FXML private TextField textfieldOf_sourceIDOf_productEdit;

    @FXML private Label labelOf_productEdit_warning;
    
    int counterOfDeleteProduct = 0;
    @FXML private Button buttonOf_productEditDeleteProduct;

    //. vista di visualizzazione degli altri prodotti
    @FXML private FlowPane flowpaneOf_otherProductsInterface;
    @FXML private GridPane gridpaneOf_otherProducts;

    @FXML private TextField textfieldOf_otherProductsSearchBar;

    //. vista di visualizzazione dei prodotti di un altro venditore
    @FXML private FlowPane flowpaneOf_otherProductsDetailsInterface;
    @FXML private ImageView imageviewOf_otherProductsDetailsImage;
    @FXML private Label labelOf_otherProductsDetailsID;
    @FXML private Label labelOf_otherProductsDetailsName;
    @FXML private Label labelOf_otherProductsDetailsPrice;
    @FXML private Label labelOf_otherProductsDetailsOwner;
    @FXML private TextArea textAreaOf_otherProductsDetailsDescription;






    public void setup () {
        //! metodo che viene chiamato dal main quando la finestra è stata caricata ed il main settato >>> con l'initialize non funziona perché il main non è ancora settato 

        String shortenedBalance = shorten_balance( main.loggedInVendor.get_balance() );

        // setto le informazioni del vendor loggato
        labelOf_username.setText( main.loggedInVendor.get_username() );
        labelOf_balance.setText( shortenedBalance + "€" );

        main.get_primaryStage().setFullScreenExitKeyCombination(null); // rimuove la combinazione di tasti per uscire dalla modalità fullscreen
        hide_autoRestock(); // nascondo i campi per il restock automatico perché di default non è selezionato (si potrebbe fare dall'fxml ma è meglio tenere tutto visualizzato in fase di sviluppo)
        show_welcomeInterface();

    }

    public void update_balance () {
        //! metodo che aggiorna il balance del vendor
        labelOf_balance.setText( shorten_balance( main.loggedInVendor.get_balance() ) + "€" );
    }   

    public void update () { setup(); }

    private String shorten_balance ( float balance ) {
        //! metodo che accorcia il balance se è troppo lungo, ma non lo arrotonda
        String shortenedBalance = "";
        if (balance >= 1000000) {
            shortenedBalance = String.format("%.2fM", balance / 1000000);
        }
        else if (balance >= 1000) {
            shortenedBalance = String.format("%.2fK", balance / 1000);
        }
        else {
            shortenedBalance = String.format("%.2f", balance);
        }
        return shortenedBalance;
    }






    //. === METODI DI UTILITÀ AKA DA NON MODIFICARE ===
    @FXML private void show_welcomeInterface () {
        //! metodo che mostra il riquadro di benvenuto
        flowpaneOf_welcomeInterface.toFront();
    }

    // usato da pulsante "X"
    @FXML private void close_window () {
        //! metodo che chiude la finestra
        System.exit(0);
    }

    // usato da pulsante "Fullscreen" (due freccie in diagonale opposte e puntanti verso l'esterno, oppure due freccie in diagonale opposte e puntanti verso l'interno)
    @FXML private void enterOrExit_fullscreen () {
        //! metodo che massimizza o minimizza la finestra, e effettua le operazioni necessarie per adattare il layout alla modalità fullscreen
        
        // passaggio a fullscreen
        if (!isFullscreen) {

            isFullscreen = true;
            main.enter_fullscreen();
            imageviewOf_fullscreenOperations.setImage(new javafx.scene.image.Image("/main/resources/images/iconOf_fullScreenExit.png"));

            return;
        }

        // passaggio a windowed (non fullscreen)
        isFullscreen = false;
        main.exit_fullscreen();
        imageviewOf_fullscreenOperations.setImage(new javafx.scene.image.Image("/main/resources/images/iconOf_fullScreenEnter.png"));
        
    }
    
    // usato da pulsante "Minimize" (linea orizzontale)
    @FXML private void minimize_window () {
        //! metodo che minimizza la finestra
        main.minimize_window();
    }

    
    @FXML private void logout () {
        //! metodo che effettua il logout
        main.loggedInVendor = null;
        main.show_loginPage();
    }






    //. interfaccia delle impostazioni
    @FXML private void show_settingsInterface () {
        //! metodo che mostra la vista delle impostazioni
        counterOfDeleteAccount = 0;
        buttonOf_settingsDeleteAccount.setText("Delete Account");
        flowpaneOf_settingsInterface.toFront();
        labelOf_vendorID.setText( main.loggedInVendor.get_ID() );

        // se sono sul focus del textfield dello username e premo invio, passo al passwordField
        textfieldOf_vendorUsername.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                passwordfieldOf_vendorPassword.requestFocus();
            }
        });

        // se sono sul focus del passwordField e premo invio, salvo le impostazioni
        passwordfieldOf_vendorPassword.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                save_settings();
            }
        });

        // se sono sul focus del textfield della password e premo invio, salvo le impostazioni
        labelOf_vendorPasswordShower.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                save_settings();
            }
        });

    }

    @FXML private void copy_ID () {
        //! metodo che copia l'ID del vendor negli appunti
        
        ClipboardContent content = new ClipboardContent();
        content.putString( main.loggedInVendor.get_ID() );
        Clipboard.getSystemClipboard().setContent(content);

    }

    // usato da pulsante "Show/Hide Password" (occhio/occhio sbarrato)
    @FXML private void showOrHide_password () {
        //! metodo che mostra o nasconde la password a seconda dello stato attuale della password (mostrata o non mostrata)        
        if (!isVendorPasswordShown) {
            labelOf_vendorPasswordShower.setVisible(true);
            passwordfieldOf_vendorPassword.setVisible(false);
            isVendorPasswordShown = true;

            imageOf_showOrHide_vendorPassword.setImage(new Image("/main/resources/images/iconOf_hidePassword.png")); // cambia l'icona del pulsante in "nascondi password"
        
            return;
        }
        
        labelOf_vendorPasswordShower.setVisible(false);
        passwordfieldOf_vendorPassword.setVisible(true);
        isVendorPasswordShown = false;

        // il passwordField richiede il focous senza però selezionare il testo e sposta il cursore alla fine del testo
        passwordfieldOf_vendorPassword.requestFocus();
        passwordfieldOf_vendorPassword.deselect();
        passwordfieldOf_vendorPassword.end();
        
        imageOf_showOrHide_vendorPassword.setImage(new Image("/main/resources/images/iconOf_showPassword.png")); // cambia l'icona del pulsante in "mostra password"
    
    }

    // usato da passwordField ad ogni variazione di testo
    @FXML private void update_labelOf_vendorPasswordShower () {
        //! metodo che aggiorna costantemente il labelOf_vendorPasswordShower
        labelOf_vendorPasswordShower.setText( passwordfieldOf_vendorPassword.getText() );
    }

    @FXML private void save_settings () {
        //! metodo che salva le impostazioni del vendor
        
        String newUsername = textfieldOf_vendorUsername.getText();
        String newPassword = passwordfieldOf_vendorPassword.getText();

        if (newUsername.equals("") && newPassword.equals("")) {
            show_settingsWarning("No Changes Were Made.");
            return;
        }

        // controllo che lo username non contenga caratteri speciali: |!@#$%^&*()+{}:"<>?|[];',.
        boolean containsSpecialCharacters = false;
        for (char c : "\\|!@#$%^&*()+{}:\"<>?|[];',.".toCharArray()) {
            if (textfieldOf_vendorUsername.getText().contains(String.valueOf(c))) {
                containsSpecialCharacters = true;
                break;
            }
        }
        if (containsSpecialCharacters) {
            show_settingsWarning("Username can't contain special characters.");
            return;
        }

        // controllo se lo username contiene spazi oppure è più lungo di 12 caratteri
        if ( newUsername.contains(" ") || newUsername.length() > 12 ) {
            show_settingsWarning("Username must be less than 12 characters and contain no spaces.");
            return;
        }

        // controllo se la password è più lunga di 12 caratteri
        if ( newPassword.length() > 12 ) {
            show_settingsWarning("Password must be less than 12 characters and contain no spaces.");
            return;
        }

        boolean resultOf_saveSettings = main.dataHandler.update_vendor( main.loggedInVendor.get_ID(), newUsername, newPassword );
        if (!resultOf_saveSettings) {
            show_settingsWarning("Username Already Taken.");
            return;
        }

        reset_settingsView();
        show_settingsWarning("Settings Saved.");
        labelOf_username.setText( main.loggedInVendor.get_username() );

    }

    // usato da pulsante "Reset"
    @FXML private void reset_settingsView () {
        //! metodo che resetta la vista delle impostazioni
        textfieldOf_vendorUsername.setText( "" );
        passwordfieldOf_vendorPassword.setText( "" );
        if (isVendorPasswordShown)
            showOrHide_password();
        // cancello il focus che altrimenti rimarrebbe, per il metodo showOrHide_password(), sul passwordField
        flowpaneOf_settingsInterface.requestFocus();
    }

    // usato da pulsante "Delete Account"
    @FXML private void delete_account () {
        //! metodo che elimina l'account del vendor loggato

        counterOfDeleteAccount++;
        if (counterOfDeleteAccount < 2) {

            labelOf_settingsWarning.setText("Are you sure? Click again to confirm.");
            buttonOf_settingsDeleteAccount.setText("Confirm Deletion");
            
            // faccio partire un timer di 5 al termine del quale l'operazione viene annullata
            Platform.runLater( () -> {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    counterOfDeleteAccount = 0;
                                    labelOf_settingsWarning.setText("");
                                    buttonOf_settingsDeleteAccount.setText("Delete Account");
                                });
                            }
                        },
                        5000
                );
            });
            

            return;
        }
        main.dataHandler.delete_vendor( main.loggedInVendor.get_username() );
        counterOfDeleteAccount = 0;
        main.loggedInVendor = null;
        main.show_loginPage();

    }

    public void show_settingsWarning ( String warningText ) {
        //! metodo che mostra un messaggio di warning

        labelOf_settingsWarning.setText( warningText );

        // faccio partire un timer di 5 al termine del quale l'operazione viene annullata
        Platform.runLater( () -> {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                labelOf_settingsWarning.setText("");
                            });
                        }
                    },
                    3000
            );
        });

    }






    //. interfaccia della ricarica del saldo
    @FXML private void show_depositInterface () {
        //! metodo che mostra la vista della ricarica del saldo
        flowpaneOf_depositInterface.toFront();
    }

    @FXML private void deposit () {
        //! metodo che deposita il saldo inserito nel textfield
        String moneyQuantity = textfieldOf_moneyQuantity.getText();
        if (moneyQuantity.equals("")) {
            return;
        }

        float money = 0;
        try {
            money = Float.parseFloat( moneyQuantity );
        } catch (NumberFormatException e) {
            show_depositInterfaceWarning("Invalid Input.");
            return;
        }
        main.loggedInVendor.deposit( money );

        textfieldOf_moneyQuantity.setText("");  
        labelOf_balance.setText( shorten_balance( main.loggedInVendor.get_balance() ) + "€" );

        main.dataHandler.update_vendorFile();

    }

    @FXML private void withdraw () {
        //! metodo che preleva il saldo inserito nel textfield
        String moneyQuantity = textfieldOf_moneyQuantity.getText();
        if (moneyQuantity.equals("")) {
            return;
        }

        float money = 0;
        try {
            money = Float.parseFloat( moneyQuantity );
        } catch (NumberFormatException e) {
            show_depositInterfaceWarning("Invalid Input.");
            return;
        }
        if ( !main.loggedInVendor.withdraw( money ) ) {
            show_depositInterfaceWarning("Not Enough Balance.");
            return;
        }

        textfieldOf_moneyQuantity.setText("");
        labelOf_balance.setText( shorten_balance( main.loggedInVendor.get_balance() ) + "€" );

        main.dataHandler.update_vendorFile();

    }

    public void show_depositInterfaceWarning ( String warningText ) {
        //! metodo che mostra un messaggio di warning

        labelOf_depositInterfaceWarning.setText( warningText );

        // faccio partire un timer di 5 al termine del quale l'operazione viene annullata
        Platform.runLater( () -> {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                labelOf_depositInterfaceWarning.setText("");
                            });
                        }
                    },
                    3000
            );
        });

    }






    //. interfaccia della registrazione di un nuovo prodotto
    @FXML private void show_newProductInterface () {
        //! metodo che mostra la vista della registrazione di un nuovo prodotto
        flowpaneOf_newProductInterface.toFront();

        // se sono sul focus del textfield del nome del prodotto e premo invio, passo al textfield del path dell'immagine
        textfieldOf_nameOf_newProduct.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfieldOf_vendorUsername.requestFocus();
                choose_image();
            }
        });

        // se sono sul focus del textfield del path dell'immagine e premo invio, passo al textfield del prezzo
        textfiledOf_pathOfImageOf_newProduct.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfieldOf_priceOf_newProduct.requestFocus();
            }
        });

        // se sono sul focus del textfield del prezzo e premo invio, passo al textfield dello stock attuale
        textfieldOf_priceOf_newProduct.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfiledOf_currentStockOf_newProduct.requestFocus();
            }
        });

        // se sono sul focus del textfield dello stock attuale e premo invio, passo alla textArea della descrizione
        textfiledOf_currentStockOf_newProduct.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textAreaOf_descriptionOf_newProduct.requestFocus();
            }
        });

        // se sono sul focus della textfield della quantità minima e premo invio, passo alla textfield della quantità di restock
        textfieldOf_minStockOf_newProduct.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfieldOf_restockAmountOf_newProduct.requestFocus();
            }
        });

        // se sono sul focus della textfield della quantità di restock e premo invio, passo alla textfield del sourceID
        textfieldOf_restockAmountOf_newProduct.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfieldOf_sourceIDOf_newProduct.requestFocus();
            }
        });
        
    }

    @FXML private void hide_autoRestock () {
        //! metodo che nasconde o mostra i campi per il restock automatico
        if (checkboxOf_autoRestockOf_newProduct.isSelected()) {
            flowpaneOf_minimumQuantityOf_newProduct.setVisible(true);
            flowpaneOf_restockAmountOf_newProduct.setVisible(true);
            flowpaneOf_sourceIDOf_newProduct.setVisible(true);
            return;
        }

        flowpaneOf_minimumQuantityOf_newProduct.setVisible(false);
        flowpaneOf_restockAmountOf_newProduct.setVisible(false);
        flowpaneOf_sourceIDOf_newProduct.setVisible(false);
    
        // cancello l'eventuale testo nei campi
        textfieldOf_minStockOf_newProduct.setText("");
        textfieldOf_restockAmountOf_newProduct.setText("");
        textfieldOf_sourceIDOf_newProduct.setText("");

    }

    @FXML void choose_image () {
        //! metodo che permette di scegliere un'immagine da inserire nel prodotto

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Product Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog( main.get_primaryStage() );
        if (selectedFile != null) {
            textfiledOf_pathOfImageOf_newProduct.setText( selectedFile.getAbsolutePath() );
        }
        
        textfiledOf_pathOfImageOf_newProduct.requestFocus(); // così se premo invio passo al textfield del prezzo

    }

    @FXML private void register_newProduct () {
        //! metodo che registra un nuovo prodotto

        String name = textfieldOf_nameOf_newProduct.getText();
        // controllo che il nome non sia vuoto e non contenga caratteri speciali: |!@#$%^&*()+{}:"<>?|[];',.
        if (name.equals("")) {
            show_registrationOf_newProduct_warning("Name can't be empty.");
            return;
        }
        for (char c : "\\|!@#$%^&*()+{}:\"<>?|[];',.".toCharArray()) {
            if (name.contains(String.valueOf(c))) {
                show_registrationOf_newProduct_warning("Name can't contain special characters.");
                return;
            }
        }

        String price = textfieldOf_priceOf_newProduct.getText();
        // controllo che il prezzo sia un numero positivo
        if (price.equals("")) {
            show_registrationOf_newProduct_warning("Price can't be empty.");
            return;
        }
        float priceFloat = 0;
        try {
            priceFloat = Float.parseFloat( price );
        } catch (NumberFormatException e) {
            show_registrationOf_newProduct_warning("Invalid Price.");
            return;
        }
        if (priceFloat <= 0) {
            show_registrationOf_newProduct_warning("Price must be positive.");
            return;
        }

        String currentStock = textfiledOf_currentStockOf_newProduct.getText();
        // controllo che lo stock attuale sia un numero positivo
        if (currentStock.equals("")) {
            show_registrationOf_newProduct_warning("Current Stock can't be empty.");
            return;
        }
        int currentStockInt = 0;
        try {
            currentStockInt = Integer.parseInt( currentStock );
        } catch (NumberFormatException e) {
            show_registrationOf_newProduct_warning("Invalid Current Stock.");
            return;
        }
        if (currentStockInt < 0) {
            show_registrationOf_newProduct_warning("Current Stock must be positive.");
            return;
        }
        
        String description = textAreaOf_descriptionOf_newProduct.getText();
        // controllo che la descrizione non sia vuota
        if (description.equals("")) {
            show_registrationOf_newProduct_warning("Description can't be empty.");
            return;
        }
        if (description.length() > 250) {
            show_registrationOf_newProduct_warning("Description must be less than 250 characters.");
            return;
        }
        if (description.contains(",")) {
            show_registrationOf_newProduct_warning("Description can't contain the character ','.");
            return;
        }
        if (description.contains("|")) {
            show_registrationOf_newProduct_warning("Description can't contain the character '|'.");
            return;
        }
        
        boolean autoRestock = checkboxOf_autoRestockOf_newProduct.isSelected();
        if (autoRestock) {

            String minStock = textfieldOf_minStockOf_newProduct.getText();
            // controllo che il minStock sia un numero positivo e che non contenga caratteri speciali
            if (minStock.equals("")) {
                show_registrationOf_newProduct_warning("Minimum Stock can't be empty.");
                return;
            }
            int minStockInt = 0;
            try {
                minStockInt = Integer.parseInt( minStock );
            } catch (NumberFormatException e) {
                show_registrationOf_newProduct_warning("Invalid Minimum Stock.");
                return;
            }
            if (minStockInt < 0) {
                show_registrationOf_newProduct_warning("Minimum Stock must be positive.");
                return;
            }



            String restockAmount = textfieldOf_restockAmountOf_newProduct.getText();
            // controllo che il restockAmount sia un numero positivo e che non contenga caratteri speciali
            if (restockAmount.equals("")) {
                show_registrationOf_newProduct_warning("Restock Amount can't be empty.");
                return;
            }
            int restockAmountInt = 0;
            try {
                restockAmountInt = Integer.parseInt( restockAmount );
            } catch (NumberFormatException e) {
                show_registrationOf_newProduct_warning("Invalid Restock Amount.");
                return;
            }
            if (restockAmountInt < 0) {
                show_registrationOf_newProduct_warning("Restock Amount must be positive.");
                return;
            }



            String sourceID = textfieldOf_sourceIDOf_newProduct.getText();
            // controllo che il sourceID non sia vuoto e che non contenga caratteri speciali: |!@#$%^&*()+{}:"<>?|[];',.
            if (sourceID.equals("")) {
                show_registrationOf_newProduct_warning("Source ID can't be empty.");
                return;
            }
            for (char c : "\\|!@#$%^&*()+{}:\"<>?|[];',.".toCharArray()) {
                if (textfieldOf_vendorUsername.getText().contains(String.valueOf(c))) {
                    show_registrationOf_newProduct_warning("Invalid Source ID.");
                    return;
                }
            }
            // controllo che il sourceID sia un ID valido
            if ( !main.dataHandler.isProductIDValid(sourceID) ) {
                show_registrationOf_newProduct_warning("Invalid Source ID.");
                return;
            }
            // controllo che il sourceID non indichi un mio prodotto
            if ( main.dataHandler.retrieve_productByID(sourceID).get_vendorID().equals( main.loggedInVendor.get_ID() ) ) {
                show_registrationOf_newProduct_warning("Source ID can't be one of your products.");
                return;
            }

        }
        else {
            textfieldOf_minStockOf_newProduct.setText("0");
            textfieldOf_restockAmountOf_newProduct.setText("0");
            textfieldOf_sourceIDOf_newProduct.setText("");
        }

        String pathOf_image = textfiledOf_pathOfImageOf_newProduct.getText();
        if (pathOf_image.equals("")) {
            pathOf_image = "/main/resources/images/iconOf_noImage.png";
        }
        else {
            try {

                //. controllo che il path inserito sia valido e che l'immagine non sia troppo grande
                Image image = new Image( new File(pathOf_image).toURI().toString() ); // controllo che il path sia valido
                Image imageToCheck = new Image( "/main/resources/images/iconOf_noImage.png" ); 
                if (image.getWidth() > imageToCheck.getWidth() || image.getHeight() > imageToCheck.getHeight()) { // controllo che l'immagine non sia troppo grande, comparando le dimensioni con un'immagine di default
                    show_registrationOf_newProduct_warning("Image must be " + (int)imageToCheck.getWidth() + "x" + (int)imageToCheck.getHeight() + " pixels or smaller.");
                    return;
                }

                //. copio l'immagine nella cartella delle immagini
                File source = new File(pathOf_image);
                String currentTime = String.valueOf(System.currentTimeMillis());
                File dest = new File("src/main/resources/user_images/" + main.loggedInVendor.get_ID() + "_" + CryptingEngine.encrypt_string(name + currentTime) + ".png");
                Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                pathOf_image = "/main/resources/user_images/" + main.loggedInVendor.get_ID() + "_" + CryptingEngine.encrypt_string(name + currentTime) + ".png";

            } catch (Exception e) {
                show_registrationOf_newProduct_warning("Invalid Image.");
                return;
            }
        }

        // converto dati necessari nel formato corretto e registro il prodotto
        int minStockInt = Integer.parseInt( textfieldOf_minStockOf_newProduct.getText() );
        int restockAmountInt = Integer.parseInt( textfieldOf_restockAmountOf_newProduct.getText() );
        boolean isClone = checkboxOf_autoRestockOf_newProduct.isSelected();
        String sourceID = textfieldOf_sourceIDOf_newProduct.getText();
        Product newProduct = new Product( main.loggedInVendor.get_ID() , name , description , pathOf_image , priceFloat , currentStockInt , autoRestock , minStockInt , restockAmountInt , isClone , sourceID );
    
        main.dataHandler.register_product( newProduct , this.main );
        reset_configurationOf_registrationOf_newProduct();
        show_registrationOf_newProduct_warning("Product Registered.");

    }

    @FXML private void reset_configurationOf_registrationOf_newProduct () {
        //! metodo che resetta la vista della registrazione di un nuovo prodotto
        textfieldOf_nameOf_newProduct.setText("");
        textfiledOf_pathOfImageOf_newProduct.setText("");
        textfieldOf_priceOf_newProduct.setText("");
        textfiledOf_currentStockOf_newProduct.setText("");
        textAreaOf_descriptionOf_newProduct.setText("");
        checkboxOf_autoRestockOf_newProduct.setSelected(false);
        textfieldOf_minStockOf_newProduct.setText("");
        textfieldOf_restockAmountOf_newProduct.setText("");
        textfieldOf_sourceIDOf_newProduct.setText("");
        hide_autoRestock();
    }

    private void show_registrationOf_newProduct_warning ( String warningText ) {
        //! metodo che mostra un messaggio di warning

        labelOf_registrationOf_newProduct_warning.setText( warningText );

        // faccio partire un timer di 5 al termine del quale l'operazione viene annullata
        Platform.runLater( () -> {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                labelOf_registrationOf_newProduct_warning.setText("");
                            });
                        }
                    },
                    3000
            );
        });

    }

    




    //. interfaccia del magazzino
    @FXML private void show_storageInterface () {
        //! metodo che mostra la vista dei prodotti
        flowpaneOf_storageInterface.toFront();
        fill_storageGrid();
    }

    @FXML private void search_storage () {
        //! metodo che riempie la griglia dei prodotti in base alla stringa di ricerca
        fill_storageGrid();
    }
    
    public void fill_storageGrid () {
        //! metodo che riempie la griglia dei prodotti
        
        int currentRow = 1;
        try {
            
            gridpaneOf_storageProducts.getChildren().clear(); // pulisco la griglia dei prodotti (per evitare sovrapposizioni o prodotti fantasma aka. eliminati ma ancora visualizzati)
            ArrayList<Product> productsToView = main.dataHandler.retrieve_productsByOwner( main.loggedInVendor.get_ID() );
            if (productsToView.size() == 0) {
                return;
            }

            //. pulisco la lista "productsToView" da tutti i prodotti in base alla stringa "search"
            // parola chiave per la ricerca
            String search = textfieldOf_storageSearchBar.getText().toLowerCase();
            if (!search.equals("")) {
                ArrayList<Product> productsToRemove = new ArrayList<>();
            
                for (Product currentProduct : productsToView) {
            
                    if ( !(currentProduct.get_name().toLowerCase().contains(search) || currentProduct.get_description().toLowerCase().contains(search)) ) {
                        productsToRemove.add(currentProduct);
                    }
                
                }

                for (Product currentProduct : productsToRemove) {
                    productsToView.remove(currentProduct);
                }
            
            }

            // filtro per prodotti con restock automatico
            if (checkboxOf_storageAutoRestock.isSelected()) {
                ArrayList<Product> productsToRemove = new ArrayList<>();
            
                for (Product currentProduct : productsToView) {
            
                    if ( !currentProduct.get_autoRestock() ) {
                        productsToRemove.add(currentProduct);
                    }
                
                }

                for (Product currentProduct : productsToRemove) {
                    productsToView.remove(currentProduct);
                }
            
            }

            // filtro per prodotti in esaurimento
            if (checkboxOf_storageFinishing.isSelected()) {
                ArrayList<Product> productsToRemove = new ArrayList<>();
            
                for (Product currentProduct : productsToView) {
            
                    if ( currentProduct.get_currentStock() > 5 ) {
                        productsToRemove.add(currentProduct);
                    }
                
                }

                for (Product currentProduct : productsToRemove) {
                    productsToView.remove(currentProduct);
                }
            
            }

            //. visualizzazione dei prodotti
            for ( Product currentProduct : productsToView ) { // visualizzo i prodotti
                
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/resources/fxml/VendorProductCard.fxml"));
                AnchorPane productCard = loader.load();
                VendorProductCardController productCardController = loader.getController();
                productCardController.set_main(main);
                productCardController.setup(currentProduct , this);
                
                gridpaneOf_storageProducts.add(productCard, 0, currentRow);
                currentRow++;
            }

        }
        catch (Exception e) { e.printStackTrace(); }

    }

    public void show_storageWarning ( String warningText ) {
        //! metodo che mostra un messaggio di warning

        labelOf_storageWarning.setText( warningText );

        // faccio partire un timer di 5 al termine del quale l'operazione viene annullata
        Platform.runLater( () -> {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                labelOf_storageWarning.setText("");
                            });
                        }
                    },
                    3000
            );
        });

    }






    //. interfaccia di modifica di un prodotto
    public void show_productEditPage ( Product product ) {
        //! metodo che mostra la vista di modifica di un prodotto
        flowpaneOf_productEditPage.toFront();

        labelOf_productID_productEdit.setText( product.get_ID() );
        textfieldOf_nameOf_productEdit.setText( product.get_name() );
        textfiledOf_pathOfImageOf_productEdit.setText( product.get_pathOf_image() );
        textfieldOf_priceOf_productEdit.setText( String.valueOf( product.get_sellingPrice() ) );
        textfiledOf_currentStockOf_productEdit.setText( String.valueOf( product.get_currentStock() ) );
        textAreaOf_descriptionOf_productEdit.setText( product.get_description() );
        
        checkboxOf_autoRestockOf_productEdit.setSelected( product.get_autoRestock() );
        if ( checkboxOf_autoRestockOf_productEdit.isSelected() ) {
            flowpaneOf_minimumQuantityOf_productEdit.setVisible(true);
            flowpaneOf_restockAmountOf_productEdit.setVisible(true);
            flowpaneOf_sourceIDOf_productEdit.setVisible(true);
            textfieldOf_minStockOf_productEdit.setText( String.valueOf( product.get_minStock() ) );
            textfieldOf_restockAmountOf_productEdit.setText( String.valueOf( product.get_restockAmount() ) );
            textfieldOf_sourceIDOf_productEdit.setText( product.get_sourceID() );
        }
        else {
            flowpaneOf_minimumQuantityOf_productEdit.setVisible(false);
            flowpaneOf_restockAmountOf_productEdit.setVisible(false);
            flowpaneOf_sourceIDOf_productEdit.setVisible(false);
        }



        // se sono sul focus del textfield del nome del prodotto e premo invio, passo al textfield del path dell'immagine
        textfieldOf_nameOf_productEdit.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                //choose_image_productEdit();
            }
        });

        // se sono sul focus del textfield del path dell'immagine e premo invio, passo al textfield del prezzo
        textfiledOf_pathOfImageOf_productEdit.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfieldOf_priceOf_productEdit.requestFocus();
            }
        });

        // se sono sul focus del textfield del prezzo e premo invio, passo al textfield dello stock attuale
        textfieldOf_priceOf_productEdit.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfiledOf_currentStockOf_productEdit.requestFocus();
            }
        });

        // se sono sul focus del textfield dello stock attuale e premo invio, passo alla textArea della descrizione
        textfiledOf_currentStockOf_productEdit.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textAreaOf_descriptionOf_productEdit.requestFocus();
            }
        });

        // se sono sul focus della textfield della quantità minima e premo invio, passo alla textfield della quantità di restock
        textfieldOf_minStockOf_productEdit.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfieldOf_restockAmountOf_productEdit.requestFocus();
            }
        });

        // se sono sul focus della textfield della quantità di restock e premo invio, passo alla textfield del sourceID
        textfieldOf_restockAmountOf_productEdit.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                textfieldOf_sourceIDOf_productEdit.requestFocus();
            }
        });

    }

    @FXML private void hide_autoRestock_productEdit () {
        //! metodo che nasconde o mostra i campi per il restock automatico

        if (checkboxOf_autoRestockOf_productEdit.isSelected()) {
            flowpaneOf_minimumQuantityOf_productEdit.setVisible(true);
            flowpaneOf_restockAmountOf_productEdit.setVisible(true);
            flowpaneOf_sourceIDOf_productEdit.setVisible(true);
            return;
        }

        flowpaneOf_minimumQuantityOf_productEdit.setVisible(false);
        flowpaneOf_restockAmountOf_productEdit.setVisible(false);
        flowpaneOf_sourceIDOf_productEdit.setVisible(false);

        // cancello l'eventuale testo nei campi
        textfieldOf_minStockOf_productEdit.setText("");
        textfieldOf_restockAmountOf_productEdit.setText("");
        textfieldOf_sourceIDOf_productEdit.setText("");

    }

    // tocheck : implementare fileChooser per scegliere l'immagine nuova (per ora è assente perché causava problemi che in debug non si presentavano, ma si presentavano solo in esecuzione normale)

    private void show_productEdit_warning ( String warningText ) {
        //! metodo che mostra un messaggio di warning

        labelOf_productEdit_warning.setText( warningText );

        // faccio partire un timer di 5 al termine del quale l'operazione viene annullata
        Platform.runLater( () -> {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                labelOf_productEdit_warning.setText("");
                            });
                        }
                    },
                    3000
            );
        });

    }

    @FXML private void save_changes_productEdit () {
        //! metodo che salva le modifiche apportate ad un prodotto

        String newNameOf_updatedProduct = textfieldOf_nameOf_productEdit.getText();
        // controllo che il nome non sia vuoto e non contenga caratteri speciali: |!@#$%^&*()+{}:"<>?|[];',.
        if (newNameOf_updatedProduct.equals("")) {
            show_productEdit_warning("Name can't be empty.");
            return;
        }
        for (char c : "\\|!@#$%^&*()+{}:\"<>?|[];',.".toCharArray()) {
            if (newNameOf_updatedProduct.contains(String.valueOf(c))) {
                show_productEdit_warning("Name can't contain special characters.");
                return;
            }
        }

        String newProductPrice = textfieldOf_priceOf_productEdit.getText();
        // controllo che il prezzo sia un numero positivo
        if (newProductPrice.equals("")) {
            show_productEdit_warning("Price can't be empty.");
            return;
        }
        float newPriceFloatOf_updatedProduct = 0;
        try {
            newPriceFloatOf_updatedProduct = Float.parseFloat( newProductPrice );
        } catch (NumberFormatException e) {
            show_productEdit_warning("Invalid Price.");
            return;
        }
        if (newPriceFloatOf_updatedProduct <= 0) {
            show_productEdit_warning("Price must be positive.");
            return;
        }

        String newProductCurrentStock = textfiledOf_currentStockOf_productEdit.getText();
        // controllo che lo stock attuale sia un numero positivo
        if (newProductCurrentStock.equals("")) {
            show_productEdit_warning("Current Stock can't be empty.");
            return;
        }
        int newCurrentStockIntOf_updatedProduct = 0;
        try {
            newCurrentStockIntOf_updatedProduct = Integer.parseInt( newProductCurrentStock );
        } catch (NumberFormatException e) {
            show_productEdit_warning("Invalid Current Stock.");
            return;
        }
        if (newCurrentStockIntOf_updatedProduct < 0) {
            show_productEdit_warning("Current Stock must be positive.");
            return;
        }

        String newDescriptionOf_updatedProduct = textAreaOf_descriptionOf_productEdit.getText();
        // controllo che la descrizione non sia vuota
        if (newDescriptionOf_updatedProduct.equals("")) {
            show_productEdit_warning("Description can't be empty.");
            return;
        }
        if (newDescriptionOf_updatedProduct.length() > 250) {
            show_productEdit_warning("Description must be less than 250 characters.");
            return;
        }
        if (newDescriptionOf_updatedProduct.contains(",")) {
            show_productEdit_warning("Description can't contain the character ','.");
            return;
        }
        if (newDescriptionOf_updatedProduct.contains("|")) {
            show_productEdit_warning("Description can't contain the character '|'.");
            return;
        }

        boolean newProductAutoRestockStatusOf_updatedProduct = checkboxOf_autoRestockOf_productEdit.isSelected();
        if (newProductAutoRestockStatusOf_updatedProduct) {

            String newMinStock = textfieldOf_minStockOf_productEdit.getText();
            // controllo che il minStock sia un numero positivo e che non contenga caratteri speciali
            if (newMinStock.equals("")) {
                show_productEdit_warning("Minimum Stock can't be empty.");
                return;
            }
            int minStockInt = 0;
            try {
                minStockInt = Integer.parseInt( newMinStock );
            } catch (NumberFormatException e) {
                show_productEdit_warning("Invalid Minimum Stock.");
                return;
            }
            if (minStockInt < 0) {
                show_productEdit_warning("Minimum Stock must be positive.");
                return;
            }



            String newRestockAmount = textfieldOf_restockAmountOf_productEdit.getText();
            // controllo che il restockAmount sia un numero positivo e che non contenga caratteri speciali
            if (newRestockAmount.equals("")) {
                show_productEdit_warning("Restock Amount can't be empty.");
                return;
            }
            int restockAmountInt = 0;
            try {
                restockAmountInt = Integer.parseInt( newRestockAmount );
            } catch (NumberFormatException e) {
                show_productEdit_warning("Invalid Restock Amount.");
                return;
            }
            if (restockAmountInt < 0) {
                show_productEdit_warning("Restock Amount must be positive.");
                return;
            }



            String newSourceID = textfieldOf_sourceIDOf_productEdit.getText();
            // controllo che il sourceID non sia vuoto e che non contenga caratteri speciali: |!@#$%^&*()+{}:"<>?|[];',.
            if (newSourceID.equals("")) {
                show_productEdit_warning("Source ID can't be empty.");
                return;
            }
            for (char c : "\\|!@#$%^&*()+{}:\"<>?|[];',.".toCharArray()) {
                if (textfieldOf_vendorUsername.getText().contains(String.valueOf(c))) {
                    show_productEdit_warning("Source ID can't contain special characters.");
                    return;
                }
            }
            // controllo che il sourceID sia un ID valido
            if ( !main.dataHandler.isProductIDValid(newSourceID) ) {
                show_productEdit_warning("Invalid Source ID.");
                return;
            }
            // controllo che il sourceID non sia uguale all'ID del prodotto che si sta modificando
            if (newSourceID.equals( labelOf_productID_productEdit.getText() )) {
                show_productEdit_warning("Source ID can't be the same as the Product ID.");
                return;
            }
            // controllo che il sourceID non indichi un mio prodotto
            if ( main.dataHandler.retrieve_productByID(newSourceID).get_vendorID().equals( main.loggedInVendor.get_ID() ) ) {
                show_productEdit_warning("Source ID can't be one of your products.");
                return;
            }
        
        }
        else {
            textfieldOf_minStockOf_productEdit.setText("0");
            textfieldOf_restockAmountOf_productEdit.setText("0");
            textfieldOf_sourceIDOf_productEdit.setText("");
        }

        // tocheck : implementare aggiornamento immagine (per ora è assente perché causava problemi che in debug non si presentavano, ma si presentavano solo in esecuzione normale)
            // il problema che si verificava era che al reset della pagina, benché tutte le informazioni fossero state resettate, 
            // l'immagine rimaneva invariata (con l'aggiunta di un ritardo) oppure la lista dello storage non veniva direttamenre caricata perché l'URL dell'immagine non era valido
            // questo problema si risolveva solo riavviando l'applicazione (l'immagine veniva caricata correttamente, ma ad un altra modifica si verificava allo stesso modo)

        // converto dati necessari nel formato corretto e aggiorno il prodotto
        int newMinStockIntOf_updatedProduct = Integer.parseInt( textfieldOf_minStockOf_productEdit.getText() );
        int newRestockAmountIntOf_updatedProduct = Integer.parseInt( textfieldOf_restockAmountOf_productEdit.getText() );
        boolean isClone = checkboxOf_autoRestockOf_productEdit.isSelected();
        String newSourceIDOF_updatedProduct = textfieldOf_sourceIDOf_productEdit.getText();

        main.dataHandler.update_product( labelOf_productID_productEdit.getText() , newNameOf_updatedProduct , newDescriptionOf_updatedProduct , textfiledOf_pathOfImageOf_productEdit.getText() , newPriceFloatOf_updatedProduct , newCurrentStockIntOf_updatedProduct , newProductAutoRestockStatusOf_updatedProduct , newMinStockIntOf_updatedProduct , newRestockAmountIntOf_updatedProduct , isClone , newSourceIDOF_updatedProduct );
        reset_productEditPage();
        show_storageInterface();

    }

    @FXML private void reset_productEditPage () {
        //! metodo che resetta la vista di modifica di un prodotto
        
        textfieldOf_nameOf_productEdit.setText("");
        textfiledOf_pathOfImageOf_productEdit.setText("");
        textfieldOf_priceOf_productEdit.setText("");
        textfiledOf_currentStockOf_productEdit.setText("");
        textAreaOf_descriptionOf_productEdit.setText("");
        checkboxOf_autoRestockOf_productEdit.setSelected(false);
        textfieldOf_minStockOf_productEdit.setText("");
        textfieldOf_restockAmountOf_productEdit.setText("");
        textfieldOf_sourceIDOf_productEdit.setText("");
        hide_autoRestock_productEdit();
        show_storageInterface();    

    }

    @FXML private void delete_product () {
        //! metodo che elimina un prodotto

        counterOfDeleteProduct++;
        if (counterOfDeleteProduct < 2) {

            labelOf_productEdit_warning.setText("Are you sure? Click again to confirm.");
            buttonOf_productEditDeleteProduct.setText("Confirm Deletion");
            
            // faccio partire un timer di 5 al termine del quale l'operazione viene annullata
            Platform.runLater( () -> {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    counterOfDeleteProduct = 0;
                                    labelOf_productEdit_warning.setText("");
                                    buttonOf_productEditDeleteProduct.setText("Delete Product");
                                });
                            }
                        },
                        5000
                );
            });
            

            return;
        }
        main.dataHandler.delete_product( labelOf_productID_productEdit.getText() );
        counterOfDeleteProduct = 0;
        reset_productEditPage();

    }






    //. interfaccia in cui vengono mostrati i prodotti degli altri venditori (serve per trovare gli ID dei prodotti da clonare)
    @FXML private void show_otherVendorsProducts () {
        //! metodo che mostra la vista dei prodotti degli altri venditori
        flowpaneOf_otherProductsInterface.toFront();
        fill_otherVendorsProductsGrid();
    }

    @FXML private void search_otherVendorsProducts () {
        //! metodo che riempie la griglia dei prodotti degli altri venditori in base alla stringa di ricerca
        fill_otherVendorsProductsGrid();
    }

    public void fill_otherVendorsProductsGrid () {
        //! metodo che riempie la griglia dei prodotti degli altri venditori

        int currentRow = 1;
        try {
            
            gridpaneOf_otherProducts.getChildren().clear(); // pulisco la griglia dei prodotti (per evitare sovrapposizioni o prodotti fantasma aka. eliminati ma ancora visualizzati)
            ArrayList<Product> productsToView = main.dataHandler.retrieve_productsByNotOwner( main.loggedInVendor.get_ID() );
            if (productsToView.size() == 0) {
                return;
            }

            //. pulisco la lista "productsToView" da tutti i prodotti in base alla stringa "search"
            String search = textfieldOf_otherProductsSearchBar.getText().toLowerCase(); // parola chiave per la ricerca
            if (!search.equals("")) {
                ArrayList<Product> productsToRemove = new ArrayList<>();
            
                for (Product currentProduct : productsToView) {
            
                    if ( !(currentProduct.get_name().toLowerCase().contains(search) || currentProduct.get_description().toLowerCase().contains(search)) ) {
                        productsToRemove.add(currentProduct);
                    }
                
                }

                for (Product currentProduct : productsToRemove) {
                    productsToView.remove(currentProduct);
                }
            
            }

            //. visualizzazione dei prodotti
            for ( Product currentProduct : productsToView ) { // visualizzo i prodotti
                
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/resources/fxml/VendorNotOwnerProductCard.fxml"));
                AnchorPane productCard = loader.load();
                VendorNotOwnerProductCardController productCardController = loader.getController();
                productCardController.set_main(main);
                productCardController.setup(currentProduct , this);
                
                gridpaneOf_otherProducts.add(productCard, 0, currentRow);
                currentRow++;
            
            }

        }
        catch (Exception e) { e.printStackTrace(); }

    }







    //. interfaccia di visualizzazione dei dettagli di un prodotto
    public void show_productDetailsPage ( Product product ) {
        //! metodo che mostra la vista dei dettagli di un prodotto
        flowpaneOf_otherProductsDetailsInterface.toFront();
        flowpaneOf_otherProductsDetailsInterface.requestFocus();

        imageviewOf_otherProductsDetailsImage.setImage( new Image( product.get_pathOf_image() ) );
        labelOf_otherProductsDetailsID.setText( product.get_ID() );
        labelOf_otherProductsDetailsName.setText( product.get_name() );
        labelOf_otherProductsDetailsPrice.setText( String.valueOf(product.get_sellingPrice()) );
        labelOf_otherProductsDetailsOwner.setText( main.dataHandler.retrieve_vendorByID( product.get_vendorID() ).get_username() );
        textAreaOf_otherProductsDetailsDescription.setText( product.get_description() );

        // se premo ESC chiudo la vista dei dettagli del prodotto e torno alla vista dei prodotti degli altri venditori
        flowpaneOf_otherProductsDetailsInterface.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ESCAPE")) {
                close_otherProductsDetails();
            }
        });

    }

    @FXML public void copy_productID () {
        //! metodo che copia l'ID del prodotto nella clipboard

        ClipboardContent content = new ClipboardContent();
        content.putString( labelOf_otherProductsDetailsID.getText() );
        Clipboard.getSystemClipboard().setContent(content);
    
    }

    @FXML public void close_otherProductsDetails () {
        //! metodo che chiude la vista dei dettagli di un prodotto
        flowpaneOf_otherProductsInterface.toFront();
    }

}
