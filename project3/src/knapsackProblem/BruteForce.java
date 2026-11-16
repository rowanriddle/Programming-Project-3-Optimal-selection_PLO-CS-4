package knapsackProblem;

/*
 * Team Name: Team 2
 * Team Members: Matthew Meek, Lia Day, Rowan Bailey
 * Course: CS 2430- 501
 * Project: Programming Project 3 - Fall 2026
 *
 * File: BruteForce.java
 * Purpose: Find the three highest-rated valid combinations of
 *          science experiments using exhaustive search.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BruteForce {

    // Maximum payload weight allowed on the shuttle.
    private static final int CAPACITY = 700;

    // Stores information about each science experiment.
    static class Experiment {
        String name;
        int weight;
        int rating;

        public Experiment(String name, int weight, int rating) {
            this.name = name;
            this.weight = weight;
            this.rating = rating;
        }
    }

    // Stores the results of one possible experiment combination.
    static class Solution {
        List<Experiment> chosen;
        int totalWeight;
        int totalRating;

        public Solution(List<Experiment> chosen,
                        int totalWeight, int totalRating) {
            this.chosen = chosen;
            this.totalWeight = totalWeight;
            this.totalRating = totalRating;
        }
    }

    // List containing all 12 available experiments.
    private static final Experiment[] experiments = {
        new Experiment("Cloud Patterns", 36, 5),
        new Experiment("Solar Flares", 264, 9),
        new Experiment("Solar Power", 188, 6),
        new Experiment("Binary Stars", 203, 8),
        new Experiment("Relativity", 104, 8),
        new Experiment("Seed Viability", 7, 4),
        new Experiment("Sun Spots", 90, 2),
        new Experiment("Mice Tumors", 65, 8),
        new Experiment("Microgravity Plant Growth", 75, 5),
        new Experiment("Micrometeorites", 170, 9),
        new Experiment("Cosmic Rays", 80, 7),
        new Experiment("Yeast Fermentation", 27, 4)
    };

    /*
     * BRUTE FORCE ALGORITHM
     *
     * Generates every possible subset using binary bitmasks.
     * Each bit represents whether an experiment is selected.
     *
     * 0 = Experiment is not selected.
     * 1 = Experiment is selected.
     *
     * With 12 experiments, there are 2^12 = 4096 subsets.
     */
    public static List<Solution> bruteForce() {

        List<Solution> solutions = new ArrayList<>();

        int n = experiments.length;

        // Generate all possible subsets.
        int totalSubsets = 1 << n;

        for (int mask = 0; mask < totalSubsets; mask++) {

            int totalWeight = 0;
            int totalRating = 0;

            List<Experiment> chosen = new ArrayList<>();

            // Examine each bit to determine which experiments
            // belong to the current subset.
            for (int i = 0; i < n; i++) {

                // Check whether bit i is set.
                if ((mask & (1 << i)) != 0) {

                    Experiment current = experiments[i];

                    chosen.add(current);

                    totalWeight += current.weight;
                    totalRating += current.rating;
                }
            }

            // Only save combinations that do not exceed
            // the shuttle's 700 kg weight capacity.
            if (totalWeight <= CAPACITY) {

                Solution solution = new Solution(chosen, totalWeight, totalRating);

                solutions.add(solution);
            }
        }

        // Sort solutions by:
        // 1. Highest rating first.
        // 2. Lowest weight first when ratings are tied.
        solutions.sort(
            Comparator.comparingInt((Solution s) -> s.totalRating).reversed().thenComparingInt(s -> s.totalWeight));

        return solutions;
    }

    /*
     * DISPLAY RESULTS
     *
     * Prints the three highest-rated valid combinations.
     * The first result is the optimal solution.
     */
    public static void displayTopThree(List<Solution> solutions) {

        System.out.println("\n======================================");
        System.out.println(" BRUTE FORCE - TOP THREE SOLUTIONS");
        System.out.println("======================================");

        for (int i = 0; i < 3 && i < solutions.size(); i++) {

            Solution solution = solutions.get(i);

            System.out.println("\nSolution #" + (i + 1));

            if (i == 0) {
                System.out.println("*** OPTIMAL SOLUTION ***");
            }

            System.out.println("Chosen Experiments:");

            for (Experiment experiment : solution.chosen) {
                System.out.println("  " + experiment.name
                    + " | Weight: " + experiment.weight + " kg"
                    + " | Rating: " + experiment.rating);
            }

            System.out.println("------------------------------");
            System.out.println("Total Weight: "+ solution.totalWeight + " kg");

            System.out.println("Total Rating: "+ solution.totalRating);
        }
    }

    /*
     * MAIN METHOD
     *
     * Runs the brute force algorithm and displays
     * the three best valid experiment combinations.
     */
    public static void main(String[] args) {

        System.out.println("SPACE SHUTTLE PAYLOAD OPTIMIZATION");
        System.out.println("Maximum Capacity: " + CAPACITY + " kg");

        List<Solution> solutions = bruteForce();

        System.out.println("\nTotal Subsets Examined: "+ (1 << experiments.length));
        displayTopThree(solutions);
    }

}



