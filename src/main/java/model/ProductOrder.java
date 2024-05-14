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

    public int set_quantity ( int newQuantity ) {
        //! metodo che setta la quantità
        this.quantity = newQuantity;
        return this.quantity;
    }

    public double get_totalPrice () {
        //! metodo che restituisce il prezzo totale
        return this.product.get_sellingPrice() * this.quantity;
    }

}
