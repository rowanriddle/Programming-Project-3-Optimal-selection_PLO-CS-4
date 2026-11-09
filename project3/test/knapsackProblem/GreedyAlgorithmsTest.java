package knapsackProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GreedyAlgorithmsTest {
	GreedyAlgorithms ga = new GreedyAlgorithms();
	Experiment[] experiments= {
			new Experiment("Cloud Patterns", 36, 5),
			new Experiment("Solar Flares", 264, 9),
			new Experiment("Solar Power", 188, 6),
			new Experiment("Binary Stars", 203, 8),
			new Experiment("Relativity", 104, 8),
			new Experiment("Seed Viability", 7, 4),
			new Experiment("Sun Spots", 90, 2),
			new Experiment("Mice Tumors", 65, 8),
			new	Experiment("Microgravity Plant Growth", 75, 5),
			new Experiment("Micrometeorites", 170, 9),
			new Experiment("Cosmic Rays", 80, 7),
			new Experiment("Yeast Fermentation", 27, 4)	
	};
	
	int totalCapacity = 700;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testHighestRatingFirst_experiments() {
		Experiment[] expected = {new Experiment("Solar Flares", 264, 9), 
				new Experiment("Micrometeorites", 170, 9),
				new Experiment("Mice Tumors", 65, 8),
				new Experiment("Relativity", 104, 8),
				new Experiment("Cosmic Rays", 80, 7),
				new Experiment("Seed Viability", 7, 4)
				};
		Experiment[] results = ga.highestRatingFirst(experiments, 700);
		Arrays.sort(expected,Comparator.comparing(Experiment::getName));
		Arrays.sort(results, Comparator.comparing(Experiment::getName));
	
		for(int i = 0; i < results.length; i++) {
			assertEquals(expected[i].getName(), results[i].getName());
		}
	}
	
	@Test
	void testHighestRatingFirst_Empty() {
		Experiment[] empty_experiments = {
				
		};
		assertThrows(IllegalArgumentException.class, () -> ga.highestRatingFirst(empty_experiments, 700));
	}

}
