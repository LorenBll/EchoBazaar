package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.java.model.Product;
import javafx.scene.control.Spinner;
import javafx.fxml.Initializable;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;






public class VendorProductCardController extends Controller implements Initializable {
    
    public Product myProduct;
    public VendorPageController vendorPageController;

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productStock;
    @FXML private Label autoRestock;

    boolean isAddStockVisible = false;
    @FXML private ImageView imageviewOf_add_stock;
    @FXML private Spinner<Integer> arrivalQuantity;






    // metodo che viene chiamato automaticamente quando il controller viene inizializzato
    @Override public void initialize ( java.net.URL location , java.util.ResourceBundle resources ) {
        //! metodo che viene chiamato automaticamente quando il controller viene inizializzato
        this.arrivalQuantity.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 5) );
        this.arrivalQuantity.setVisible(false);

        // se isAddStockVisible è true, e premo ESC, allora nascondo il campo di input per l'aggiunta di stock e ripo il bottone allo stato originale
        this.arrivalQuantity.setOnKeyPressed( e -> {
            if ( e.getCode() == KeyCode.ESCAPE ) {
                this.arrivalQuantity.setVisible(false);
                imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
                isAddStockVisible = false;
            }
        });

    }

    public void setup ( Product myProduct , VendorPageController vendorPageController ) {
        //! metodo che inizializza il controller
        
        this.myProduct = myProduct;
        this.vendorPageController = vendorPageController;
        
        this.productImage.setImage( new javafx.scene.image.Image(myProduct.get_pathOf_image()) );
        this.productName.setText(myProduct.get_name());

        this.productStock.setText("Stock: " + myProduct.get_currentStock());
        
        String autoRestockStatus = "";
        if ( myProduct.get_autoRestock() ) {
            autoRestockStatus = "ON";
        } else {
            autoRestockStatus = "OFF";
        }
        this.autoRestock.setText("Auto restock: " + autoRestockStatus);
        
    }






    @FXML public void handle_stockAddition () {
        //! metodo che aggiunge stock al prodotto

        if ( !isAddStockVisible ) {
            this.arrivalQuantity.setVisible(true);
            imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_confirmRestockAmount.png") );
            arrivalQuantity.requestFocus();
            isAddStockVisible = true;
            return;
        }

        int quantity = arrivalQuantity.getValue();
        myProduct.add_stock(quantity);
        main.dataHandler.update_productFile();
        this.productStock.setText("Stock: " + myProduct.get_currentStock());
        imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
        this.arrivalQuantity.setVisible(false);

    }

    @FXML public void handle_productEdit () {
        //! metodo che apre la finestra di modifica del prodotto
        vendorPageController.show_productEditPage(myProduct);
    }

}
