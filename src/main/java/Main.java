package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;
import javafx.event.EventHandler;

import main.java.controllers.*;

public class Main extends Application {
    
    private Stage primaryStage;
    private BorderPane mainLayout;






    public void start ( Stage primaryStage ) {

        this.primaryStage = primaryStage;
        
        //. settaggio impostazioni della finestra principale
        this.primaryStage.setTitle("EchoBazaar");
        this.primaryStage.setResizable(false);
        this.primaryStage.initStyle(StageStyle.UNDECORATED); // rimuove i bordi della finestra (barra del titolo, pulsanti di chiusura, ecc.)
        this.primaryStage.getIcons().add(new Image("/main/resources/images/iconOf_echoBazaar.png")); // icona della finestra (mostrata nella barra delle applicazioni)
        
        show_loginPage();
        
        //. key listener per annullare il focus quando si preme il tasto ESC
        mainLayout.setOnKeyPressed(event -> {
            
            if (event.getCode() == KeyCode.ESCAPE) {
                Node focusedNode = mainLayout.getScene().getFocusOwner();
                if (focusedNode != mainLayout)
                    focusedNode.getParent().requestFocus(); // annulla il focus
            }
        
        });
    
    }

    public void show_loginPage () {
        try {

            cancel_defaultFocus();

            // caricamento del file fxml del login
            URL locationOf_fxml = getClass().getResource("/main/resources/fxml/Login.fxml");
            FXMLLoader loader = new FXMLLoader(locationOf_fxml);
            this.mainLayout = loader.load();

            // visualizzazione della scena contenente il layout del login
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            
            // settaggio del controller della pagina di login
            LoginPageController controller = loader.getController();
            controller.set_main(this);

            primaryStage.show();
    
        } catch (Exception e) { e.printStackTrace(); }
    }






    public Stage get_primaryStage () {
        return this.primaryStage;
    }
    
    public BorderPane get_mainLayout () {
        return this.mainLayout;
    }
    
    public void cancel_defaultFocus () {
        primaryStage.setOnShown(e -> primaryStage.getScene().getRoot().requestFocus()); // focus sulla finestra principale per annullare il focus di default
    }

        class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");

            closeBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            this.getChildren().add(closeBtn);
        }
    }

    public static void main ( String[] args ) { launch(args); }
    
}