package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import main.java.auth.CryptingEngine;
import main.java.model.users.Customer;
import main.java.model.users.Vendor;
import javafx.application.Platform;






public class RegistrationPageController extends Controller {

    @FXML private TextField textFieldOf_username;
    @FXML private Label labelOf_error; // label che mostra un messaggio di errore
    
    //. attributi relativi all'input della password
    public boolean isPasswordShown = false;
    @FXML private PasswordField passwordField;
    @FXML private Label labelOf_passwordShower;
    @FXML private ImageView imageOf_showOrHide_password; // immagine che verrà mostrata all'interno del pulsante

    @FXML private RadioButton radioButtonOf_accountTypeSelection_customer;
    @FXML private RadioButton radioButtonOf_accoutnTypeSelection_vendor;






    // metodo che viene chiamato automaticamente all'avvio del programma
    @Override @FXML public void initialize () {

        // se premo invio mentre sono nel campo di testo dello username, passo al campo di testo della password
        textFieldOf_username.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                passwordField.requestFocus();
            }
        });

        // se premo invio mentre sono nel campo di testo della password, mi sposto sui radio button
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                radioButtonOf_accountTypeSelection_customer.requestFocus();
            }
        });

    }

    // usato da pulsante "Show/Hide Password" (occhio/occhio sbarrato) : NON DI PASSWORD DI CONFERMA
    @FXML private void showOrHide_password () {
        //! metodo che mostra o nasconde la password a seconda dello stato attuale della password (mostrata o non mostrata)        
        if (!isPasswordShown) {
            labelOf_passwordShower.setVisible(true);
            passwordField.setVisible(false);
            isPasswordShown = true;

            imageOf_showOrHide_password.setImage(new Image("/main/resources/images/iconOf_hidePassword.png")); // cambia l'icona del pulsante in "nascondi password"
        
            return;
        }
        
        labelOf_passwordShower.setVisible(false);
        passwordField.setVisible(true);
        isPasswordShown = false;

        // il passwordField richiede il focous senza però selezionare il testo e sposta il cursore alla fine del testo
        passwordField.requestFocus();
        passwordField.deselect();
        passwordField.end();
        
        imageOf_showOrHide_password.setImage(new Image("/main/resources/images/iconOf_showPassword.png")); // cambia l'icona del pulsante in "mostra password"
    
    }

    // usato da passwordField ad ogni variazione di testo : NON DI PASSWORD DI CONFERMA
    @FXML private void update_labelOf_passwordShower () {
        //! metodo che aggiorna il testo della label che mostra la password
        labelOf_passwordShower.setText(passwordField.getText());
    }

    // usato da pulsante "Go Back to Login"
    @FXML private void show_loginPage () {
        //! metodo che visualizza la pagina di login
        main.show_loginPage();
    }
    
    // usato da pulsante "Complete" (registration)
    @FXML private void register () {
        //! metodo che completa la registrazione

        // controllo che l'username non sia vuoto
        if (textFieldOf_username.getText().equals("")) {
            show_error("Username cannot be empty.");
            return;
        }

        // controllo che la password non sia vuota
        if (passwordField.getText().equals("")) {
            show_error("Password cannot be empty.");
            return;
        }

        // controllo che lo username non contenga spazi
        if (textFieldOf_username.getText().contains(" ")) {
            show_error("Username cannot contain spaces.");
            return;
        }

        // controllo che lo username non contenga caratteri speciali: |!@#$%^&*()+{}:"<>?|[];',.
        boolean containsSpecialCharacters = false;
        for (char c : "\\|!@#$%^&*()+{}:\"<>?|[];',.".toCharArray()) {
            if (textFieldOf_username.getText().contains(String.valueOf(c))) {
                containsSpecialCharacters = true;
                break;
            }
        }
        if (containsSpecialCharacters) {
            show_error("Username can't contain special characters.");
            return;
        }

        // controllo che lo username non sia più lungo di 15 caratteri
        if (textFieldOf_username.getText().length() > 12) {
            show_error("Username cannot be longer than 12 characters.");
            return;
        }

        // controllo che la password non sia più lunga di 15 caratteri
        if (passwordField.getText().length() > 12) {
            show_error("Password cannot be longer than 12 characters.");
            return;
        }

        String chosenUsername = textFieldOf_username.getText();
        String chosenPassword = passwordField.getText();
        String encryptedPassword = CryptingEngine.encrypt_string(chosenPassword);
        boolean isCustomer = radioButtonOf_accountTypeSelection_customer.isSelected();

        if (isCustomer) {
        
            Customer newCustomer = new Customer(chosenUsername, encryptedPassword);
            if (!main.dataHandler.register_customer(newCustomer)) {
                show_error("Username already taken.");
                return;
            }
        
        }
        else {
            
            Vendor newVendor = new Vendor(chosenUsername, encryptedPassword);
            if (!main.dataHandler.register_vendor(newVendor)) {
                show_error("Username already taken.");
                return;
            }

        }
        show_loginPage();

    }
        





    // === METODI DI UTILITÀ AKA DA NON MODIFICARE ===
    private void show_error ( String error ) {
        //! metodo che mostra un messaggio di errore
        
        Platform.runLater(() -> {
            labelOf_error.setText(error);

            // faccio partire un timer che aspetta 3 secondi
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> labelOf_error.setText(""));
                        }
                    },
                    3000
            );
        });

    }

    // usato da pulsante "X" e pulsante "Cancel"
    @FXML private void close_window () {
        //! metodo che chiude il programma
        System.exit(0);
    }

}
