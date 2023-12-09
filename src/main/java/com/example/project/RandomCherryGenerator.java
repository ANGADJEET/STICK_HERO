package com.example.project;

import java.util.Random;

public class RandomCherryGenerator {
    private static RandomCherryGenerator gen = null;
    public static RandomCherryGenerator getInstance() {
        if (gen == null) {
            gen = new RandomCherryGenerator();
        }
        return gen;
    }
    public double generateCherry() {
            Random random = new Random();
            double randomValue = 300 + (50 * random.nextDouble());
            return randomValue;
        }
}
