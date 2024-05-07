package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.application.Platform;






public class CustomerPageController extends Controller {

    //. barra di gestione della finestra (minimizza, chiudi e fullscreen)
    public boolean isFullscreen = false; // indica se la finestra è in modalità fullscreen o no
    @FXML private ImageView imageOf_fullscreenOperations;

    //. riquadro delle informazioni relative al customer loggato e delle funzioni basilari che può svolgere
    @FXML private Label labelOf_username;
    @FXML private Label labelOf_balance;
    @FXML private FlowPane flowpaneOf_commonFunctions;

    
    //. riquadro in cui verrano mostrate le varie viste
    @FXML private StackPane stackpaneOf_customerViews;
    
    //. vista di benvenuto
    @FXML private FlowPane flowpaneOf_welcome;

    //. vista delle impostazioni
    boolean isCustomerPasswordShown = false;
    int counterOfDeleteAccount = 0;
    @FXML private FlowPane flowpaneOf_settings;
    @FXML private Label labelOf_customerID;
    @FXML private TextField textfieldOf_customerUsername;
    @FXML private PasswordField passwordfieldOf_customerPassword;
    @FXML private ImageView imageOf_showOrHide_customerPassword;
    @FXML private Label labelOf_customerPasswordShower;
    @FXML private Label labelOf_settingsWarning;
    @FXML private Button buttonOf_settingsDeleteAccount;

    //. vista della ricarica del saldo
    @FXML private FlowPane flowpaneOf_depositInterface;
    @FXML private TextField textfieldOf_moneyQuantity;
    @FXML private Label labelOf_depositInterfaceWarning;






    public void setup () {
        //! metodo che viene chiamato dal main quando la finestra è stata caricata ed il main settato >>> con l'initialize non funziona perché il main non è ancora settato 

        String shortenedBalance = shorten_balance( main.loggedInCustomer.get_balance() );

        // setto le informazioni del customer loggato
        labelOf_username.setText( main.loggedInCustomer.get_username() );
        labelOf_balance.setText( shortenedBalance + "$" );

        main.get_primaryStage().setFullScreenExitKeyCombination(null); // rimuove la combinazione di tasti per uscire dalla modalità fullscreen
        show_welcome();

    }

    private String shorten_balance ( float balance ) {
        //! metodo che accorcia il balance se è troppo lungo, ma non lo arrotonda
        String shortenedBalance = "";
        if (balance >= 1000000000) {
            shortenedBalance = String.format("%.2fB", balance / 1000000000);
        }
        else if (balance >= 1000000) {
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






    //. interfaccia delle impostazioni
    @FXML private void show_settings () {
        //! metodo che mostra la vista delle impostazioni

        counterOfDeleteAccount = 0;
        buttonOf_settingsDeleteAccount.setText("Delete Account");
        flowpaneOf_settings.toFront();
        labelOf_customerID.setText( main.loggedInCustomer.get_ID() );

        // se sono sul focus del textfield dello username e premo invio, passo al passwordField
        textfieldOf_customerUsername.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                passwordfieldOf_customerPassword.requestFocus();
            }
        });

