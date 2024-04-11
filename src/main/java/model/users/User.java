package main.java.model.users;

import main.java.auth.CryptingEngine;


public abstract class User {
    
    protected String ID;
    protected String username;
    protected String encryptedPassword;
    protected float balance;

    protected boolean isCustomer;
    protected String pathTo_profilePicture;

    public User ( String ID , String username , String password , float balance , boolean isCustomer ) {
        
        this.ID = ID;
        this.username = username;
        this.encryptedPassword = CryptingEngine.encrypt_string(password);
        this.balance = 5; // saldo iniziale di 5 euro
        
        this.isCustomer = isCustomer;
        this.pathTo_profilePicture = null;

        boolean isRegistrating = ID == null;
        if (isRegistrating) {
            generate_ID();
        }

    }

    private void generate_ID () {
        //! metodo che genera un ID univoco per l'utente
        
        long currentTime = System.currentTimeMillis(); // tempo attuale in millisecondi (da epoch, 1 gennaio 1970)
        if (this.isCustomer) {
            this.ID = "C" + currentTime;
        } else {
            this.ID = "V" + currentTime;
        } 

    }
    
    public String get_ID () {
        return this.ID;
    }
    


    public String get_username () {
        return this.username;
    }
    
    public void change_username ( String newUsername ) {
        this.username = newUsername;
        update_file();
    }
    


    public boolean login ( String password ) {
        return CryptingEngine.compare( password , this.encryptedPassword );
    }
    
    public void change_password ( String newPassword ) {
        this.encryptedPassword = CryptingEngine.encrypt_string(newPassword);
        update_file();
    }
    
    
    
    public float get_balance () {
        return this.balance;
    }

    public void increment_balance ( float incrementAmount ) {
        this.balance += incrementAmount;
        update_file();
    }

    public void decrement_balance ( float decrementAmount ) {
        this.balance -= decrementAmount;
        update_file();
    }



    public String get_profilePicture () {
        return this.pathTo_profilePicture;
    }

    public void change_profilePicture ( String pathTo_newProfilePicture ) {
        this.pathTo_profilePicture = pathTo_newProfilePicture;
        update_file();
    }



    public boolean isCustomer () {
        return true;
    }
    
    public boolean isVendor () {
        return false;
    }



    public abstract void register_account ();
    public abstract void update_file ();
    public abstract void delete_account ();

}