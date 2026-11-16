package knapsackProblem;

import java.util.ArrayList;
import java.util.List;

import knapsackProblem.BruteForce.Evaluation;
import knapsackProblem.BruteForce.Solution;

/*
 * Team Name: Team 2
 * Team Members: Matthew Meek, Lia Day, Rowan Bailey
 * Course: CS 2430- 501
 * Project: Programming Project 3 - Fall 2026
 * Author: Lia Day and Rowan Bailey
 */

public class Main {

	public static void main(String[] args) {
		
		Experiment[] experiments= {
				new Experiment("1","Cloud Patterns", 36, 5),
				new Experiment("2","Solar Flares", 264, 9),
				new Experiment("3","Solar Power", 188, 6),
				new Experiment("4","Binary Stars", 203, 8),
				new Experiment("5","Relativity", 104, 8),
				new Experiment("6","Seed Viability", 7, 4),
				new Experiment("7","Sun Spots", 90, 2),
				new Experiment("8","Mice Tumors", 65, 8),
				new	Experiment("9","Microgravity Plant Growth", 75, 5),
				new Experiment("10","Micrometeorites", 170, 9),
				new Experiment("11","Cosmic Rays (Primary Band)", 80, 7),
				new Experiment("11a","Cosmic Rays (Secondary Band", 10, 5),
				new Experiment("12","Yeast Fermentation", 27, 4)	
		};
		
		int totalCapacity = 700;
		
		/*
	     * MAIN METHOD
	     *
	     * Runs brute force, all three greedy strategies,
	     * and the revised rule tests.
	     */
		

		System.out.println("======================================");
		System.out.println(" SPACE SHUTTLE PAYLOAD OPTIMIZATION");
		System.out.println("======================================");

		System.out.println("Packages: " + experiments.length);
		System.out.println("Capacity: " + totalCapacity + " kg");
		System.out.println("Possible Subsets: "
				+ (1 << experiments.length));

		// Run brute force.
		List<Solution> solutions = BruteForce.bruteForce(experiments, totalCapacity);

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

		//Run all three greedy strategies
		
		System.out.println("\n======================================");
        System.out.println(" Greedy Algorithms Results");
        System.out.println("======================================");

		GreedyAlgorithms greedyAlgs = new GreedyAlgorithms();

		Experiment[] highestRatedResults = greedyAlgs.highestRatingFirst(experiments, totalCapacity);
		Experiment[] lightestFirst = greedyAlgs.lightestFirst(experiments, totalCapacity);
		Experiment[] bestRatingToWeightRatio = greedyAlgs.bestRatingToWeightFirst(experiments, totalCapacity);

		printResults("Highest Rating First", highestRatedResults);
		printResults("Lightest First", lightestFirst);
		printResults("Best Rating To Weight First", bestRatingToWeightRatio);


		// Compare greedy results with the optimal rating.
		int optimalRating = solutions.get(0).totalRating;

		int highestRating = getTotalRating(highestRatedResults);
		int lightesteRating = getTotalRating(lightestFirst);
		int bestRatioRating = getTotalRating(bestRatingToWeightRatio);

		System.out.println("\n======================================");
		System.out.println(" COMPARISON SUMMARY");
		System.out.println("======================================");

		System.out.println("Brute Force Optimal Rating: " + optimalRating);
		System.out.println("Highest Rating First: " + highestRating + (highestRating == optimalRating ? " - MATCHES OPTIMAL" : " - DOES NOT MATCH OPTIMAL"));
		System.out.println("Lightest First: " + lightesteRating + (lightesteRating == optimalRating ? " - MATCHES OPTIMAL" : " - DOES NOT MATCH OPTIMAL"));
		System.out.println("Best Ratio First: " + bestRatioRating + (bestRatioRating == optimalRating ? " - MATCHES OPTIMAL" : " - DOES NOT MATCH OPTIMAL"));

		// Run the focused rule tests.
		runRuleTests(experiments);

	}
	
	public static int getTotalRating(Experiment[] experiments) {
		int totalRating = 0;
		
		for (Experiment experiment : experiments) {
			totalRating += experiment.getRating()
;		}
		
		return totalRating;
	}
	
	public static void printResults(String title, Experiment[] experiments) {

        System.out.println("\n" + title);
        System.out.println("--------------------------------------");

        System.out.print("Selected IDs: ");

        for (Experiment experiment : experiments) {
            System.out.print(experiment.getid() + " ");
        }

        System.out.println("\nSelected Experiments:");
        
        int totalWeight = 0;
        int totalRating = 0;

        for (Experiment experiment : experiments) {

            System.out.println("  " + experiment.getid() + " - "
                + experiment.getName()
                + " | " + experiment.getWeight() + " kg"
                + " | Rating: " + experiment.getRating());
            
            totalWeight += experiment.getWeight();
            totalRating +=experiment.getRating();
        }
        
        System.out.println("Total Weight: " + totalWeight + " kg");
        System.out.println("Total Rating: " + totalRating);
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
            System.out.print(experiment.getid() + " ");
        }

        System.out.println("\nSelected Experiments:");

        for (Experiment experiment : solution.chosen) {

            System.out.println("  " + experiment.getid() + " - "
                + experiment.getName()
                + " | " + experiment.getWeight() + " kg"
                + " | Rating: " + experiment.getRating());
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
    public static void runRuleTests(Experiment[] experiments) {

        System.out.println("\n======================================");
        System.out.println(" REVISED MANIFEST RULE TESTS");
        System.out.println("======================================");

        // TEST 1: Solar instrument restriction.
        List<Experiment> solarTest = new ArrayList<>();

        solarTest.add(experiments[1]); // ID 2
        solarTest.add(experiments[2]); // ID 3
        solarTest.add(experiments[6]); // ID 7

        Evaluation solarResult =
            BruteForce.evaluateManifest(solarTest, 700);

        System.out.println("\nTest 1: Solar Instrument Limit");
        System.out.println("Candidate IDs: {2, 3, 7}");
        System.out.println("Expected: REJECT");
        System.out.println("Actual: " + (solarResult.valid ? "ACCEPT" : "REJECT"));
        System.out.println("Reason: " + solarResult.reason);

        // TEST 2: Experiment 11a prerequisite.
        List<Experiment> prerequisiteTest = new ArrayList<>();

        prerequisiteTest.add(experiments[11]); // ID 11a

        Evaluation prerequisiteResult =  BruteForce.evaluateManifest(prerequisiteTest, 700);

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

        Evaluation thermalResult =  BruteForce.evaluateManifest(thermalTest, 700);

        System.out.println("\nTest 3: Thermal Mass Credit");
        System.out.println("Candidate IDs: {1, 2, 4, 6, 10, 12}");
        System.out.println("Expected: ACCEPT");
        System.out.println("Expected Baseline Mass: 707 kg");
        System.out.println("Expected Adjusted Mass: 700 kg");
        System.out.println("Actual: " + (thermalResult.valid ? "ACCEPT" : "REJECT"));
        System.out.println("Actual Adjusted Mass: " + thermalResult.adjustedWeight + " kg");
        System.out.println("Actual Rating: " + thermalResult.totalRating);
    }
}




