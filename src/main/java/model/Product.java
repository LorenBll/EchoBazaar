package main.java.model;

import main.java.auth.CryptingEngine;






public class Product {
    
    private String vendorID;        // ID del venditore >>> non è necessario avere un riferimento diretto al venditore, basta l'ID
    
    private String ID;
    private String name;
    private String description;
    private String pathOf_image;

    private float sellingPrice;
    private int stock;
    
    private boolean autoRestock;    // se true, il prodotto viene automaticamente rifornito rifornito quando il numero di pezzi disponibili scende sotto minStock
    private int minStock;           // se autoRestock è true, minStock indica il numero minimo di pezzi che devono essere sempre disponibili
    private int restockAmount;      // se autoRestock è true, restockAmount indica il numero di pezzi che vengono acquistati ad ogni rifornimento

    private boolean isClone;        // se true, il prodotto è un clone di un altro prodotto
    private String sourceID;        // ID del prodotto di origine (se il prodotto è un clone di un altro prodotto)
    private float sourcePrice;      // prezzo del prodotto di origine (se il prodotto è un clone di un altro prodotto)






    private String generate_ID () {
        //! metodo che genera un ID univoco per il prodotto
        // non viene effettuato il controllo sull'unicità dell'ID generato perché è molto improbabile che si generino due ID uguali:
            // richiederebbe che due prodotti vengano inseriti esattamente nello stesso millisecondo e con lo stesso nome 
            // (inoltre EchoBazaar funziona in locale e non ha un server connesso a internet, quindi è inverificabile)
        
        return CryptingEngine.encrypt_string( "P" + this.name + this.vendorID + String.valueOf( System.currentTimeMillis() ) );

    }

    // costruttore per la registrazione di un nuovo prodotto
    public Product ( String vendorID , String name , String description , String pathOf_image , float sellingPrice , int currentStock , boolean autoRestock , int minStock , int restockAmount , boolean isClone , String sourceID , float sourcePrice ) {

        this.vendorID = vendorID;
        this.name = name;
        this.ID = generate_ID();

        if (pathOf_image == null )
            this.pathOf_image = "src/main/resources/images/iconOf_noImage.png";
        else
            this.pathOf_image = pathOf_image;
        
        this.description = description;
        this.sellingPrice = sellingPrice;
        
        this.stock = currentStock;

        if (autoRestock == true) {
            this.autoRestock = true;
            this.minStock = minStock;
            this.restockAmount = restockAmount;
        } else {
            this.autoRestock = false;
            this.minStock = 0;
            this.restockAmount = 0;
        }
        
        if (isClone == true) {
            this.isClone = true;
            this.sourceID = sourceID;
            this.sourcePrice = sourcePrice;
        } else {
            this.isClone = false;
            this.sourceID = null;
            this.sourcePrice = 0;
        }

    }

    // costruttore per la creazione di un oggetto Product a partire dai dati presenti nel pseudo-database
    public Product ( String ID , String vendorID , String name , String description , String pathOf_image , float sellingPrice , int currentStock , boolean autoRestock , int minStock , int restockAmount , boolean isClone , String sourceID , float sourcePrice ) {

        this.ID = ID;
        this.vendorID = vendorID;
        this.name = name;
        this.description = description;
        this.pathOf_image = pathOf_image;
        this.sellingPrice = sellingPrice;
        this.stock = currentStock;
        this.autoRestock = autoRestock;
        this.minStock = minStock;
        this.restockAmount = restockAmount;
        this.isClone = isClone;
        this.sourceID = sourceID;
        this.sourcePrice = sourcePrice;

    }



    public String get_ID () {
        return this.ID;
    }

    public String get_vendorID () {
        return this.vendorID;
    }

    public String get_name () {
        return this.name;
    }

    public String get_description () {
        return this.description;
    }

    public String get_pathOf_image () {
        return this.pathOf_image;
    }

    public float get_sellingPrice () {
        return this.sellingPrice;
    }

    public int get_stock () {
        return this.stock;
    }

    public boolean get_autoRestock () {
        return this.autoRestock;
    }

    public int get_minStock () {
        return this.minStock;
    }

    public int get_restockAmount () {
        return this.restockAmount;
    }

    public boolean get_isClone () {
        return this.isClone;
    }

    public String get_sourceID () {
        return this.sourceID;
    }

    public float get_sourcePrice () {
        return this.sourcePrice;
    }



    public void set_name ( String newName ) {
        this.name = newName;
    }

    public void set_description ( String newDescription ) {
        this.description = newDescription;
    }

    public void set_pathOf_image ( String newPathOf_image ) {
        this.pathOf_image = newPathOf_image;
    }

    public void set_sellingPrice ( float newSellingPrice ) {
        this.sellingPrice = newSellingPrice;
    }

    public void set_stock ( int newStock ) {
        this.stock = newStock;
    }

    public void set_autoRestock ( boolean newAutoRestock ) {
        this.autoRestock = newAutoRestock;
    }

    public void set_minStock ( int newMinStock ) {
        this.minStock = newMinStock;
    }

    public void set_restockAmount ( int newRestockAmount ) {
        this.restockAmount = newRestockAmount;
    }

    public void set_isClone ( boolean newIsClone ) {
        this.isClone = newIsClone;
    }

    public void set_sourceID ( String newSourceID ) {
        this.sourceID = newSourceID;
    }

    public void set_sourcePrice ( float newSourcePrice ) {
        this.sourcePrice = newSourcePrice;
    }



}