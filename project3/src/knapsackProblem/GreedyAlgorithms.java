package knapsackProblem;

import java.util.Arrays;
import java.util.Comparator;

public class GreedyAlgorithms {
	
	private void validateInputs(Experiment[] experiments, int capacity) {
		if (experiments == null) {
			throw new IllegalArgumentException("Experiments can't be null");
		}
		
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity can't be negative");
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
		
		Arrays.sort(sortedExperiments, Comparator
			.comparingInt(Experiment::getRating)
			.reversed()
			.thenComparing(Experiment::getWeight)
		);
				
		
		return resultArray(sortedExperiments, capacity);
		
	}
	
	public Experiment[] lightestFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		Experiment[] sortedExperiments = experiments.clone();
		
		Arrays.sort(sortedExperiments, 
			Comparator.comparingInt(Experiment::getWeight)
			.thenComparing(Comparator.comparingInt(Experiment::getRating).reversed())
		);
		
		return resultArray(sortedExperiments, capacity);
	}
	
	
	public Experiment[] bestRatingToWeightFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		Experiment[] sortedExperiments = experiments.clone();
		
		double[] scoreArray = new double[experiments.length];
		
		for (int i = 0; i < sortedExperiments.length; i++){
			scoreArray[i] = (double) sortedExperiments[i].getRating() / sortedExperiments[i].getWeight();
		}
		
		CombinedArrays[] combinedArray = new CombinedArrays[sortedExperiments.length];
		
		//creates a new combined object of the experiment and its score at index i.
		for(int i = 0; i < sortedExperiments.length; i++) {
			combinedArray[i] = new CombinedArrays(sortedExperiments[i], scoreArray[i]); 
		}
		
		//sorts the combined array by score, rating is used as a tie breaker. 
		Arrays.sort(combinedArray, Comparator
			.comparingDouble(CombinedArrays::getScore)
			.reversed()
			.thenComparing(Comparator.comparingInt((CombinedArrays e) -> e.getExperiment().getRating())
			.reversed()) 
		);
		
		//loops through combinedArray and replaces each index in sortedExperiments with its corresponding Experiment.
		for (int i = 0; i < sortedExperiments.length; i++){
			sortedExperiments[i] = combinedArray[i].getExperiment();
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
