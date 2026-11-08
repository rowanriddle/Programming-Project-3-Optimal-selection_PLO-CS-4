package knapsackProblem;

public class GreedyAlgorithms {

	public Experiment[] highestRatingFirst(Experiment[] experiments, int capacity) { 
		Experiment[] sortedExperiments = experiments.clone();
		
		
		for(int i = 0; i < sortedExperiments.length; i++) {
			for(int j = i + 1; j < sortedExperiments.length; j++) {
				
				if (experiments[j].getRating() > experiments[i].getRating()) {
					
					Experiment temp = experiments [i];
					experiments [i] = experiments [j];
					experiments [j] = temp;
				}
			}
		}
		return null;
		
	}
}
