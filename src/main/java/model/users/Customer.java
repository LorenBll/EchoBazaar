package main.java.model.users;

import main.java.auth.CryptingEngine;






public class Customer {
    
    private String ID;
    private String username;
    private String encryptedPassword;   
    private float balance;





    
    private String generate_ID () {
        //! metodo che genera un ID univoco per il cliente
        // non viene effettuato il controllo sull'unicità dell'ID generato perché è molto improbabile che si generino due ID uguali:
            // richiederebbe che due customer si registrino esattamente nello stesso millisecondo e con lo stesso username 
            // (inoltre EchoBazaar funziona in locale e non ha un server connesso a internet, quindi è inverificabile)
        
        return CryptingEngine.encrypt_string( "C" + this.username + String.valueOf( System.currentTimeMillis() ) );

    }
    
    public Customer ( String username , String encryptedPassword ) {

        this.username = username;
        this.ID = generate_ID();
        this.encryptedPassword = encryptedPassword;
        this.balance = 5;

    }

    public Customer ( String username , String ID , String encryptedPassword , float balance ) {

        this.username = username;
        this.ID = ID;
        this.encryptedPassword = encryptedPassword;
        this.balance = balance;

    }



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



    public void set_username ( String newUsername ) {
        this.username = newUsername;
    }

    public void set_encryptedPassword ( String newPassword ) {
        this.encryptedPassword = newPassword;
    }

}