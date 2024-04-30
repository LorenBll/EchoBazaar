package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;






public class MainPageController extends Controller {
    
    public boolean isFullscreen = false; // indica se la finestra è in modalità fullscreen o no
    @FXML private ImageView imageOf_fullscreenOperations;

    @FXML private Label labelOf_username;
    @FXML private Label labelOf_balance;

    @FXML private FlowPane flowpaneOf_commonFunctions;





    public void setup () {
        //! metodo che viene chiamato dal main quando la finestra è stata caricata ed il main settato >>> con l'initialize non funziona perché il main non è ancora settato 

        //. settaggio delle informazioni dell'utente loggato
        if (main.loggedInCustomer != null) {

            labelOf_username.setText( main.loggedInCustomer.get_username() );

            double balance = main.loggedInCustomer.get_balance();
            String shortenedBalance = "";
            if ( balance > 1.0e6 )
                shortenedBalance = String.format("%.2f M", balance/1.0e6);
            else if ( balance > 1.0e3 ) 
                shortenedBalance = String.format("%.2f k", balance/1.0e3);
            else 
                shortenedBalance = String.format("%.2f ", balance);
            labelOf_balance.setText( shortenedBalance + "$" );

        }

        // todo . settaggio delle informazioni del venditore loggato
        
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
