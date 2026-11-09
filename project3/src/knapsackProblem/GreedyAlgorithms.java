package knapsackProblem;

import java.util.Arrays;

public class GreedyAlgorithms {
	
	private void validateInputs(Experiment[] experiments, int capacity) {
		if (experiments == null) {
			throw new IllegalArgumentException("Experiments can't be null");
		}
		
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity can't negative");
		}
		
		for (Experiment experiment : experiments) {
			if (experiment == null) {
				throw new IllegalArgumentException("Experiment can't be null");
			}
			
			if (experiment.getWeight() <= 0) {
				throw new IllegalArgumentException("Weight must be positive");
			}
		}
	}

	public Experiment[] highestRatingFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		Experiment[] sortedExperiments = experiments.clone();
		
		for(int i = 0; i < sortedExperiments.length; i++) {
			for(int j = i + 1; j < sortedExperiments.length; j++) {	
				
				if (sortedExperiments[j].getRating() > sortedExperiments[i].getRating() || 
						(sortedExperiments[i].getRating() == sortedExperiments[j].getRating() && sortedExperiments[j].getWeight() < sortedExperiments[i].getWeight())){
							Experiment temp = sortedExperiments [i];
							sortedExperiments [i] = sortedExperiments [j];
							sortedExperiments [j] = temp;
				}
			}
		}
		
		return resultArray(sortedExperiments, capacity);
		
	}
	
	public Experiment[] lightestFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		Experiment[] sortedExperiments = experiments.clone();
		
		for(int i = 0; i < sortedExperiments.length; i++) {
			for(int j = i + 1; j < sortedExperiments.length; j++) {	
				if (sortedExperiments[j].getWeight() < sortedExperiments[i].getWeight() || 
						(sortedExperiments[i].getWeight() == sortedExperiments[j].getWeight() && sortedExperiments[j].getRating() > sortedExperiments[i].getRating())){
							Experiment temp = sortedExperiments [i];
							sortedExperiments [i] = sortedExperiments [j];
							sortedExperiments [j] = temp;
				}
				
			}
		}
		
		return resultArray(sortedExperiments, capacity);
	}
	
	
	public Experiment[] bestRatingToWeightFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		Experiment[] sortedExperiments = experiments.clone();
		
		double[] scoreArray = new double[experiments.length];
		
		for (int i = 0; i < sortedExperiments.length; i++){
			scoreArray[i] = (double) sortedExperiments[i].getRating() / sortedExperiments[i].getWeight();
		}
		
		for(int i = 0; i < scoreArray.length; i++) {
			for(int j = i + 1; j < scoreArray.length; j++) {	
				if (scoreArray[j] > scoreArray[i] ||
						(scoreArray[j] == scoreArray[i] && sortedExperiments[j].getRating() > sortedExperiments[i].getRating())){
					
					double temp = scoreArray [i];
					scoreArray [i] = scoreArray [j];
					scoreArray [j] = temp;
					
					Experiment temporary = sortedExperiments [i];
					sortedExperiments [i] = sortedExperiments [j];
					sortedExperiments [j] = temporary;
				}
			}
		}
		
		return resultArray(sortedExperiments, capacity);
		
	}
	
	private Experiment[] resultArray(Experiment[] experiments, int capacity) {
		Experiment[] result = new Experiment[experiments.length];
		int index = 0;
		int weight = 0;
		
		for (Experiment experiment : experiments) {
			if(weight + experiment.getWeight() <= capacity) {
				result[index] = experiment;
				index++;
				weight += experiment.getWeight();
			}
		}
		
		return Arrays.copyOf(result, index);
	}
}
