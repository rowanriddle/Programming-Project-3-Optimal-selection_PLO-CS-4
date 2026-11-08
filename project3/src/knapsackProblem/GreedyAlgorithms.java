package knapsackProblem;

public class GreedyAlgorithms {

	public Experiment[] highestRatingFirst(Experiment[] experiments, int capacity) { 
		Experiment[] sortedExperiments = experiments.clone();
		
		
		for(int i = 0; i < sortedExperiments.length; i++) {
			for(int j = i + 1; j < sortedExperiments.length; j++) {
				
				if (sortedExperiments[j].getRating() > sortedExperiments[i].getRating()) {
					
					Experiment temp = sortedExperiments [i];
					sortedExperiments [i] = sortedExperiments [j];
					sortedExperiments [j] = temp;
				}
			}
		}
		
		Experiment[] result = new Experiment[12];
		int index = 0;
		int weight = 0;
		
		for (Experiment experiment : sortedExperiments) {
			if(weight + experiment.getWeight() <= 700) {
				result[index] = experiment;
				index++;
				weight += experiment.getWeight();
			}
		}
		return result;
		
	}
}
