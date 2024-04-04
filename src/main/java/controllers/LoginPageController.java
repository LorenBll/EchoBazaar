package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;






public class LoginPageController extends Controller {

    public boolean isPasswordVisible = false;



    @FXML private TextField textFieldOf_username;
    
    //. elementi relativi all'input della password
    @FXML private PasswordField passwordField;
    @FXML private Label labelOf_passwordShower;
    @FXML private ImageView imageOf_showOrHide_password; // immagine che verrà mostrata all'interno del bottone






    @FXML private void showOrHide_password () {
        
        if (!isPasswordVisible) {
            labelOf_passwordShower.setVisible(true);
            isPasswordVisible = true;
            imageOf_showOrHide_password.setImage(new Image("/main/resources/images/iconOf_hidePassword.png"));
            return;
        }
        
        imageOf_showOrHide_password.setImage(new Image("/main/resources/images/iconOf_showPassword.png"));
        labelOf_passwordShower.setVisible(false);
        isPasswordVisible = false;
    
    }

    @FXML private void update_labelOf_passwordShower () {
        labelOf_passwordShower.setText(passwordField.getText());
    }

    @FXML private void close_window () {
        System.exit(0);
    }

}