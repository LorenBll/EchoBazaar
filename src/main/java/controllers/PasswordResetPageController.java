package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;






public class PasswordResetPageController extends Controller {

    @FXML private TextField textFieldOf_ID;
    @FXML private Label labelOf_error; // label che mostra un messaggio di errore
    
    //. attributi relativi all'input della password
    public boolean isPasswordShown = false;
    @FXML private PasswordField passwordField;
    @FXML private Label labelOf_passwordShower;
    @FXML private ImageView imageOf_showOrHide_password; // immagine che verrà mostrata all'interno del pulsante
    @FXML private Label labelOf_wrongPassword; // label che mostra un messaggio di errore se la password è sbagliata






    @Override @FXML public void initialize () {

        // se premo invio mentre sono nel campo di testo dell'ID, passo al campo di testo della password
        textFieldOf_ID.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                passwordField.requestFocus();
            }
        });

        // se premo invio mentre sono nel campo di testo della password, effettuo il reset
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                reset();
            }
        });

    }

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

    // usato da pulsante "Back To Login"
    @FXML private void show_loginPage () {
        //! metodo che mostra la pagina di login
        main.show_loginPage();
    }

    // usato da pulsante "Confirm Reset"
    @FXML private void reset () {
        //! metodo che effettua il reset della password dell'utente

        //. controllo che l'ID non sia vuoto
        if (textFieldOf_ID.getText().equals("")) {
            show_error("User ID cannot be empty.");
            return;
        }

        //. controllo che la password non sia vuota
        if (passwordField.getText().equals("")) {
            show_error("New Password cannot be empty.");
            return;
        }

        String inputtedID = textFieldOf_ID.getText();
        String inputtedPassword = passwordField.getText();

        //. effettuo il reset della password
        boolean passwordResetResult = main.dataHandler.reset_password(inputtedID, inputtedPassword);
        if (passwordResetResult) {
            // se il reset è andato a buon fine, mostro la pagina di login
            main.show_loginPage();
            return;
        }

        // se il reset non è andato a buon fine, mostro un messaggio di errore
        show_error("Wrong ID used.");
        
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

}