package main.java.controllers;

import main.java.Main;
import javafx.fxml.FXML;

public abstract class Controller {

    protected Main main;

    @FXML public void initialize () {}

    public void set_main ( Main main ) {
        this.main = main;
    }

}