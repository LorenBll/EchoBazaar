package main.java.controllers;

import main.java.Main;
import javafx.fxml.FXML;






public abstract class Controller {

    protected Main main;





    
    @FXML public void initialize () {
        // questo è scritto in in questa classe astratta per evitare di doverlo scrivere
        // in ogni controller che estende questa classe che lo riconterrebbe vuoto. 
    }

    public void set_main ( Main main ) {
        this.main = main;
    }

}