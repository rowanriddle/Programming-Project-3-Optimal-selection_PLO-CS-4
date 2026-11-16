package knapsackProblem;

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
