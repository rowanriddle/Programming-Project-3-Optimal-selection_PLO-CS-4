package knapsackProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
* Team Name: Team 2
* Team Members: Matthew Meek, Lia Day, Rowan Bailey
* Course: CS 2430- 501
* Project: Programming Project 3 - Fall 2026
* Author: Matthew Meek
*/

class BruteForceTest {
	
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
	
	List<BruteForce.Solution> solutions = BruteForce.bruteForce(experiments, 700);
	
	private List<String> names(BruteForce.Solution solution){
		List<String> names = new ArrayList<>();
		
		for(Experiment e : solution.chosen) {
			names.add(e.getName());
		}
		return names;
	}
	
	@Test
	void testBruteForce_optimalSolution() {
		List<String> expectedNames = Arrays.asList("Cloud Patters", "Binary Stars", "Relativity", "Seed Viability", "Mice Tumors", "Micrometeorites", "Cosmic Rays", "Yeast Fermentation");
		
		int expectedWeight = 692;
		int expectedRating = 53;
		
		BruteForce.Solution results = solutions.get(0);
		assertEquals(expectedNames, names(results));
		assertEquals(expectedWeight, results.totalWeight);
		assertEquals(expectedRating, results.totalRating);
		
	}
	
	@Test
	void testBruteForce_secondBestSolution() {
		List<String> expectedNames = Arrays.asList("Cloud Patterns", "Relativity", "Seed Viability", "Sun Spots", "Mice Tumors", "Microgravity Plan Growth", "Micrometeorites", "Cosmic Rays", "Yeast Fermentation");
		
		int expectedWeight = 654;
		int expectedRating = 52;
		
		BruteForce.Solution results = solutions.get(1);
		assertEquals(expectedNames, names(results));
		assertEquals(expectedWeight, results.totalWeight);
		assertEquals(expectedRating, results.totalRating);
		
	}
	
	@Test
	void testBruteForce_thirdBestSolution() {
		List<String> expectedNames = Arrays.asList("Cloud Patters", "Solar Power", "Relativity", "Seed Viability", "Mice Tumors", "Micrometeorites", "Cosmic Rays", "Yeast Fermentation");
		
		int expectedWeight = 672;
		int expectedRating = 51;
		
		BruteForce.Solution results = solutions.get(2);
		assertEquals(expectedNames, names(results));
		assertEquals(expectedWeight, results.totalWeight);
		assertEquals(expectedRating, results.totalRating);
		
	}

	@Test
	void testBruteForce_noSOlutionExceedsCapacity() {
		int expected = 700;
		int results = 0;
		for(BruteForce.Solution s : solutions) {
			results = Math.max(results, s.totalWeight);
		}
		assertTrue(results <= expected);
	}
}
