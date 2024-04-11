package main.java.model.users;

public class Customer extends User {
    
    public Customer ( String ID , String username , String password , float balance ) {
        super( ID , username , password , balance , true );
    }

    public void change_profilePicture ( String pathTo_newProfilePicture ) {
        //! metodo che aggiorna l'immagine del profilo dell'utente
        this.pathTo_profilePicture = pathTo_newProfilePicture;
        update_file();
    }

    public void register_account () {
        //! metodo che registra l'account dell'utente
        // todo
    }

    public void update_file () {
        //! metodo che aggiorna il file dell'utente
        // todo
    }

    public void delete_account () {
        //! metodo che elimina l'account dell'utente
        // todo
    }

}