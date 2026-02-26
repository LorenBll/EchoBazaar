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
    private ArrayList<Product> products = new ArrayList<Product>();






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
        catch (Exception e) { e.printStackTrace(); }

        //. legge i dati presenti nei file e li carica nelle liste
        try {
        
            load_customers();
            load_vendors();
            load_products();

        } 
        catch (Exception e) { e.printStackTrace(); }

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
        //! metodo che aggiorna i dati relativi ad un customer
    
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
        catch (Exception e) { e.printStackTrace(); }

    }

    public void load_customers () {
        //! metodo che carica i dati dei customer dal file
    
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
        catch (Exception e) { e.printStackTrace(); }
    
    }

    public Customer retrieve_customerByID ( String customerID ) {
        //! metodo che restituisce un customer in base all'ID
        for ( Customer c : customers ) {
            if ( c.get_ID().equals( customerID ) ) {
                return c;
            }
        }
        return null;
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

        // identifico il vendor da eliminare
        Vendor vendorToDelete = null;
        for ( Vendor v : vendors ) {
            if ( v.get_username().equals( username ) ) {
                vendorToDelete = v;
                break;
            }
        }

        // rimuovo il vendor dalla lista
        vendors.remove(vendorToDelete);
        update_vendorFile();

        String vendorID = vendorToDelete.get_ID();
        
        // rimuovo i product del vendor : creo una copia dell'ArrayList per evitare ConcurrentModificationException
        ArrayList<Product> productsCopy = new ArrayList<Product>(products);
        for ( Product p : productsCopy ) {
            if ( p.get_vendorID().equals( vendorID ) ) {
                delete_product(p.get_ID());
            }
        }

    }

    public boolean update_vendor ( String ID , String newUsername , String newPassword ) {
        //! metodo che aggiorna i dati relativi ad un vendor
    
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
            
            //. converto l'ArrayList di productsID in una stringa separata da pipe
            String productsID = "";
            for ( String productID : v.get_productsID() ) {
                productsID += productID + "|";
            }
            
            productsID = "[" + productsID;
            if ( productsID.length() > 1 )
                productsID = productsID.substring(0, productsID.length() - 1); // rimuovo l'ultimo pipe, che si trova alla fine della stringa
            productsID += "]";
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
        catch (Exception e) { e.printStackTrace(); }

    }

    public void load_vendors () {
        //! metodo che carica i dati dei vendor dal file
    
        try {
            File file = new File("src/main/resources/data/vendors.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] vendorData = scanner.nextLine().split(",");

                //. converto la stringa di productsID in un ArrayList
                String unconverted_productsID = vendorData[4].substring(1, vendorData[4].length() - 1);  // rimuovo le parentesi quadre

                String[] productsID = {};
                if ( unconverted_productsID.length() == 0 ) {
                    unconverted_productsID = "";
                } else {
                    productsID = unconverted_productsID.split("\\|"); // splitto la stringa in base al pipe
                }

                ArrayList<String> converted_productsID = new ArrayList<String>();
                for ( String productID : productsID ) {
                    converted_productsID.add(productID);
                }
  
                Vendor vendor = new Vendor( vendorData[0], vendorData[1], vendorData[2], Float.parseFloat(vendorData[3]), converted_productsID );
                vendors.add(vendor);
            }
            scanner.close();
        } 
        catch (Exception e) { e.printStackTrace(); }
    }

    public Vendor retrieve_vendorByID ( String vendorID ) {
        //! metodo che restituisce un vendor in base all'ID
        for ( Vendor v : vendors ) {
            if ( v.get_ID().equals( vendorID ) ) {
                return v;
            }
        }
        return null;
    }






    // === METODI PER GESTIRE I DATI DEI PRODUCT ===
    public void register_product ( Product newProduct , Main main ) {
        //! metodo che aggiunge un product alla lista e aggiorna il file

        //. non è necessario controllare se esiste giù un product con lo stesso nome o ID perché sarebbe limitante per i venditori

        // inserisco il product nella lista in ordine alfabetico per name
        int currentIndex = 0;
        for ( Product p : products ) {
            if ( p.get_name().compareTo( newProduct.get_name() ) > 0 ) {
                products.add(currentIndex, newProduct);
                main.loggedInVendor.add_productID( newProduct.get_ID() );
                update_vendorFile();
                update_productFile();
                return;
            }
            currentIndex++;
        }
        
        // se non ho inserito il product in nessun punto, lo inserisco alla fine
        products.add(newProduct);
        
        main.loggedInVendor.add_productID( newProduct.get_ID() );
        update_vendorFile();
        update_productFile();
        
    }

    public void delete_product ( String ID ) { // non uso il nome perché non è per forza univoco
        //! metodo che rimuove un product dalla lista e aggiorna il file
        
        // rimuovo il product dai vendor
        for ( Vendor v : vendors ) {
            if ( v.get_productsID().contains( ID ) ) {
                v.remove_productID(ID);
            }
        }
        update_vendorFile();
        
        // aggiorno i product clone
        for ( Product p : products ) {
            try {
                if ( p.get_sourceID().equals( ID ) ) {
                    p.set_autoRestock(false);
                    p.set_minStock(0);
                    p.set_restockAmount(0);
                    p.set_isClone(false);
                    p.set_sourceID(null);
                }
            } catch (Exception e) {
                continue; // se si arriva qui, significa che il sourceID è null, quindi non c'è bisogno di fare nulla
            }
        }
        
        // rimuovo il product dalla lista
        for ( Product p : products ) {
            if ( p.get_ID().equals( ID ) ) {

                // elimino il file dell'immagine
                if ( !p.get_pathOf_image().equals("/main/resources/images/iconOf_noImage.png") ) {
                    File file = new File("src/" + p.get_pathOf_image());
                    file.delete();
                }

                products.remove(p);
                break;   
            }
        }
        update_productFile();

    }

    public void update_product ( String ID , String newName , String newDescription , String newPathOf_image , float newSellingPrice , int newCurrentStock , boolean autoRestock , int newMinStock , int newRestockAmount , boolean isClone , String newSourceID ) {
        //! metodo che aggiorna i dati relativi ad un product
    
        //. cerco il product da aggiornare e lo aggiorno
        for ( Product p : products ) {
            if ( p.get_ID().equals( ID ) ) {
                
                p.set_name(newName);
                p.set_description(newDescription);
                p.set_pathOf_image(newPathOf_image);
                p.set_sellingPrice(newSellingPrice);
                p.set_currentStock(newCurrentStock);
                
                if ( autoRestock == true ) {
                    p.set_autoRestock(true);
                    p.set_minStock(newMinStock);
                    p.set_restockAmount(newRestockAmount);
                    p.set_isClone(true);
                    p.set_sourceID(newSourceID);
                } else {
                    p.set_autoRestock(false);
                    p.set_minStock(0);
                    p.set_restockAmount(0);
                    p.set_isClone(false);
                    p.set_sourceID(null);
                }

                update_productFile();
                break;
            }
        }

    }

    public void update_productFile () {
        //! metodo che aggiorna il file dei product con i dati presenti nella lista

        //. ordino i product in ordine alfabetico per name
        products.sort((p1, p2) -> p1.get_name().compareTo(p2.get_name()));

        //. converto i product in HashMap
        ArrayList<HashMap<String, String>> productsMap = new ArrayList<HashMap<String, String>>();
        for ( Product p : products ) {
            HashMap<String, String> productMap = new HashMap<String, String>();
            productMap.put("ID", p.get_ID());
            productMap.put("vendorID", p.get_vendorID());
            productMap.put("name", p.get_name());

            productMap.put("description", p.get_description().replaceAll("\n", "\\|")); // rimuovo tutti gli acapo dalla stringa description

            productMap.put("pathOf_image", p.get_pathOf_image());
            productMap.put("sellingPrice", String.valueOf( p.get_sellingPrice() ));
            productMap.put("currentStock", String.valueOf( p.get_currentStock() ));
            productMap.put("autoRestock", String.valueOf( p.get_autoRestock() ));
            productMap.put("minStock", String.valueOf( p.get_minStock() ));
            productMap.put("restockAmount", String.valueOf( p.get_restockAmount() ));
            productMap.put("isClone", String.valueOf( p.get_isClone() ));
            productMap.put("sourceID", p.get_sourceID());
            productsMap.add(productMap);
        }

        //. scrivo i product nel file, uno per riga
        try {
            FileWriter writer = new FileWriter("src/main/resources/data/products.txt");
            for ( HashMap<String, String> productMap : productsMap ) {
                writer.write( productMap.get("ID") + "," + productMap.get("vendorID") + "," + productMap.get("name") + ",\"" + productMap.get("description") + "\"," + productMap.get("pathOf_image") + "," + productMap.get("sellingPrice") + "," + productMap.get("currentStock") + "," + productMap.get("autoRestock") + "," + productMap.get("minStock") + "," + productMap.get("restockAmount") + "," + productMap.get("isClone") + "," + productMap.get("sourceID") + "\n" );
            }
            writer.close();
        } 
        catch (Exception e) { e.printStackTrace(); }

    }

    public void load_products () {
        //! metodo che carica i dati dei product dal file

        try {
            File file = new File("src/main/resources/data/products.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] productData = scanner.nextLine().split(",");
                
                String productID = productData[0];
                String vendorID = productData[1];
                String name = productData[2];

                // rimuovo i doppi apici dalla stringa description e sostituisco i pipe con acapo
                String description = productData[3];
                description = description.substring(1, description.length() - 1);
                description = description.replaceAll("\\|", "\n");

                String pathOf_image = productData[4];
                float sellingPrice = Float.parseFloat(productData[5]);
                int currentStock = Integer.parseInt(productData[6]);
                boolean autoRestock = Boolean.parseBoolean(productData[7]);
                int minStock = Integer.parseInt(productData[8]);
                int restockAmount = Integer.parseInt(productData[9]);
                boolean isClone = Boolean.parseBoolean(productData[10]);
                String sourceID = productData[11];

                Product product = new Product( productID, vendorID, name, description, pathOf_image, sellingPrice, currentStock, autoRestock, minStock, restockAmount, isClone, sourceID );
                products.add(product);
            }
            scanner.close();
        } 
        catch (Exception e) { e.printStackTrace(); }

    }

    public boolean isProductIDValid ( String ID ) {
        //! metodo che controlla se un ID è valido
        for ( Product p : products ) {
            if ( p.get_ID().equals( ID ) ) {
                return true;
            }
        }
        return false;
    }

    public Product retrieve_productByID ( String productID ) {
        //! metodo che restituisce un product in base all'ID
        for ( Product p : products ) {
            if ( p.get_ID().equals( productID ) ) {
                return p;
            }
        }
        return null;
    } 

    public ArrayList<Product> retrieve_productsByOwner ( String productOwner_vendorID ) {
        //! metodo che restituisce tutti i product di un determinato venditore
        ArrayList<Product> productsByOwner = new ArrayList<Product>();
        for ( Product p : products ) {
            if ( p.get_vendorID().equals( productOwner_vendorID ) ) {
                productsByOwner.add(p);
            }
        }
        return productsByOwner;
    }

    public ArrayList<Product> retrieve_productsByNotOwner ( String productOwner_vendorID ) {
        //! metodo che restituisce tutti i product di tutti i venditori tranne quello specificato
        ArrayList<Product> productsByNotOwner = new ArrayList<Product>();
        for ( Product p : products ) {
            if ( !p.get_vendorID().equals( productOwner_vendorID ) ) {
                productsByNotOwner.add(p);
            }
        }
        return productsByNotOwner;
    }






    public boolean vendorBuy_product ( ProductOrder productOrder , String vendorID , Product productToRestock ) {  
        //! metodo che permette ad un venditore di acquistare un product per il restock : productToRestock ha come sourceID productOrder.get_product().get_ID()

        // productToRestock è il product che deve essere restockato, mentre productOrder.get_product() è il product che deve essere acquistato, ovvero quello indicato dal sourceID di productToRestock

        Vendor buyer = retrieve_vendorByID(vendorID);
        Vendor seller = retrieve_vendorByID(productOrder.get_product().get_vendorID());
        Product buyed_product = productOrder.get_product();
        float totalCost = buyed_product.get_sellingPrice() * productOrder.get_quantity();

        //. controllo che la quantità richiesta sia disponibile
        if ( buyed_product.get_currentStock() < productOrder.get_quantity() ) {
            return false;
        }

        //. controllo che il venditore abbia abbastanza soldi
        if ( buyer.get_balance() < totalCost ) {
            return false;
        }

        //. effettuo l'acquisto
        buyer.pay(totalCost);
        seller.get_paid(totalCost);
        buyed_product.set_currentStock( buyed_product.get_currentStock() - productOrder.get_quantity() );
        productToRestock.set_currentStock( productToRestock.get_currentStock() + productOrder.get_quantity() );
        update_vendorFile();
        update_productFile();

        //. effettuo le operazioni di autorestock su buyed_product ( non su productToRestock perché non è stato acquistato )
        if ( buyed_product.get_autoRestock() ) {
            if ( buyed_product.get_currentStock() < buyed_product.get_minStock() ) {
                ProductOrder newOrder = new ProductOrder ( retrieve_productByID(buyed_product.get_sourceID()) , buyed_product.get_restockAmount() );
                vendorBuy_product(newOrder, buyed_product.get_vendorID() , buyed_product);
            }
        }

        return true;
    }

    public boolean customerBuy_cart ( ArrayList<ProductOrder> rawCart , Customer buyer ) {
        //! metodo che permette ad un customer di acquistare il contenuto del carrello
        
        ArrayList<ProductOrder> cart = new ArrayList<>(rawCart); // creo una copia dell'ArrayList per evitare ConcurrentModificationException
        ArrayList<Product> productsToRestock = new ArrayList<Product>();

        // viene data la priorità al customer : se possibile si effettua tutti gli acquisti
        for ( ProductOrder currentProductOrder : cart ) {
            
            Vendor seller = retrieve_vendorByID(currentProductOrder.get_product().get_vendorID());

            // non controllo se la quantità richiesta è disponibile, perché è già stato fatto in precedenza

            float totalProductOrderPrice = currentProductOrder.get_product().get_sellingPrice() * currentProductOrder.get_quantity();
            // non controllo se il customer ha abbastanza soldi, perché è già stato fatto in precedenza

            //. effettuo l'acquisto
            buyer.pay(totalProductOrderPrice);
            seller.get_paid(totalProductOrderPrice);
            currentProductOrder.get_product().set_currentStock( currentProductOrder.get_product().get_currentStock() - currentProductOrder.get_quantity() );
            
            update_customerFile();
            update_vendorFile();
            update_productFile();

            if (currentProductOrder.get_product().get_autoRestock()) {
                if (currentProductOrder.get_product().get_currentStock() < currentProductOrder.get_product().get_minStock()) {
                    productsToRestock.add(currentProductOrder.get_product());
                }
            }

        }

        //. effettuo le operazioni di autorestock
        for ( Product currentProductToRestock : productsToRestock ) {
            ProductOrder newOrder = new ProductOrder ( retrieve_productByID(currentProductToRestock.get_sourceID()) , currentProductToRestock.get_restockAmount() );
            vendorBuy_product(newOrder, currentProductToRestock.get_vendorID() , currentProductToRestock);
        }

        return true;
    }

    public ArrayList<Product> get_products () {
        return products;
    }

}