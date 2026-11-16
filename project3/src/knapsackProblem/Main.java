package knapsackProblem;

public class main {

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




