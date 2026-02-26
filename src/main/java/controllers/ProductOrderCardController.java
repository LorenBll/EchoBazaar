package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import main.java.model.ProductOrder;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.control.SpinnerValueFactory;






public class ProductOrderCardController extends Controller {
    
    ProductOrder myProductOrder;
    CustomerPageController customerPageController;

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label orderPrice;

    boolean isAddAmountVisible = false;
    @FXML private ImageView addAmountButton;
    @FXML private Spinner<Integer> quantitySpinner;






    // inizializzazione del controller
    public void initialize () {
        //! metodo che inizializza il controller

        quantitySpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1) );
        quantitySpinner.setVisible(false);

        // se isAddAmountVisible è true, e premo ESC, allora nascondo il campo di input per l'aggiunta di stock e ripo il bottone allo stato originale
        this.quantitySpinner.setOnKeyPressed( e -> {
            if ( e.getCode() == KeyCode.ESCAPE ) {
                this.quantitySpinner.setVisible(false);
                addAmountButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
                quantitySpinner.getValueFactory().setValue( myProductOrder.get_quantity() );
                isAddAmountVisible = false;
            }
        });

    }

    public void setup ( ProductOrder myProductOrder , CustomerPageController customerPageController ) {
        //! metodo che inizializza il controller
        
        this.myProductOrder = myProductOrder;
        this.customerPageController = customerPageController;
        
        productImage.setImage( new javafx.scene.image.Image(myProductOrder.get_product().get_pathOf_image()) );
        productName.setText( myProductOrder.get_product().get_name() + " ( " + myProductOrder.get_quantity() + " )");
        orderPrice.setText( myProductOrder.get_totalPrice() + " €" );

        // il valore di default dello spinner è la quantità corrente dell'ordine
        quantitySpinner.getValueFactory().setValue( myProductOrder.get_quantity() );

    }

    @FXML public void delete_order () {
        //! metodo che elimina l'ordine
        customerPageController.delete_productOrderFromCart( myProductOrder );
    }

    @FXML public void add_orderAmount () {
        //! metodo che aggiunge la quantità dell'ordine

        if ( !isAddAmountVisible ) {
            quantitySpinner.setVisible(true);
            addAmountButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_confirmRestockAmount.png") );
            quantitySpinner.requestFocus();
            isAddAmountVisible = true;
            return;
        }

        //. se è già stato premuto il bottone di conferma, allora aggiungo la quantità
        if ( quantitySpinner.getValue() == 0 ) {
            customerPageController.delete_productOrderFromCart( myProductOrder );
            return;
        }
        if ( myProductOrder.get_quantity() == quantitySpinner.getValue() ) {
            // resetto
            quantitySpinner.setVisible(false);
            addAmountButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
            isAddAmountVisible = false;
            return;
        }
        if ( myProductOrder.get_product().get_currentStock() < quantitySpinner.getValue() ) {
            customerPageController.show_cartWarning("Not enough stock.");
            // resetto
            quantitySpinner.setVisible(false);
            quantitySpinner.getValueFactory().setValue( myProductOrder.get_quantity() );
            addAmountButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
            isAddAmountVisible = false;            
            return;
        }
        float totalProductOrderPrice = myProductOrder.get_product().get_sellingPrice() * quantitySpinner.getValue();
        if ( main.loggedInCustomer.get_balance() < totalProductOrderPrice + (customerPageController.calculate_totalOrdersPrice() - myProductOrder.get_totalPrice() ) ){
            customerPageController.show_cartWarning("Not enough balance to Buy Everything in the Cart.");
            // resetto
            quantitySpinner.setVisible(false);
            quantitySpinner.getValueFactory().setValue( myProductOrder.get_quantity() );
            addAmountButton.setImage( new javafx.scene.image.Image("/main/resources/images/iconOf_addStock.png") );
            isAddAmountVisible = false;
            return;
        }
        
        myProductOrder.set_quantity( quantitySpinner.getValue() );
        customerPageController.fill_cart();
        
        // non è necessario resettare nulla, perchè tutto verrà resettato automaticamente quando verrà chiamato il metodo fill_cart()

    }

}
