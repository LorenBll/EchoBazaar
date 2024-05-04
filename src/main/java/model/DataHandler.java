package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;
import main.java.model.users.Customer;
import main.java.model.users.Vendor;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import main.java.auth.CryptingEngine;
import main.java.Main;






public class DataHandler {

    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    // todo private ArrayList<Product> products = new ArrayList<Product>();






    public DataHandler () {

        //. crea i file per salvare i dati se non esistono
        try {
            
            File file = new File("src/main/resources/data/customers.txt");
            if (file.createNewFile()) {
                //System.out.println("File created: " + file.getName());
            }

            file = new File("src/main/resources/data/vendors.txt");
            if (file.createNewFile()) {
                //System.out.println("File created: " + file.getName());
            }

            file = new File("src/main/resources/data/products.txt");
            if (file.createNewFile()) {
                //System.out.println("File created: " + file.getName());
            }

        } 
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



        //. legge i dati presenti nei file e li carica nelle liste
        try {
        
            load_customers();
            load_vendors();
            //todo load_products();

        } 
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



    public boolean login ( String username , String inputtedPassword , Main main ) {
        //! metodo che effettua il login dell'utente
        
        String encryptedPassword = CryptingEngine.encrypt_string(inputtedPassword);

        //. controllo se l'utente è un customer e faccio il login
            // il controllo è fatto in modo dicotomico: se l'username è minore di quello del customer attuale, cerco nella prima metà della lista, altrimenti nella seconda
        int leftIndex = 0;
        int rightIndex = customers.size() - 1;
        while (leftIndex <= rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
            Customer currentCustomer = customers.get(middleIndex);

            // se ho trovato l'utente, controllo la password
            if ( currentCustomer.get_username().equals(username) && currentCustomer.get_encryptedPassword().equals(encryptedPassword) ) {
                main.loggedInCustomer = currentCustomer; // setto l'utente loggato
                return true;
            }
            
            if (currentCustomer.get_username().compareTo(username) < 0) {
                leftIndex = middleIndex + 1;
            }
            else {
                rightIndex = middleIndex - 1;
            }
        
        }

        //. controllo se l'utente è un vendor e faccio il login
            // il controllo è fatto in modo dicotomico: se l'username è minore di quello del vendor attuale, cerco nella prima metà della lista, altrimenti nella seconda
        leftIndex = 0;
        rightIndex = vendors.size() - 1;
        while (leftIndex <= rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
            Vendor currentVendor = vendors.get(middleIndex);

            // se ho trovato l'utente, controllo la password
            if ( currentVendor.get_username().equals(username) && currentVendor.get_encryptedPassword().equals(encryptedPassword) ) {
                main.loggedInVendor = currentVendor; // setto l'utente loggato
                return true;
            }
            
            if (currentVendor.get_username().compareTo(username) < 0) {
                leftIndex = middleIndex + 1;
            }
            else {
                rightIndex = middleIndex - 1;
            }
        
        }

        return false;
    
    }

    public boolean reset_password ( String ID , String inputtedPassword ) {
        //! metodo che resetta la password di un utente

        //. se l'utente è un customer e faccio il reset ( se non lo è, il metodo controlla se è un vendor )
            // la ricerca è fatta in modo lineare, in quanto la lista non è ordinata per ID
        for ( Customer c : customers ) {
            if ( c.get_ID().equals(ID) ) {
                c.set_encryptedPassword( CryptingEngine.encrypt_string(inputtedPassword) );
                update_customerFile();
                return true;
            }
        }

        //. controllo se l'utente è un vendor e faccio il reset ( se non lo è, il metodo ritorna false )
            // la ricerca è fatta in modo lineare, in quanto la lista non è ordinata per ID
        for ( Vendor v : vendors ) {
            if ( v.get_ID().equals(ID) ) {
                v.set_encryptedPassword( CryptingEngine.encrypt_string(inputtedPassword) );
                update_vendorFile();
                return true;
            }
        }

        return false;

    }






    // === METODI PER GESTIRE I DATI DEI CUSTOMER ===
    public boolean register_customer ( Customer newCustomer ) {
        //! metodo che aggiunge un customer alla lista e aggiorna il file

        //. controllo che non ci sia un customer con lo stesso username
        for ( Customer c : customers ) {
            if ( c.get_username().equals( newCustomer.get_username() ) ) {
                return false;
            }
        }

        //. controllo che non ci sia un vendor con lo stesso username
        for ( Vendor v : vendors ) {
            if ( v.get_username().equals( newCustomer.get_username() ) ) {
                return false;
            }
        }

        //. inserisco il customer nella lista in ordine alfabetico per username
        int currentIndex = 0;
        for ( Customer c : customers ) {
            if ( c.get_username().compareTo( newCustomer.get_username() ) > 0 ) {
                customers.add(currentIndex, newCustomer);
                update_customerFile();
                return true;
            }
            currentIndex++;
        }
        
        // se non ho inserito il customer in nessun punto, lo inserisco alla fine
        customers.add(newCustomer);
        update_customerFile();
        return true;
    
    }

    public void delete_customer ( String username ) {
        //! metodo che rimuove un customer dalla lista e aggiorna il file
        for ( Customer c : customers ) {
            if ( c.get_username().equals( username ) ) {
                customers.remove(c);
                update_customerFile();
                return;
            }
        }
    }

    public boolean update_customer ( String ID , String newUsername , String newPassword ) {
    
        //. controllo che non ci sia un customer con lo stesso username e ID diverso 
        for ( Customer c : customers ) {
            if ( c.get_username().equals( newUsername) && !c.get_ID().equals( ID ) ) {
                return false;
            }
        }

        // . controllo che non ci sia un vendor con lo stesso username
        for ( Vendor v : vendors ) {
            if ( v.get_username().equals( newUsername ) ) {
                return false;
            }
        }

        //. cerco il customer da aggiornare e lo aggiorno
        for ( Customer c : customers ) {
            if ( c.get_ID().equals( ID ) ) {
                
                if ( !newUsername.equals("") ) 
                    c.set_username(newUsername);
                if ( !newPassword.equals("") )
                    c.set_encryptedPassword( CryptingEngine.encrypt_string(newPassword) );

                update_customerFile();
                return true;
            }
        }

        return false;

    }

    public void update_customerFile () {
        //! metodo che aggiorna il file dei customer con i dati presenti nella lista

        //. ordino i customer in ordine alfabetico per username
        customers.sort((c1, c2) -> c1.get_username().compareTo(c2.get_username()));

        //. converto i customer in HashMap
        ArrayList<HashMap<String, String>> customersMap = new ArrayList<HashMap<String, String>>();
        for ( Customer c : customers ) {
            HashMap<String, String> customerMap = new HashMap<String, String>();
            customerMap.put("username", c.get_username());
            customerMap.put("ID", c.get_ID());
            customerMap.put("encryptedPassword", c.get_encryptedPassword());
            customerMap.put("balance", String.valueOf( c.get_balance() ));
            customersMap.add(customerMap);
        }

        //. scrivo i customer nel file, uno per riga
        try {
            FileWriter writer = new FileWriter("src/main/resources/data/customers.txt");
            for ( HashMap<String, String> customerMap : customersMap ) {
                writer.write( customerMap.get("username") + "," + customerMap.get("ID") + "," + customerMap.get("encryptedPassword") + "," + customerMap.get("balance") + "\n" );
            }
            writer.close();
        } 
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void load_customers () {
    
        try {
            File file = new File("src/main/resources/data/customers.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] customerData = scanner.nextLine().split(",");
                Customer customer = new Customer( customerData[0], customerData[1], customerData[2], Float.parseFloat(customerData[3]) );
                customers.add(customer);
            }
            scanner.close();
        } 
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    
    }






    // === METODI PER GESTIRE I DATI DEI VENDOR ===
    public boolean register_vendor ( Vendor newVendor ) {
        //! metodo che aggiunge un vendor alla lista e aggiorna il file

        //. controllo che non ci sia un vendor con lo stesso username
        for ( Vendor v : vendors ) {
            if ( v.get_username().equals( newVendor.get_username() ) ) {
                return false;
            }
        }

        //. controllo che non ci sia un customer con lo stesso username
        for ( Customer c : customers ) {
            if ( c.get_username().equals( newVendor.get_username() ) ) {
                return false;
            }
        }

        //. inserisco il vendor nella lista in ordine alfabetico per username
        int currentIndex = 0;
        for ( Vendor v : vendors ) {
            if ( v.get_username().compareTo( newVendor.get_username() ) > 0 ) {
                vendors.add(currentIndex, newVendor);
                update_vendorFile();
                return true;
            }
            currentIndex++;
        }
        
        // se non ho inserito il vendor in nessun punto, lo inserisco alla fine
        vendors.add(newVendor);
        update_vendorFile();
        return true;
    
    }

    public void delete_vendor ( String username ) {
        //! metodo che rimuove un vendor dalla lista e aggiorna il file
        for ( Vendor v : vendors ) {
            if ( v.get_username().equals( username ) ) {
                vendors.remove(v);
                update_vendorFile();
                return;
            }
        }
    }

    public boolean update_vendor ( String ID , String newUsername , String newPassword ) {
    
        //. controllo che non ci sia un vendor con lo stesso username e ID diverso 
        for ( Vendor v : vendors ) {
            if ( v.get_username().equals( newUsername) && !v.get_ID().equals( ID ) ) {
                return false;
            }
        }

        // . controllo che non ci sia un customer con lo stesso username
        for ( Customer c : customers ) {
            if ( c.get_username().equals( newUsername ) ) {
                return false;
            }
        }

        //. cerco il vendor da aggiornare e lo aggiorno
        for ( Vendor v : vendors ) {
            if ( v.get_ID().equals( ID ) ) {
                
                if ( !newUsername.equals("") ) 
                    v.set_username(newUsername);
                if ( !newPassword.equals("") )
                    v.set_encryptedPassword( CryptingEngine.encrypt_string(newPassword) );

                update_vendorFile();
                return true;
            }
        }

        return false;

    }

    public void update_vendorFile () {
        //! metodo che aggiorna il file dei vendor con i dati presenti nella lista

        //. ordino i vendor in ordine alfabetico per username
        vendors.sort((v1, v2) -> v1.get_username().compareTo(v2.get_username()));

        //. converto i vendor in HashMap
        ArrayList<HashMap<String, String>> vendorsMap = new ArrayList<HashMap<String, String>>();

        for ( Vendor v : vendors ) {
            HashMap<String, String> vendorMap = new HashMap<String, String>();
            vendorMap.put("username", v.get_username());
            vendorMap.put("ID", v.get_ID());
            vendorMap.put("encryptedPassword", v.get_encryptedPassword());
            vendorMap.put("balance", String.valueOf( v.get_balance() ));
            
            // converto l'ArrayList di productsID in una stringa separata da pipe
            String productsID = "[" + String.join("|", v.get_productsID()) + "]";
            vendorMap.put("productsID", productsID);
            
            vendorsMap.add(vendorMap);
        }

        //. scrivo i vendor nel file, uno per riga
        try {
            FileWriter writer = new FileWriter("src/main/resources/data/vendors.txt");
            for ( HashMap<String, String> vendorMap : vendorsMap ) {
                writer.write( vendorMap.get("username") + "," + vendorMap.get("ID") + "," + vendorMap.get("encryptedPassword") + "," + vendorMap.get("balance") + "," + vendorMap.get("productsID") + "\n" );
            }
            writer.close();
        } 
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void load_vendors () {
    
        try {
            File file = new File("src/main/resources/data/vendors.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] vendorData = scanner.nextLine().split(",");

                // converto la stringa di productsID in un ArrayList
                String unconverted_productsID = vendorData[4].substring(1, vendorData[4].length() - 1);
                System.out.println(unconverted_productsID);
                String[] productsID = unconverted_productsID.split("|");
                ArrayList<String> converted_productsID = new ArrayList<String>();
                for ( String productID : productsID ) {
                    converted_productsID.add(productID);
                }

                Vendor vendor = new Vendor( vendorData[0], vendorData[1], vendorData[2], Float.parseFloat(vendorData[3]), converted_productsID );
                vendors.add(vendor);
            }
            scanner.close();
        } 
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }






    //tocheck === METODI PER GESTIRE I DATI DEI PRODUCT ===

}
