package knapsackProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GreedyAlgorithmsTest {
	GreedyAlgorithms ga = new GreedyAlgorithms();

	Experiment[] experiments = { new Experiment("1,", "Cloud Patterns", 36, 5),
			new Experiment("2,", "Solar Flares", 264, 9), 
			new Experiment("3,", "Solar Power", 188, 6),
			new Experiment("4,", "Binary Stars", 203, 8),
			new Experiment("5,", "Relativity", 104, 8),
			new Experiment("6,", "Seed Viability", 7, 4),
			new Experiment("7,", "Sun Spots", 90, 2),
			new Experiment("8,", "Mice Tumors", 65, 8), 
			new Experiment("9,", "Microgravity Plant Growth", 75, 5),
			new Experiment("10,", "Micrometeorites", 170, 9), 
			new Experiment("11", "Cosmic Rays (Primary Band)", 80, 7),
			new Experiment("11a", "Cosmic Rays (Secondary Band)", 10, 5),
			new Experiment("12", "Yeast Fermentation", 27, 4) };

	int totalCapacity = 700;

	private int totalWeight(Experiment[] results) {
		int total = 0;
		for (Experiment e : results) {
			total += e.getWeight();
		}
		return total;
	}

	private int totalRating(Experiment[] results) {
		int total = 0;
		for (Experiment e : results) {
			total += e.getRating();
		}
		return total;
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testHighestRatingFirst_totals() {
		int expectedWeight = 700;
		int expectedRating = 50;
		Experiment[] results = ga.highestRatingFirst(experiments, 700);
		assertEquals(expectedWeight, totalWeight(results));
		assertEquals(expectedRating, totalRating(results));

	}

	@Test
	void testHighestRatingFirst_experiments() {
		Experiment[] expected = { 
				new Experiment("12", "Yeast Fermentation", 27, 4),
				new Experiment("6,", "Seed Viability", 7, 4),
				new Experiment("1,", "Cloud Patterns", 36, 5),
				new Experiment("8,", "Mice Tumors", 65, 8), 
				new Experiment("9,", "Microgravity Plant Growth", 75, 5),
				new Experiment("11", "Cosmic Rays (Primary Band)", 80, 7),
				new Experiment("5,", "Relativity", 104, 8),
				new Experiment("7,", "Sun Spots", 90, 2),
				new Experiment("10,", "Micrometeorites", 170, 9)
				
		};
		Experiment[] results = ga.highestRatingFirst(experiments, 700);
		Arrays.sort(expected, Comparator.comparing(Experiment::getName));
		Arrays.sort(results, Comparator.comparing(Experiment::getName));
		
		assertEquals(expected.length, results.length);
		for (int i = 0; i < results.length; i++) {
			assertEquals(expected[i].getName(), results[i].getName());
		}
	}

	@Test
	void testBestRatingToWeightFirst_totals() {
		int expectedWeight = 654;
		int expectedRating = 52;
		Experiment[] results = ga.bestRatingToWeightFirst(experiments, 700);
		assertEquals(expectedWeight, totalWeight(results));
		assertEquals(expectedRating, totalRating(results));

		
	}
	@Test
	void testHighestRatingFirst_Empty() {
		Experiment[] empty_experiments = {

		};
		assertThrows(IllegalArgumentException.class, () -> ga.highestRatingFirst(empty_experiments, 700));
	}

	@Test
	void testSolarInstrumentLimit() {
		Experiment[] manifest = { new Experiment("2", "Solar Flares", 264, 9),
				new Experiment("3", "Solar Power", 188, 6), new Experiment("7", "Sun Spots", 90, 2) };
		boolean expected = false;
		boolean results = ga.isManifestValid(manifest);
		assertEquals(expected, results);
	}

	void test11aPrerequisite() {
		Experiment[] manifest = { new Experiment("11a", "Cosmic Rays (Secondary Band)", 10, 5)

		};
		boolean expected = false;
		boolean results = ga.isManifestValid(manifest);
		assertEquals(expected, results);
	}

	@Test
	void testThermalMassCredit_credit() {
		Experiment[] manifest = { new Experiment("1", "Cloud Patterns", 36, 5),
				new Experiment("2", "Solar Flares", 264, 9), new Experiment("4", "Binary Stars", 203, 8),
				new Experiment("6", "Seed Viability", 7, 4), new Experiment("10", "Micrometeorites", 170, 9),
				new Experiment("12", "Yeast Germentation", 27, 4) };

		int expected = 7;
		int results = ga.thermalMassCredit(manifest);
		assertEquals(expected, results);
	}

	void testThermalMassCredit_adjustedMass() {
		Experiment[] manifest = { new Experiment("1", "Cloud Patterns", 36, 5),
				new Experiment("2", "Solar Flares", 264, 9), new Experiment("4", "Binary Stars", 203, 8),
				new Experiment("6", "Seed Viability", 7, 4), new Experiment("10", "Micrometeorites", 170, 9),
				new Experiment("12", "Yeast Germentation", 27, 4) };
		int rawMass = 0;
		for (Experiment e : manifest) {
			rawMass += e.getWeight();
		}

		int expected = 700;
		int results = rawMass - ga.thermalMassCredit(manifest);
		assertEquals(expected, results);
	}

}
