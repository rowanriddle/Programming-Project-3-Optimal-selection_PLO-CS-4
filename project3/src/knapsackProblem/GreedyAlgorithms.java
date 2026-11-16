package knapsackProblem;

/*
 * Team Name: Team 2
 * Team Members: Matthew Meek, Lia Day, Rowan Bailey
 * Course: CS 2430- 501
 * Project: Programming Project 3 - Fall 2026
 * Author: Lia Day
 */

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

	/**
	 * Selects experiments based on highest rating to the lowest. 
	 * Experiments with  equal ratings are selected by lowest weight first.  
	 * @param experiments to be evaluated.
	 * @param capacity max capacity of the payload
	 * @return a list of chosen experiments not exceeding the 700kg limit.
	 */
	public Experiment[] highestRatingFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		if(experiments.length == 0) {
			throw new IllegalArgumentException("experiments empty");
		}
		Experiment[] sortedExperiments = experiments.clone();
		
		Arrays.sort(sortedExperiments, Comparator
			.comparingInt(Experiment::getRating)
			.reversed()
			.thenComparing(Experiment::getWeight)
		);
				
		
		return results(sortedExperiments, capacity);
		
	}
	
	/**
	 * Selects experiments from the lowest weight to the highest. 
	 * Experiments with  equal weight are selected by highest rating first.  
	 * @param experiments to be evaluated.
	 * @param capacity max capacity of the payload
	 * @return a list of chosen experiments not exceeding the 700kg limit.
	 */
	public Experiment[] lightestFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		Experiment[] sortedExperiments = experiments.clone();
		
		Arrays.sort(sortedExperiments, 
			Comparator.comparingInt(Experiment::getWeight)
			.thenComparing(Comparator.comparingInt(Experiment::getRating).reversed())
		);
		
		return results(sortedExperiments, capacity);
	}
	
	/**
	 * Selects experiments based on the highest rating to weight ratio.
	 * Each ratio/score is determined by dividing its rating by weight.
	 * Experiments with  equal score are selected by highest rating.  
	 * @param experiments to be evaluated.
	 * @param capacity max capacity of the payload
	 * @return a list of chosen experiments not exceeding the 700kg limit.
	 */
	public Experiment[] bestRatingToWeightFirst(Experiment[] experiments, int capacity) { 
		validateInputs(experiments, capacity);
		
		Experiment[] sortedExperiments = experiments.clone();
		
		double[] scoreArray = new double[experiments.length];
		
		for (int i = 0; i < sortedExperiments.length; i++){
			scoreArray[i] = (double) sortedExperiments[i].getRating() / sortedExperiments[i].getWeight();
		}
		
		ScoresExperiments[] scoresExperimentsArr = new ScoresExperiments[sortedExperiments.length];
		
		//creates a new combined object of the experiment and its score at index i.
		for(int i = 0; i < sortedExperiments.length; i++) {
			scoresExperimentsArr[i] = new ScoresExperiments(sortedExperiments[i], scoreArray[i]); 
		}
		
		//sorts the scoresExperimentsArr by score, rating is used as a tie breaker. 
		Arrays.sort(scoresExperimentsArr, Comparator
			.comparingDouble(ScoresExperiments::getScore)
			.reversed()
			.thenComparing(Comparator.comparingInt((ScoresExperiments e) -> e.getExperiment().getRating())
			.reversed()) 
		);
		
		//loops through scoresExperimentsArr and replaces each index in sortedExperiments with its corresponding Experiment.
		for (int i = 0; i < sortedExperiments.length; i++){
			sortedExperiments[i] = scoresExperimentsArr[i].getExperiment();
		}
		
		return results(sortedExperiments, capacity);
		
	}
	
	/**
	 * Selects results from the sorted array without exceeding the max capacity.
	 * Checks each experiment against manifest rules and applies a thermal mass credit if applicable. 
	 * 
	 * @param experiments sorted array of experiments
	 * @param capacity max capacity allowed in kg
	 * @return an array containing the selected experiments
	 */
	private Experiment[] results(Experiment[] experiments, int capacity) {
		Experiment[] result = new Experiment[experiments.length];
		int index = 0;
		int weight = 0;
		
		for (Experiment experiment : experiments) {
			result[index] = experiment;
			
			Experiment[] tempResult = Arrays.copyOf(result, index + 1); 
			
			int adjustedWeight = weight + experiment.getWeight() - thermalMassCredit(tempResult);
			
			if(isManifestValid(tempResult) && adjustedWeight <= capacity) {
				index++;
				weight += experiment.getWeight();
			}
		}
		
		return Arrays.copyOf(result, index);
	}
	
	/**
	 * Checks manifest for the following applicable rules.
	 * Solar instrument count must be less than three.
	 * Experiment 11a must also include experiment 11 in the manifest.
	 * 
	 * @param experiments array of sorted experiments 
	 * @return a boolean value representing the validity of the manifest.
	 */
	public boolean isManifestValid (Experiment[] experiments) {
		int solarInstrumentCount = 0;
		boolean includesExp11 = false;
		boolean includesExp11a = false;
		
		for (Experiment experiment : experiments) {
			String id = experiment.getid();
			
			if (id.equals("2") || id.equals("3") || id.equals("7")){
				solarInstrumentCount++;
			}
			
			if(id.equals("11")) {
				includesExp11 = true;
			}
			
			if(id.equals("11a")) {
				includesExp11a = true;
			}
		}
		
		if (solarInstrumentCount > 2) {
			return false;
		}
		
		if(includesExp11a && !includesExp11) {
			return false;
		}
		
		return true;
	}
	
	public int thermalMassCredit (Experiment[] experiments) {
		boolean includesExp6 = false;
		boolean includesExp12 = false;
		
		for (Experiment experiment : experiments) {
			if(experiment.getid().equals("6")){
				includesExp6 = true;
			}
			
			if(experiment.getid().equals("12")){
				includesExp12 = true;
			}
		}
		
		if(includesExp6 && includesExp12) {
			return 7;
		}
		
		return 0;
	}
			
}
