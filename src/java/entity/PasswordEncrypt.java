/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;


/**
 * @author Shane
 */
public class PasswordEncrypt implements Serializable{
    
    public PasswordEncrypt() {
        
    }
    
    public String encrypt(String password) {
        
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception ex) {
            
        }
        try {
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        } catch (Exception ex) {
            
        }
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        
        return bigInt.toString(16);
    }
}
