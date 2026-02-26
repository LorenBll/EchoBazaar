package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.java.model.Product;
import javafx.scene.control.Spinner;
import javafx.fxml.Initializable;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import main.java.model.ProductOrder;






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

        // se il campo di input per l'aggiunta di stock non è visibile, allora lo visualizzo
        if ( !isAddStockVisible ) {
            this.arrivalQuantity.setVisible(true);
            arrivalQuantity.requestFocus();
            imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_confirmRestockAmount.png") );
            isAddStockVisible = true;
            return;
        }

        //. se il campo di input per l'aggiunta di stock è visibile, allora proseguo con il procedimento normale
        
        if ( arrivalQuantity.getValue() == 0 ) { // se non ha senso comprare, resetto
            this.arrivalQuantity.setVisible(false);
            imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
            isAddStockVisible = false;
            return;
        }

        if ( !myProduct.get_autoRestock() ) { // se l'autorestock non è attivo devo semplicemente aggiornare il database

            // aggiorno la quantità
            int quantity = arrivalQuantity.getValue();
            myProduct.set_currentStock( myProduct.get_currentStock() + quantity );
            main.dataHandler.update_productFile();
            this.productStock.setText("Stock: " + myProduct.get_currentStock());
            
            // resetto
            this.arrivalQuantity.setVisible(false);
            imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
            isAddStockVisible = false;
            arrivalQuantity.getValueFactory().setValue(1);
            
            return;
        }

        // se l'autorestock è attivo, devo creare un ordine di restock
        ProductOrder newOrder = new ProductOrder ( main.dataHandler.retrieve_productByID( myProduct.get_sourceID() ) , arrivalQuantity.getValue() );
        if ( main.dataHandler.vendorBuy_product(newOrder, myProduct.get_vendorID() , myProduct)) {
            
            // aggiorno la quantità del prodotto
            vendorPageController.fill_storageGrid();

            // aggiorno il saldo del venditore
            vendorPageController.update_balance();

            // resetto
            this.arrivalQuantity.setVisible(false);
            imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
            isAddStockVisible = false;
            arrivalQuantity.getValueFactory().setValue(1);
            
            return;
        }

        // se siamo arrivati qui, la quantità richiesta non è disponibile oppure il venditore ordinante non ha abbastanza soldi
        vendorPageController.show_storageWarning("Not enough stock available or not enough money to buy.");
        
        // resetto
        this.arrivalQuantity.setVisible(false);
        imageviewOf_add_stock.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
        isAddStockVisible = false;
        arrivalQuantity.getValueFactory().setValue(1);

    }

    @FXML public void handle_productEdit () {
        //! metodo che apre la finestra di modifica del prodotto
        vendorPageController.show_productEditPage(myProduct);
    }

}
