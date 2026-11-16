package knapsackProblem;

/*
 * Team Name: Team 2
 * Team Members: Matthew Meek, Lia Day, Rowan Bailey
 * Course: CS 2430- 501
 * Project: Programming Project 3 - Fall 2026
 *
 */

public class Experiment {
	private String id;
	private String name;
	private int weight;
	private int rating;
	
	public Experiment(String id, String name, int weight, int rating) {
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public int getRating() {
		return rating;
	}
	
	
	public String getid() {
		return id;
	}
}
