
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

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

        MessageDigest mDig = MessageDigest.getInstance("SHA-1");
        
        System.out.println("Aufgabe 1bcd:");
        BigInteger p = new BigInteger("307");
        BigInteger g = new BigInteger("5");
        BigInteger pk1 = new BigInteger("43");
        BigInteger pk2 = new BigInteger("72");
        BigInteger A = computePK(p, g, pk1);
        BigInteger B = computePK(p, g, pk2);
        BigInteger K1 = computeK(B, pk1, p);
        BigInteger K2 = computeK(A, pk2, p);
        
        System.out.println("Alice:  A=" + A);
        System.out.println("Bob:    B=" + B);
        System.out.println("Secret: K=" + K1.toString() + " K'=" + K2.toString());
        System.out.println("SHA-1=" + new BigInteger(mDig.digest(K1.mod(new BigInteger("256")).toByteArray())).toString(16));
        
        
        System.out.println("\nAufgabe 1e:");
        g = new BigInteger("5");
        A = new BigInteger("67");
        B = new BigInteger("172");
        pk1 = breakPK(p, g, A);
        pk2 = breakPK(p, g, B);
        K1 = computeK(B, pk1, p);
        K2 = computeK(A, pk2, p);
        
        System.out.println("The private key of A=" + A  + " is " + pk1);
        System.out.println("The private key of B =" + B  + " is " + pk2);
        System.out.println("Secret: K=" + K1 + " K'=" + K2);
        System.out.println("SHA-1=" + new BigInteger(mDig.digest(K1.mod(new BigInteger("256")).toByteArray())).toString(16));

        System.out.println("\nAufgabe 1f:");
        p = new BigInteger("2148532933");
        g = new BigInteger("1001");
        A = new BigInteger("1992854757");
        pk1 = breakPK(p, g, A);
        
        System.out.println("The private key is pk = " + pk1);
        System.out.println("Test: g^a = " + g.modPow(pk1, p) + " = " + A + " = A");
    }

    private static BigInteger breakPK(BigInteger p, BigInteger g, BigInteger PUBK) {

        BigInteger count = BigInteger.ONE;
        BigInteger bipk1 = g;

        do {
            bipk1 = bipk1.multiply(g).mod(p);
            count = count.add(BigInteger.ONE);
        } while (!bipk1.equals(PUBK));

        return count;
        
    }

    private static BigInteger computeK(BigInteger A, BigInteger pk, BigInteger p) {
        return A.modPow(pk, p);
    }

    // Public Key berechnen
    private static BigInteger computePK(BigInteger p, BigInteger g, BigInteger pk) {
//        return g.modPow(pk, p);
        return fastExponentiation(g, pk).mod(p);
    }
    
    // Algorithmus von https://en.wikipedia.org/wiki/Exponentiation_by_squaring
    private static BigInteger fastExponentiation(BigInteger val, BigInteger exp){
        
        BigInteger y = BigInteger.ONE;
        BigInteger x = val;
        
        if ( exp.equals(BigInteger.ZERO) ){
            return BigInteger.ONE;
        } 
        if ( exp.compareTo(BigInteger.ZERO) == -1  ){
            x = BigInteger.ONE.divide(x);
            exp = exp.negate();
        }
        
        while ( exp.compareTo(BigInteger.ONE) == 1 ){
            if ( exp.and(BigInteger.ONE).equals(BigInteger.ZERO) ){
                x = x.multiply(x);
                exp = exp.divide(BigInteger.ONE.shiftLeft(1));
            } else {
                y = x.multiply(y);
                x = x.multiply(x);
                exp = (exp.subtract(BigInteger.ONE)).divide(BigInteger.ONE.shiftLeft(1));
            }
        }
        
        return x.multiply(y);
        
    }
}
