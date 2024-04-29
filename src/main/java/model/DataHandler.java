package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;
import main.java.model.users.Customer;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import main.java.auth.CryptingEngine;






public class DataHandler {

    private ArrayList<Customer> customers = new ArrayList<Customer>();
    //private ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    //private ArrayList<Product> products = new ArrayList<Product>();






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
            //load_vendors();
            //load_products();

        } 
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



    public boolean login ( String username , String inputtedPassword ) {
        //! metodo che effettua il login dell'utente

        //. controllo se l'utente è un customer e faccio il login
            // il controllo è fatto in modo dicotomico: se l'username è minore di quello del customer attuale, cerco nella prima metà della lista, altrimenti nella seconda
        int leftIndex = 0;
        int rightIndex = customers.size() - 1;
        String encryptedPassword = CryptingEngine.encrypt_string(inputtedPassword);
        while (leftIndex <= rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
            Customer currentCustomer = customers.get(middleIndex);

            // se ho trovato l'utente, controllo la password
            if ( currentCustomer.get_username().equals(username) && currentCustomer.get_encryptedPassword().equals(encryptedPassword) ) {
                return true;
            }
            
            if (currentCustomer.get_username().compareTo(username) < 0) {
                leftIndex = middleIndex + 1;
            }
            else {
                rightIndex = middleIndex - 1;
            }
        
        }

        // todo : . controllo se l'utente è un vendor e faccio il login

        return false;
    
    }

    public boolean reset_password ( String ID , String inputtedPassword ) {
        //! metodo che resetta la password di un utente

        //. controllo se l'utente è un customer e faccio il reset
            // la ricerca è fatta in modo lineare, in quanto la lista non è ordinata per ID

        for ( Customer c : customers ) {
            if ( c.get_ID().equals(ID) ) {
                c.set_encryptedPassword( CryptingEngine.encrypt_string(inputtedPassword) );
                update_customerFile();
                return true;
            }
        }

        // todo : . controllo se l'utente è un vendor e faccio il reset

        return false;

    }






    // === METODI PER GESTIRE I DATI DEI CUSTOMER ===
    public boolean add_customer ( Customer customer ) {
        //! metodo che aggiunge un customer alla lista e aggiorna il file

        //. controllo che non ci sia un customer con lo stesso username
        for ( Customer c : customers ) {
            if ( c.get_username().equals( customer.get_username() ) ) {
                return false;
            }
        }

        // todo : . controllo che non ci sia un vendor con lo stesso username

        //. inserisco il customer nella lista in ordine alfabetico per username
        int currentIndex = 0;
        for ( Customer c : customers ) {
            if ( c.get_username().compareTo( customer.get_username() ) > 0 ) {
                customers.add(currentIndex, customer);
                update_customerFile();
                return true;
            }
            currentIndex++;
        }
        
        // se non ho inserito il customer in nessun punto, lo inserisco alla fine
        customers.add(customer);
        update_customerFile();
        return true;
    
    }

    public void remove_customer ( String username ) {
        //! metodo che rimuove un customer dalla lista e aggiorna il file
        for ( Customer c : customers ) {
            if ( c.get_username().equals( username ) ) {
                customers.remove(c);
                update_customerFile();
                return;
            }
        }
    }

    public void update_customerFile () {
        //! metodo che aggiorna il file dei customer con i dati presenti nella lista

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
                Customer customer = new Customer(customerData[0], customerData[1], customerData[2], Float.parseFloat(customerData[3]));
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

}
