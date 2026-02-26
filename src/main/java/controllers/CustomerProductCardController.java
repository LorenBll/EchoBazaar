package main.java.controllers;

import javafx.fxml.Initializable;
import main.java.model.Product;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import main.java.model.ProductOrder;






public class CustomerProductCardController extends Controller implements Initializable {
    
    private Product myProduct;
    private CustomerPageController customerPageController;

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productPrice;
    @FXML private Label productOwner;

    boolean isAddToCartVisible = false;
    @FXML private ImageView addToCartButton;
    @FXML private Spinner<Integer> quantitySpinner;






    // inizializzazione del controller
    public void initialize ( java.net.URL location , java.util.ResourceBundle resources ) {
        //! metodo che inizializza il controller

        quantitySpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1) );
        quantitySpinner.setVisible(false);

        // se isAddStockVisible è true, e premo ESC, allora nascondo il campo di input per l'aggiunta di stock e ripo il bottone allo stato originale
        this.quantitySpinner.setOnKeyPressed( e -> {
            if ( e.getCode() == KeyCode.ESCAPE ) {
                this.quantitySpinner.setVisible(false);
                addToCartButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_shop.png") );
                isAddToCartVisible = false;
            }
        });

    }

    public void setup ( Product myProduct , CustomerPageController customerPageController ) {
        //! metodo che inizializza il controller
        
        this.myProduct = myProduct;
        this.customerPageController = customerPageController;
        
        productImage.setImage( new javafx.scene.image.Image(myProduct.get_pathOf_image()) );
        productName.setText(myProduct.get_name());
        productPrice.setText( myProduct.get_sellingPrice() + " €" );
        productOwner.setText( "By " + main.dataHandler.retrieve_vendorByID( myProduct.get_vendorID()).get_username() );

    }

    @FXML public void show_productDetails () {
        //! metodo che mostra i dettagli del prodotto
        customerPageController.show_productDetailsPage(myProduct);
    }

    @FXML public void add_toCart () {
        //! metodo che aggiunge il prodotto al carrello

        if ( !isAddToCartVisible ) {
            quantitySpinner.setVisible(true);
            addToCartButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_confirmRestockAmount.png") );
            quantitySpinner.requestFocus();
            isAddToCartVisible = true;
            return;
        }

        //. se è già stato premuto il bottone, allora aggiungo il prodotto al carrello
        int quantity = quantitySpinner.getValue();
        if ( myProduct.get_currentStock() < quantity ) {
            customerPageController.show_vendorProductWarning("Not enough stock.");
            // resetto
            quantitySpinner.setVisible(false);
            addToCartButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_shop.png") );
            isAddToCartVisible = false;            
            return;
        }
        if ( main.loggedInCustomer.get_balance() < myProduct.get_sellingPrice() * quantity ) {
            customerPageController.show_vendorProductWarning("Not enough balance.");
            // resetto
            quantitySpinner.setVisible(false);
            addToCartButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_shop.png") );
            isAddToCartVisible = false;
            return;
        }

        // resetto
        quantitySpinner.setVisible(false);
        addToCartButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_shop.png") );
        isAddToCartVisible = false;

        ProductOrder newOrder = new ProductOrder( myProduct , quantity );
        customerPageController.add_productToCart(newOrder);
        customerPageController.show_vendorProductWarning("Product added to cart."); 

    }

    

}
