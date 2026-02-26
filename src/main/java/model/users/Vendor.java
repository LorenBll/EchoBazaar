package main.java.model.users;

import java.util.ArrayList;
import main.java.auth.CryptingEngine;






public class Vendor {
    
    private String ID;
    private String username;
    private String encryptedPassword;
    private float balance;

    private ArrayList<String> productsID;






    private String generate_ID () {
        //! metodo che genera un ID univoco per il venditore
        // non viene effettuato il controllo sull'unicità dell'ID generato perché è molto improbabile che si generino due ID uguali:
            // richiederebbe che due venditori si registrino esattamente nello stesso millisecondo e con lo stesso username 
            // (inoltre EchoBazaar funziona in locale e non ha un server connesso a internet, quindi è inverificabile)
        
        return CryptingEngine.encrypt_string( "V" + this.username + String.valueOf( System.currentTimeMillis() ) );

    }

    // costruttore per la registrazione di un nuovo venditore
    public Vendor ( String username , String encryptedPassword ) {

        this.username = username;
        this.ID = generate_ID();
        this.encryptedPassword = encryptedPassword;
        this.balance = 5;

        this.productsID = new ArrayList<String>();

    }

    // costruttore per la creazione di un oggetto Vendor a partire dai dati presenti nel pseudo-database
    public Vendor ( String username , String ID , String encryptedPassword , float balance , ArrayList<String> productsID ) {

        this.username = username;
        this.ID = ID;
        this.encryptedPassword = encryptedPassword;
        this.balance = balance;

        this.productsID = productsID;

    }






    // === METODI DI UTILITÀ AKA DA NON MODIFICARE ===
    public String get_ID () {
        return this.ID;
    }

    public String get_username () {
        return this.username;
    }

    public String get_encryptedPassword () {
        return this.encryptedPassword;
    }

    public float get_balance () {
        return this.balance;
    }

    public ArrayList<String> get_productsID () {
        return this.productsID;
    }

    public void deposit ( float amount ) {
        this.balance += amount;
    }

    public boolean withdraw ( float amount ) {
        if ( this.balance - amount >= 0 ) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public void set_username ( String newUsername ) {
        this.username = newUsername;
    }

    public void set_encryptedPassword ( String newPassword ) {
        this.encryptedPassword = newPassword;
    }

    public void add_productID ( String productID ) {
        this.productsID.add( productID );
    }

    public void remove_productID ( String productID ) {
        this.productsID.remove( productID );
    }

    public void pay ( float amount ) {
        this.balance -= amount;
    }

    public void get_paid ( float amount ) {
        this.balance += amount;
    }



}
