package knapsackProblem;

/*
 * Team Name: Team 2
 * Team Members: Matthew Meek, Lia Day, Rowan Bailey
 * Course: CS 2430- 501
 * Project: Programming Project 3 - Fall 2026
 *
 */

public class ScoresExperiments {
	private Experiment experiment;
	private double score;
	
	public ScoresExperiments(Experiment experiment, double score) {
		this.experiment = experiment;
		this.score = score;
	}

	public Experiment getExperiment() {
		return experiment;
	}

	public double getScore() {
		return score;
	}
}
