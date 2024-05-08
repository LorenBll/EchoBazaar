package main.java.model;






public class ProductOrder {
    
    public Product product;
    public int quantity;

    public ProductOrder ( Product product , int quantity ) {
        //! metodo costruttore
        this.product = product;
        this.quantity = quantity;
    }

    public Product get_product () {
        //! metodo che restituisce il prodotto
        return this.product;
    }

    public int get_quantity () {
        //! metodo che restituisce la quantità
        return this.quantity;
    }



    

}
