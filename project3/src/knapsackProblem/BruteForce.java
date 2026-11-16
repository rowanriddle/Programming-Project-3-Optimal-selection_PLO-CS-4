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

    private static final int CAPACITY = 700;

    /*
     * Experiment class stores the ID, name, baseline mass,
     * and science rating of each available package.
     */
    static class Experiment {
        String id;
        String name;
        int weight;
        int rating;

        public Experiment(String id, String name, int weight, int rating) {
            this.id = id;
            this.name = name;
            this.weight = weight;
            this.rating = rating;
        }
    }

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
        List<Experiment> chosen;
        int totalWeight;
        int totalRating;

        public Solution(List<Experiment> chosen, int totalWeight, int totalRating) {
            this.chosen = chosen;
            this.totalWeight = totalWeight;
            this.totalRating = totalRating;
        }
    }

    /*
     * All 13 selectable science packages.
     *
     * Experiment 11a is a separate package with its
     * own mass and rating.
     */
    private static final Experiment[] experiments = {

        new Experiment("1", "Cloud Patterns", 36, 5),
        new Experiment("2", "Solar Flares", 264, 9),
        new Experiment("3", "Solar Power", 188, 6),
        new Experiment("4", "Binary Stars", 203, 8),
        new Experiment("5", "Relativity", 104, 8),
        new Experiment("6", "Seed Viability", 7, 4),
        new Experiment("7", "Sun Spots", 90, 2),
        new Experiment("8", "Mice Tumors", 65, 8),
        new Experiment("9", "Microgravity Plant Growth", 75, 5),
        new Experiment("10", "Micrometeorites", 170, 9),
        new Experiment("11", "Cosmic Rays (Primary Band)", 80, 7),
        new Experiment("11a", "Cosmic Rays (Secondary Band)", 10, 5),
        new Experiment("12", "Yeast Fermentation", 27, 4)

    };

    /*
     * Checks whether a manifest contains an experiment
     * with the specified ID.
     */
    private static boolean containsID(List<Experiment> manifest, String id) {
        for (Experiment experiment : manifest) {
            if (experiment.id.equals(id)) {
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
    public static Evaluation evaluateManifest(
            List<Experiment> manifest) {

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
            totalWeight += experiment.weight;
            totalRating += experiment.rating;
        }

        // Rule 3: Apply the 7 kg thermal mass credit once
        // when both experiments 6 and 12 are selected.
        if (containsID(manifest, "6") && containsID(manifest, "12")) {

            totalWeight -= 7;
        }

        // Check the adjusted weight against capacity.
        if (totalWeight > CAPACITY) {
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
    public static List<Solution> bruteForce() {

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
            Evaluation result = evaluateManifest(manifest);

            if (result.valid) {
                solutions.add(new Solution(manifest, result.adjustedWeight, result.totalRating));
            }
        }

        // Sort by highest rating first.
        // If ratings tie, prefer the lighter manifest.
        solutions.sort(Comparator.comparingInt((Solution s) -> s.totalRating).reversed().thenComparingInt(s -> s.totalWeight));
        return solutions;
    }

    /*
     * GREEDY STRATEGY 1: HIGHEST RATING FIRST
     *
     * Ranking:
     * 1. Highest science rating first.
     * 2. Lowest baseline mass breaks ties.
     * 3. ID breaks remaining ties.
     */
    public static Solution highestRatingFirst() {

        List<Experiment> sorted = new ArrayList<>(
            List.of(experiments)
        );

        sorted.sort(Comparator.comparingInt((Experiment e) -> e.rating).reversed().thenComparingInt(e -> e.weight).thenComparing(e -> e.id));
        return buildGreedyManifest(sorted);
    }

    /*
     * GREEDY STRATEGY 2: LIGHTEST FIRST
     *
     * Ranking:
     * 1. Lowest baseline mass first.
     * 2. Highest rating breaks ties.
     * 3. ID breaks remaining ties.
     */
    public static Solution lightestFirst() {

        List<Experiment> sorted = new ArrayList<>(List.of(experiments));
        sorted.sort(Comparator.comparingInt((Experiment e) -> e.weight).thenComparing(Comparator.comparingInt((Experiment e) -> e.rating).reversed()).thenComparing(e -> e.id));
        return buildGreedyManifest(sorted);
    }

    /*
     * GREEDY STRATEGY 3: BEST RATING-TO-WEIGHT RATIO
     *
     * Ranking:
     * 1. Highest rating / baseline mass first.
     * 2. Highest rating breaks ties.
     * 3. Lowest mass breaks remaining ties.
     */
    public static Solution bestRatioFirst() {

        List<Experiment> sorted = new ArrayList<>(List.of(experiments));
        sorted.sort(Comparator.comparingDouble((Experiment e) -> (double) e.rating / e.weight).reversed().thenComparing(Comparator.comparingInt((Experiment e) -> e.rating).reversed()).thenComparingInt(e -> e.weight));
        return buildGreedyManifest(sorted);
    }

    /*
     * BUILDS A GREEDY MANIFEST
     *
     * Examines packages in their sorted ranking order.
     * Every tentative addition is evaluated using the
     * shared manifest evaluator.
     *
     * If Experiment 11a appears before Experiment 11,
     * it is temporarily deferred. After the main ranking
     * pass, it is reconsidered if Experiment 11 was added.
     *
     * This allows the secondary band to be considered
     * without violating its prerequisite.
     */
    private static Solution buildGreedyManifest(
            List<Experiment> sorted) {

        List<Experiment> manifest = new ArrayList<>();
        List<Experiment> deferred = new ArrayList<>();

        for (Experiment candidate : sorted) {

            // Defer 11a if its prerequisite has not
            // been selected yet.
            if (candidate.id.equals("11a") && !containsID(manifest, "11")) {
                deferred.add(candidate);
                continue;
            }

            // Make a temporary candidate manifest.
            List<Experiment> tentative =new ArrayList<>(manifest);
            tentative.add(candidate);

            // Check all revised rules and adjusted mass.
            Evaluation result = evaluateManifest(tentative);

            // Keep the package only if the manifest
            // remains valid.
            if (result.valid) {
                manifest.add(candidate);
            }
        }

        // Reconsider deferred packages after the main pass.
        for (Experiment candidate : deferred) {

            if (containsID(manifest, "11")) {

                List<Experiment> tentative = new ArrayList<>(manifest);
                tentative.add(candidate);

                Evaluation result = evaluateManifest(tentative);

                if (result.valid) {
                    manifest.add(candidate);
                }
            }
        }

        Evaluation finalResult = evaluateManifest(manifest);

        return new Solution(manifest, finalResult.adjustedWeight, finalResult.totalRating);
    }

    /*
     * PRINTS A SOLUTION
     *
     * Displays experiment IDs, names, adjusted weight,
     * and total science rating.
     */
    public static void displaySolution(String title, Solution solution) {

        System.out.println("\n" + title);
        System.out.println("--------------------------------------");

        System.out.print("Selected IDs: ");

        for (Experiment experiment : solution.chosen) {
            System.out.print(experiment.id + " ");
        }

        System.out.println("\nSelected Experiments:");

        for (Experiment experiment : solution.chosen) {

            System.out.println("  " + experiment.id + " - "
                + experiment.name
                + " | " + experiment.weight + " kg"
                + " | Rating: " + experiment.rating);
        }

        System.out.println("Adjusted Mass: " + solution.totalWeight + " kg");
        System.out.println("Total Rating: " + solution.totalRating);
    }

    /*
     * TESTS FOR THE THREE NEW MANIFEST RULES
     *
     * Each test creates a sample manifest, evaluates it,
     * and prints the expected and actual results.
     */
    public static void runRuleTests() {

        System.out.println("\n======================================");
        System.out.println(" REVISED MANIFEST RULE TESTS");
        System.out.println("======================================");

        // TEST 1: Solar instrument restriction.
        List<Experiment> solarTest = new ArrayList<>();

        solarTest.add(experiments[1]); // ID 2
        solarTest.add(experiments[2]); // ID 3
        solarTest.add(experiments[6]); // ID 7

        Evaluation solarResult =
            evaluateManifest(solarTest);

        System.out.println("\nTest 1: Solar Instrument Limit");
        System.out.println("Candidate IDs: {2, 3, 7}");
        System.out.println("Expected: REJECT");
        System.out.println("Actual: " + (solarResult.valid ? "ACCEPT" : "REJECT"));
        System.out.println("Reason: " + solarResult.reason);

        // TEST 2: Experiment 11a prerequisite.
        List<Experiment> prerequisiteTest = new ArrayList<>();

        prerequisiteTest.add(experiments[11]); // ID 11a

        Evaluation prerequisiteResult = evaluateManifest(prerequisiteTest);

        System.out.println("\nTest 2: Experiment 11a Prerequisite");
        System.out.println("Candidate IDs: {11a}");
        System.out.println("Expected: REJECT");
        System.out.println("Actual: "+ (prerequisiteResult.valid? "ACCEPT" : "REJECT"));
        System.out.println("Reason: "+ prerequisiteResult.reason);

        // TEST 3: Thermal mass credit.
        List<Experiment> thermalTest = new ArrayList<>();

        thermalTest.add(experiments[0]); // ID 1
        thermalTest.add(experiments[1]); // ID 2
        thermalTest.add(experiments[3]); // ID 4
        thermalTest.add(experiments[5]); // ID 6
        thermalTest.add(experiments[9]); // ID 10
        thermalTest.add(experiments[12]); // ID 12

        Evaluation thermalResult = evaluateManifest(thermalTest);

        System.out.println("\nTest 3: Thermal Mass Credit");
        System.out.println("Candidate IDs: {1, 2, 4, 6, 10, 12}");
        System.out.println("Expected: ACCEPT");
        System.out.println("Expected Baseline Mass: 707 kg");
        System.out.println("Expected Adjusted Mass: 700 kg");
        System.out.println("Actual: " + (thermalResult.valid ? "ACCEPT" : "REJECT"));
        System.out.println("Actual Adjusted Mass: " + thermalResult.adjustedWeight + " kg");
        System.out.println("Actual Rating: " + thermalResult.totalRating);
    }

    /*
     * MAIN METHOD
     *
     * Runs brute force, all three greedy strategies,
     * and the revised rule tests.
     */
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" SPACE SHUTTLE PAYLOAD OPTIMIZATION");
        System.out.println("======================================");

        System.out.println("Packages: " + experiments.length);
        System.out.println("Capacity: " + CAPACITY + " kg");
        System.out.println("Possible Subsets: "
                + (1 << experiments.length));

        // Run brute force.
        List<Solution> solutions = bruteForce();

        System.out.println("\n======================================");
        System.out.println(" BRUTE FORCE RESULTS");
        System.out.println("======================================");

        // Display the three highest-rated valid solutions.
        for (int i = 0; i < 3 && i < solutions.size(); i++) {

            String title = "Solution #" + (i + 1);

            if (i == 0) {
                title += " - OPTIMAL";
            }

            displaySolution(title, solutions.get(i));
        }

        // Run all three greedy strategies.
        Solution ratingResult = highestRatingFirst();
        Solution weightResult = lightestFirst();
        Solution ratioResult = bestRatioFirst();

        displaySolution("GREEDY: HIGHEST RATING FIRST", ratingResult);
        displaySolution("GREEDY: LIGHTEST FIRST", weightResult);
        displaySolution("GREEDY: BEST RATING-TO-WEIGHT RATIO", ratioResult);

        // Compare greedy results with the optimal rating.
        int optimalRating = solutions.get(0).totalRating;

        System.out.println("\n======================================");
        System.out.println(" COMPARISON SUMMARY");
        System.out.println("======================================");

        System.out.println("Brute Force Optimal Rating: " + optimalRating);
        System.out.println("Highest Rating First: " + ratingResult.totalRating + (ratingResult.totalRating == optimalRating ? " - MATCHES OPTIMAL" : " - DOES NOT MATCH OPTIMAL"));
        System.out.println("Lightest First: " + weightResult.totalRating + (weightResult.totalRating == optimalRating ? " - MATCHES OPTIMAL" : " - DOES NOT MATCH OPTIMAL"));
        System.out.println("Best Ratio First: " + ratioResult.totalRating + (ratioResult.totalRating == optimalRating ? " - MATCHES OPTIMAL" : " - DOES NOT MATCH OPTIMAL"));

        // Run the focused rule tests.
        runRuleTests();
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

