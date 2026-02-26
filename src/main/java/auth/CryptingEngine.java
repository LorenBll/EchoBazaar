package main.java.auth;

import java.security.MessageDigest;






// PER CHI LEGGE : NOTARE CHE I METODI SONO STATICI IN MODO TALE DA NON DOVER ISTANZIARE UN OGGETTO CryptingEngine PER USARLI (metodi di classe)
public class CryptingEngine {
    
    public static String encrypt_string ( String string ) {
        //! metodo che cripta la string fornita in input
        
        try {
            
            //. digestione (aka hashing) della password in input
            MessageDigest messageDigester = MessageDigest.getInstance("SHA-1"); // crea un oggetto MessageDigest che implementa l'algoritmo di hashing SHA-1
            messageDigester.update(string.getBytes()); // aggiorna il messaggio digester con la password in input
            byte[] binaryArrayOf_cryptedPassword = messageDigester.digest(); // array di byte che rappresenta la password criptata
            
            //. conversione dell'array di byte contenente la stringa criptata in una stringa vera e propria
            StringBuffer stringBufferOf_outputString = new StringBuffer(); // stringa che rappresenta la password criptata
            for ( byte currentByte : binaryArrayOf_cryptedPassword ) {
                
                stringBufferOf_outputString.append(String.format("%02x", currentByte & 0xff));
            
            }
            
            return stringBufferOf_outputString.toString();

        } 

        catch ( Exception e ) {
            e.printStackTrace();
            return null; // ritorna null in caso di errore
        }
    
    }
    
    public static boolean compare ( String rawString , String hashedString ) {
        //! metodo che confronta la password in input con la password criptata in input
        
        boolean areStringsEqual = encrypt_string(rawString).equals(hashedString);
        return areStringsEqual;
    
    }

}