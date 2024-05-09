package main.java.controllers;

import javafx.fxml.Initializable;
import main.java.model.Product;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;






public class CustomerProductCardController extends Controller implements Initializable {
    
    private Product myProduct;
    private CustomerPageController customerPageController;

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productPrice;
    @FXML private Label productOwner;





    // metodo che viene chiamato automaticamente quando il controller viene inizializzato
    @Override public void initialize ( java.net.URL location , java.util.ResourceBundle resources ) {
        // todo metodo che viene chiamato automaticamente quando il controller viene inizializzato
    }

    public void setup ( Product myProduct , CustomerPageController customerPageController ) {
        // todo metodo che inizializza il controller
        
        this.myProduct = myProduct;
        this.customerPageController = customerPageController;
        
        productImage.setImage( new javafx.scene.image.Image(myProduct.get_pathOf_image()) );
        productName.setText(myProduct.get_name());
        productPrice.setText( myProduct.get_sellingPrice() + " $" );
        productOwner.setText( "By " + main.dataHandler.retrieve_vendorByID( myProduct.get_vendorID()).get_username() );

    }

}
