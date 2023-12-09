package com.example.project;

public class RandomCherryGenerator {
    private static RandomCherryGenerator gen = null;
    public static RandomCherryGenerator getInstance() {
        if (gen == null) {
            gen = new RandomCherryGenerator();
        }
        return gen;
    }
        public double generateCherry() {
            // Generate a cherry at a random position
            // and return it
        }
}
