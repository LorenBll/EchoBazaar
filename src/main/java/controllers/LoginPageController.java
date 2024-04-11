package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.auth.CryptingEngine;






public class LoginPageController extends Controller {

    @FXML private TextField textFieldOf_username;
    
    //. attributi relativi all'input della password
    public boolean isPasswordShown = false;
    @FXML private PasswordField passwordField;
    @FXML private Label labelOf_passwordShower;
    @FXML private ImageView imageOf_showOrHide_password; // immagine che verrà mostrata all'interno del pulsante






    // usato da pulsante "Show/Hide Password" (occhio/occhio sbarrato)
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

    // usato da passwordField ad ogni variazione di testo
    @FXML private void update_labelOf_passwordShower () {
        //! metodo che aggiorna il testo della label che mostra la password
        labelOf_passwordShower.setText(passwordField.getText());
    }



    // usato da pulsante "X" e pulsante "Cancel"
    @FXML private void close_window () {
        //! metodo che chiude il programma
        System.exit(0);
    }

    // usato da pulsante "Register"
    @FXML private void show_registrationPage () {
        //! metodo che mostra la pagina di registrazione
        main.show_registrationPage();
    }

    // todo usato da pulsante "Login"
    @FXML private void login () {
        //! metodo che effettua il login dell'utente

        //. recupero delle credenziali inserite dall'utente
        String username = textFieldOf_username.getText();
        String password = passwordField.getText();

        //todo controllo delle credenziali --> recupera lo username dal file di testo e confronta la password inserita con quella salvata
        String insertedPassword = CryptingEngine.encrypt_string(password);
    
    }

    // todo usato da pulsante "Forgot Password?"
    @FXML private void show_forgotPasswordPage () {
        //! metodo che mostra la pagina di recupero password
        
    }

}