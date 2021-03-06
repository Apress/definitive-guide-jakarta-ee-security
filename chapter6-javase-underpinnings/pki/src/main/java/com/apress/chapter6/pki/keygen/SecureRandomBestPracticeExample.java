package com.apress.chapter6.pki.keygen;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This examples demonstrates a best-practice approach for SecureRandom. Below its main features:
 * 1. Uses a non-default PRNG (SHA1PRNG in this case) to increase efficiency. For example, SHA1PRNG can be 17 times faster than NativePRNG
 * 2. Applies periodic seeding for increased entropy
 *
 * Notes:
 * Periodic reseeding defends against data disclosure if a seed is leaked.
 * When using SHA1PRNG, always call SecureRandom.nextBytes(byte[]) immediately after creating a new instance of the PRNG.
 */
public class SecureRandomBestPracticeExample {

    public static void main(String[] args) throws NoSuchAlgorithmException {

            // initialize a secure random number generator
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

            // call nextBytes() method to generate random bytes
            byte[] bytes = new byte[512];
            secureRandom.nextBytes(bytes);
            System.out.println("SecureRandom # generated by calling nextBytes() is " + secureRandom.nextDouble());

            // seed periodically using setSeed() to reseed a random object
            int seedByteCount = 10;
            byte[] seed = secureRandom.generateSeed(seedByteCount);

            secureRandom.setSeed(seed);
            System.out.println("SecureRandom # generated using setSeed() is  " + secureRandom.nextDouble());
    }
}