        // se sono sul focus del passwordField e premo invio, salvo le impostazioni
        passwordfieldOf_customerPassword.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                save_settings();
            }
        });

        // se sono sul focus del textfield della password e premo invio, salvo le impostazioni
        labelOf_customerPasswordShower.setOnKeyPressed( e -> {
            if (e.getCode().toString().equals("ENTER")) {
                save_settings();
            }
        });

    }

    @FXML private void copy_ID () {
        //! metodo che copia l'ID del customer negli appunti
        
        ClipboardContent content = new ClipboardContent();
        content.putString( main.loggedInCustomer.get_ID() );
        Clipboard.getSystemClipboard().setContent(content);

    }

    // usato da pulsante "Show/Hide Password" (occhio/occhio sbarrato)
    @FXML private void showOrHide_password () {
        //! metodo che mostra o nasconde la password a seconda dello stato attuale della password (mostrata o non mostrata)        
        if (!isCustomerPasswordShown) {
            labelOf_customerPasswordShower.setVisible(true);
            passwordfieldOf_customerPassword.setVisible(false);
            isCustomerPasswordShown = true;

            imageOf_showOrHide_customerPassword.setImage(new Image("/main/resources/images/iconOf_hidePassword.png")); // cambia l'icona del pulsante in "nascondi password"
        
            return;
        }
        
        labelOf_customerPasswordShower.setVisible(false);
        passwordfieldOf_customerPassword.setVisible(true);
        isCustomerPasswordShown = false;

        // il passwordField richiede il focous senza però selezionare il testo e sposta il cursore alla fine del testo
        passwordfieldOf_customerPassword.requestFocus();
        passwordfieldOf_customerPassword.deselect();
        passwordfieldOf_customerPassword.end();
        
        imageOf_showOrHide_customerPassword.setImage(new Image("/main/resources/images/iconOf_showPassword.png")); // cambia l'icona del pulsante in "mostra password"
    
    }

    // usato da passwordField ad ogni variazione di testo
    @FXML private void update_labelOf_customerPasswordShower () {
        //! metodo che aggiorna costantemente il labelOf_customerPasswordShower
        labelOf_customerPasswordShower.setText( passwordfieldOf_customerPassword.getText() );
    }

    @FXML private void save_settings () {
        //! metodo che salva le impostazioni del customer
        
        String newUsername = textfieldOf_customerUsername.getText();
        String newPassword = passwordfieldOf_customerPassword.getText();

        if (newUsername.equals("") && newPassword.equals("")) {
            show_settingsWarning("No Changes Were Made.");
            return;
        }

        // controllo che lo username non contenga caratteri speciali: |!@#$%^&*()+{}:"<>?|[];',.
        boolean containsSpecialCharacters = false;
        for (char c : "\\|!@#$%^&*()+{}:\"<>?|[];',.".toCharArray()) {
            if (textfieldOf_customerUsername.getText().contains(String.valueOf(c))) {
                containsSpecialCharacters = true;
                break;
            }
        }
        if (containsSpecialCharacters) {
            show_settingsWarning("Username can't contain special characters.");
            return;
        }

        // controllo se lo username contiene spazi oppure è più lungo di 15 caratteri
        if ( newUsername.contains(" ") || newUsername.length() > 15 ) {
            show_settingsWarning("Username must be less than 15 characters and contain no spaces.");
            return;
        }

        // controllo se la password è più lunga di 15 caratteri
        if ( newPassword.length() > 15 ) {
            show_settingsWarning("Password must be less than 15 characters and contain no spaces.");
            return;
        }

        boolean resultOf_saveSettings = main.dataHandler.update_customer( main.loggedInCustomer.get_ID(), newUsername, newPassword );
        if (!resultOf_saveSettings) {
            show_settingsWarning("Username Already Taken.");
            return;
        }

        reset_settingsView();
        show_settingsWarning("Settings Saved.");
        labelOf_username.setText( main.loggedInCustomer.get_username() );

    }

    // usato da pulsante "Reset"
    @FXML private void reset_settingsView () {
        //! metodo che resetta la vista delle impostazioni
        textfieldOf_customerUsername.setText( "" );
        passwordfieldOf_customerPassword.setText( "" );
        if (isCustomerPasswordShown)
            showOrHide_password();
        // cancello il focus che altrimenti rimarrebbe, per il metodo showOrHide_password(), sul passwordField
        flowpaneOf_settings.requestFocus();
    }

    // usato da pulsante "Delete Account"
    @FXML private void delete_account () {
        //! metodo che elimina l'account del customer loggato

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
        main.dataHandler.delete_customer( main.loggedInCustomer.get_username() );
        counterOfDeleteAccount = 0;
        main.loggedInCustomer = null;
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
        labelOf_balance.setText( shorten_balance( main.loggedInCustomer.get_balance() ) + "$" );

        main.dataHandler.update_customerFile();

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
        labelOf_balance.setText( shorten_balance( main.loggedInCustomer.get_balance() ) + "$" );

        main.dataHandler.update_customerFile();

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

    //. === METODI DI UTILITÀ AKA DA NON MODIFICARE ===
    @FXML private void show_welcome () {
        //! metodo che mostra il riquadro di benvenuto
        flowpaneOf_welcome.toFront();
    }
    
    @FXML private void logout () {
        //! metodo che effettua il logout
        main.loggedInCustomer = null;
        main.show_loginPage();
    }
    
    // usato da pulsante "X"
    @FXML private void close_window () {
        //! metodo che chiude la finestra
        System.exit(0);
    }

    // usato da pulsante "Fullscreen" (due freccie in diagonale opposte e puntanti verso l'esterno, oppure due freccie in diagonale opposte e puntanti verso l'interno)
    @FXML private void enterOrExit_fullscreen () {
        //! metodo che massimizza o minimizza la finestra, e effettua le operazioni necessarie per adattare il layout alla modalità fullscreen
        
        //. passaggio a fullscreen
        if (!isFullscreen) {

            isFullscreen = true;
            main.enter_fullscreen();
            imageOf_fullscreenOperations.setImage(new javafx.scene.image.Image("/main/resources/images/iconOf_fullScreenExit.png"));

            return;
        }

        //. passaggio a windowed (non fullscreen)
        isFullscreen = false;
        main.exit_fullscreen();
        imageOf_fullscreenOperations.setImage(new javafx.scene.image.Image("/main/resources/images/iconOf_fullScreenEnter.png"));
        
    }
    
    // usato da pulsante "Minimize" (linea orizzontale)
    @FXML private void minimize_window () {
        //! metodo che minimizza la finestra
        main.minimize_window();
    }

    public void update () { setup(); }

}
