
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fisch
 */
public class Aufgaben {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        
        MessageDigest mDig = MessageDigest.getInstance("SHA1");
        
        BigInteger p = new BigInteger("293");
        BigInteger g = new BigInteger("5");
        BigInteger pk1 = new BigInteger("43");
        BigInteger pk2 = new BigInteger("72");
        BigInteger A = computePK(p,g,pk1);
        BigInteger B = computePK(p,g,pk2);
        BigInteger K1 = computeK(B,pk1,p);
        BigInteger K2 = computeK(A, pk2,p);
                
        System.out.println("Alice:  A=" + A);
        System.out.println("Bob:    B=" + B);
        System.out.println("Secret: K=" + K1 + " K'=" + K2);
        System.out.println("SHA1: " + String.valueOf(mDig.digest(K1.toByteArray())));
        System.out.println("SHA1: " + String.valueOf(mDig.digest(K2.toByteArray())));
    
        p = new BigInteger("293");
        g = new BigInteger("5");
        A = new BigInteger("67");
        B = new BigInteger("172");
        
        breakPK(p, g, A, B);
    }
    
    
    
    private static void breakPK( BigInteger p, BigInteger g, BigInteger pk1, BigInteger pk2 ){
        
       int c = (int)(Math.log(67)/Math.log(5));
       int d = (int)(Math.log(172)/Math.log(5));
       
        System.out.println(c + "\t" + d);
    }
    
    private static BigInteger computeK(BigInteger A, BigInteger pk, BigInteger p){
        return A.modPow(pk, p);
    }
    
    // Pupk2lic Key berechnen
    private static BigInteger computePK(BigInteger p, BigInteger g, BigInteger a) {
        return g.modPow(a, p);
    }
}
