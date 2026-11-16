package knapsackProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BruteForceTest {
	
	List<BruteForce.Solution> solutions = BruteForce.bruteForce();
	
	private List<String> names(BruteForce.Solution solution){
		List<String> names = new ArrayList<>();
		
		for(BruteForce.Experiment e : solution.chosen) {
			names.add(e.name);
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
		
		BruteForce.Solution results = solutions.get(0);
		assertEquals(expectedNames, names(results));
		assertEquals(expectedWeight, results.totalWeight);
		assertEquals(expectedRating, results.totalRating);
		
	}
	
	void testBruteForce_thirdBestSolution() {
		List<String> expectedNames = Arrays.asList("Cloud Patters", "Solar Power", "Relativity", "Seed Viability", "Mice Tumors", "Micrometeorites", "Cosmic Rays", "Yeast Fermentation");
		
		int expectedWeight = 672;
		int expectedRating = 51;
		
		BruteForce.Solution results = solutions.get(0);
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
