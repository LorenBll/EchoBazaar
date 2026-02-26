package main.java.controllers;

import main.java.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;






public class VendorNotOwnerProductCardController extends Controller {

    private Product myProduct;
    private VendorPageController vendorPageController;

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productPrice;
    @FXML private Label productOwner;






    public void setup ( Product myProduct , VendorPageController vendorPageController ) {
        //! metodo che inizializza il controller

        this.myProduct = myProduct;
        this.vendorPageController = vendorPageController;

        productImage.setImage( new javafx.scene.image.Image(myProduct.get_pathOf_image()) );
        productName.setText(myProduct.get_name());
        productPrice.setText( myProduct.get_sellingPrice() + " €" );
        productOwner.setText( "By " + main.dataHandler.retrieve_vendorByID( myProduct.get_vendorID()).get_username() );
        
    }

    @FXML public void show_productDetails () {
        //! metodo che mostra i dettagli del prodotto
        vendorPageController.show_productDetailsPage(myProduct);
    }

    @FXML public void copy_ID () {
        //! metodo che copia l'ID del prodotto nella clipboard
    
        ClipboardContent content = new ClipboardContent();
        content.putString( myProduct.get_ID() );
        Clipboard.getSystemClipboard().setContent(content);
    
    }

}
