
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

        MessageDigest mDig = MessageDigest.getInstance("SHA1");
        
        System.out.println("Aufgabe 1bcd:");
        BigInteger p = new BigInteger("293");
        BigInteger g = new BigInteger("5");
        BigInteger pk1 = new BigInteger("43");
        BigInteger pk2 = new BigInteger("72");
        BigInteger A = computePK(p, g, pk1);
        BigInteger B = computePK(p, g, pk2);
        BigInteger K1 = computeK(B, pk1, p);
        BigInteger K2 = computeK(A, pk2, p);
        ArrayList<Byte> k1 = new ArrayList<>();
        ArrayList<Byte> k2 = new ArrayList<>();
        
        for (Character c : K1.toString().toCharArray()){
            k1.add(Byte.valueOf(String.valueOf(c)));
        }
        for (Character c : K2.toString().toCharArray()){
            k2.add(Byte.valueOf(String.valueOf(c)));
        }
        byte[] test = new byte[k1.size()];
        byte[] test2 = new byte[k2.size()];
        
        for ( int i = 0 ; i < k1.size() ; i++ ){
            test[i] = k1.get(i);
        }
        for ( int i = 0 ; i < k2.size() ; i++ ){
            test2[i] = k2.get(i);
        }
        String s1 = K1.toString();
        String s2 = K2.toString();
        System.out.println("Alice:  A=" + A);
        System.out.println("Bob:    B=" + B);
        System.out.println("Secret: K=" + s1 + " K'=" + s2);
        System.out.println("SHA1: " + Arrays.toString(mDig.digest(K1.toByteArray())));
        System.out.println("SHA1: " + Arrays.toString(mDig.digest(K2.toByteArray())));
        
        System.out.println("\nAufgabe 1e:");
        p = new BigInteger("293");
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

        System.out.println("\nAufgabe 1f:");
        p = new BigInteger("2148532933");
        g = new BigInteger("1001");
        A = new BigInteger("1992854757");
        pk1 = breakPK(p, g, A);
        
        System.out.println("The private key of A=" + A  + " is " + pk1);
        System.out.println("Test g^a = A\t" + g.modPow(pk1, p) + " = " + A);
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
        return g.modPow(pk, p);
    }
}
