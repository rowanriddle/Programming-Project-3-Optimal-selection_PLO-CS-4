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
 *
 * Revised requirements:
 * 1. Maximum payload capacity of 700 kg.
 * 2. At most two of experiments 2, 3, and 7.
 * 3. A 7 kg thermal credit when experiments 6 and 12
 *    are both selected.
 * 4. Experiment 11a requires experiment 11.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BruteForce {

    /*
     * Evaluation stores the calculated information for
     * a candidate manifest.
     */
    static class Evaluation {
        boolean valid;
        int adjustedWeight;
        int totalRating;
        String reason;

        public Evaluation(boolean valid, int adjustedWeight, int totalRating, String reason) {
            this.valid = valid;
            this.adjustedWeight = adjustedWeight;
            this.totalRating = totalRating;
            this.reason = reason;
        }
    }

    /*
     * Solution stores a valid manifest and its results.
     */
    static class Solution {
        public List<Experiment> chosen;
        public int totalWeight;
        public int totalRating;

        public Solution(List<Experiment> chosen, int totalWeight, int totalRating) {
            this.chosen = chosen;
            this.totalWeight = totalWeight;
            this.totalRating = totalRating;
        }
    }


    /*
     * Checks whether a manifest contains an experiment
     * with the specified ID.
     */
    private static boolean containsID(List<Experiment> manifest, String id) {
        for (Experiment experiment : manifest) {
            if (experiment.getid().equals(id)) {
                return true;
            }
        }

        return false;
    }

    /*
     * SHARED MANIFEST EVALUATOR
     *
     * Both brute force and greedy algorithms use this
     * method to enforce the revised manifest rules.
     *
     * The method:
     * 1. Checks the solar instrument restriction.
     * 2. Checks the 11a prerequisite.
     * 3. Calculates baseline mass.
     * 4. Applies the thermal mass credit.
     * 5. Calculates total science rating.
     * 6. Checks the adjusted mass against capacity.
     */
    public static Evaluation evaluateManifest(List<Experiment> manifest, int capacity) {

        int totalWeight = 0;
        int totalRating = 0;

        // Count how many restricted solar experiments
        // are included in the candidate manifest.
        int solarCount = 0;

        if (containsID(manifest, "2")) {
            solarCount++;
        }

        if (containsID(manifest, "3")) {
            solarCount++;
        }

        if (containsID(manifest, "7")) {
            solarCount++;
        }

        // Rule 1: No more than two solar experiments.
        if (solarCount > 2) {
            return new Evaluation(false, 0, 0, "Rejected: More than two solar experiments.");
        }

        // Rule 2: Experiment 11a requires experiment 11.
        if (containsID(manifest, "11a") && !containsID(manifest, "11")) {
            return new Evaluation(false, 0, 0, "Rejected: Experiment 11a requires Experiment 11.");
        }

        // Calculate the baseline mass and total rating.
        for (Experiment experiment : manifest) {
            totalWeight += experiment.getWeight();
            totalRating += experiment.getRating();
        }

        // Rule 3: Apply the 7 kg thermal mass credit once
        // when both experiments 6 and 12 are selected.
        if (containsID(manifest, "6") && containsID(manifest, "12")) {

            totalWeight -= 7;
        }

        // Check the adjusted weight against capacity.
        if (totalWeight > capacity) {
            return new Evaluation(false, totalWeight, totalRating, "Rejected: Exceeds 700 kg capacity.");
        }

        return new Evaluation(true, totalWeight, totalRating, "Accepted.");
    }

    /*
     * BRUTE FORCE / EXHAUSTIVE SEARCH
     *
     * Generates all 2^13 = 8192 possible combinations.
     * Each combination is evaluated using the shared
     * manifest evaluator.
     *
     * Only valid manifests are stored.
     */
    public static List<Solution> bruteForce(Experiment[] experiments, int capacity) {

        List<Solution> solutions = new ArrayList<>();

        int n = experiments.length;

        // Calculate the number of possible subsets.
        int totalSubsets = 1 << n;

        for (int mask = 0; mask < totalSubsets; mask++) {

            List<Experiment> manifest = new ArrayList<>();

            // Use the bits of the current mask to determine
            // which experiment packages are selected.
            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {
                    manifest.add(experiments[i]);
                }
            }

            // Evaluate the candidate using all revised rules.
            Evaluation result = evaluateManifest(manifest, capacity);

            if (result.valid) {
                solutions.add(new Solution(manifest, result.adjustedWeight, result.totalRating));
            }
        }

        // Sort by highest rating first.
        // If ratings tie, prefer the lighter manifest.
        solutions.sort(Comparator.comparingInt((Solution s) -> s.totalRating).reversed().thenComparingInt(s -> s.totalWeight));
        return solutions;
    }
}

    

    


/*
 * DYNAMIC PROGRAMMING (DP) - PSEUDOCODE ONLY
 *
 * This project uses brute force to find the optimal
 * payload combination. Dynamic programming is not
 * implemented, but the following pseudocode describes
 * how it could be approached.
 *
 * PSEUDOCODE:
 *
 * 1. Create a DP table to store the maximum science
 *    rating achievable for each available weight capacity.
 *
 * 2. Initialize all DP table entries to zero.
 *
 * 3. Iterate through each science experiment.
 *
 * 4. For each experiment, examine every possible
 *    weight capacity from 700 down to the experiment's
 *    weight.
 *
 * 5. Calculate the rating obtained by including the
 *    current experiment.
 *
 * 6. Compare that rating with the best rating obtained
 *    without including the experiment.
 *
 * 7. Store whichever option produces the higher rating.
 *
 * 8. After processing all experiments, identify the
 *    highest achievable rating and reconstruct the
 *    selected experiment combination.
 *
 * REVISED CONSTRAINTS:
 *
 * A complete DP implementation would also need to track:
 *
 * - The number of selected solar experiments (IDs 2, 3, 7).
 * - Whether Experiment 11 has been selected before allowing 11a.
 * - Whether Experiments 6 and 12 are both selected so that
 *   the 7 kg thermal mass credit can be applied.
 *
 * A basic knapsack DP table would not be sufficient
 * without expanding its state to account for these rules.
 *
 * COMPLEXITY COMPARISON:
 *
 * Brute Force:
 * Time Complexity: O(n * 2^n)
 * Examines every possible subset of experiments.
 *
 * Dynamic Programming:
 * Basic Time Complexity: O(n * W)
 * Avoids repeatedly evaluating the same subproblems.
 *
 * NOTE:
 * Dynamic programming is not implemented in this project.
 * The brute-force algorithm is used to guarantee the
 * optimal solution for the current 13-package dataset.
 */

