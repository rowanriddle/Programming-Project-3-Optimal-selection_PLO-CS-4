package knapsackProblem;

public class Main {

	public static void main(String[] args) {
		
		Experiment[] experiments= {
				new Experiment("Cloud Patterns", 36, 5),
				new Experiment("Solar Flares", 264, 9),
				new Experiment("Solar Power", 188, 6),
				new Experiment("Binary Stars", 203, 8),
				new Experiment("Relativity", 104, 8),
				new Experiment("Seed Viability", 7, 4),
				new Experiment("Sun Spots", 90, 2),
				new Experiment("Mice Tumors", 65, 8),
				new	Experiment("Microgravity Plant Growth", 75, 5),
				new Experiment("Micrometeorites", 170, 9),
				new Experiment("Cosmic Rays", 80, 7),
				new Experiment("Yeast Fermentation", 27, 4)	
		};
		
		int totalCapacity = 700;
		
		GreedyAlgorithms greedyAlgs = new GreedyAlgorithms();
		
		Experiment[] highestRatedResults = greedyAlgs.highestRatingFirst(experiments, totalCapacity);
		Experiment[] lightestFirst = greedyAlgs.lightestFirst(experiments, totalCapacity);
		Experiment[] bestRatingToWeightRatio = greedyAlgs.bestRatingToWeightFirst(experiments, totalCapacity);
		
		System.out.println("Highest Rated First Results");
		printResults(highestRatedResults);
		
		
		System.out.println("Lightest Weight First Results");
		printResults(lightestFirst);
	
		System.out.println("Best Rating-to-Weight Ratio First");
		printResults(bestRatingToWeightRatio);
		
		
	}
	
	public static void printResults(Experiment[] experiments) {
		int totalWeight = 0;
		int totalRating = 0;

		for (Experiment experiment : experiments) {
			System.out.println(experiment.getName() + " Weight: " + experiment.getWeight() + " Rating: " + experiment.getRating());
			
			totalWeight += experiment.getWeight();
			totalRating += experiment.getRating();
		}
		
		System.out.println("Total Weight: " + totalWeight);
		System.out.println("Total Rating: " + totalRating);
		System.out.println();
	}
	
}




