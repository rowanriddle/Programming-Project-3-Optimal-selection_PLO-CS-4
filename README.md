# Programming-Project-3-Optimal-selection_PLO-CS-4

# CS 2430 – Programming Project 3
## Space Shuttle Payload Optimization

**Team:** Team 2  
**Course:** CS 2430  
**Semester:** Fall 2026  
**Language:** Java

## Project Overview

This project investigates the 0/1 Knapsack Problem by selecting scientific experiments for a space shuttle with a maximum payload capacity of 700 kg.

Each experiment has a weight and science rating. The goal is to maximize the total science rating without exceeding the payload limit or violating additional selection restrictions.

The project compares three greedy algorithms against a brute-force exhaustive search.

## Algorithms Implemented

### Greedy Strategies

- **Highest Rating First:** Prioritizes experiments with the highest science ratings.
- **Lightest First:** Prioritizes experiments with the lowest weights.
- **Best Rating-to-Weight Ratio First:** Prioritizes experiments with the highest rating per kilogram.

### Brute Force

Evaluates all 8,192 possible combinations of the 13 experiment packages and identifies the three highest-rated valid solutions.

### Dynamic Programming

Dynamic programming was not implemented. Pseudocode comments describing a possible DP approach are included in the source code.

## Payload Restrictions

The program accounts for three additional requirements:

1. No more than two of Experiments 2, 3, and 7 may be selected.
2. Selecting both Experiments 6 and 12 provides a 7 kg thermal mass credit.
3. Experiment 11a can only be selected if Experiment 11 is also selected.

The adjusted payload weight must not exceed 700 kg.

## Project Structure

```text
knapsackProblem/
│
├── Experiment.java
├── ScoresExperiments.java
├── GreedyAlgorithms.java
├── BruteForce.java
└── Main.java
```

- `Experiment.java`: Stores experiment IDs, names, weights, and ratings.
- `ScoresExperiments.java`: Stores experiments with their rating-to-weight ratios.
- `GreedyAlgorithms.java`: Implements the three greedy strategies and manifest validation.
- `BruteForce.java`: Evaluates all possible experiment combinations.
- `Main.java`: Runs the algorithms and displays the results.

## Running the Program

1. Clone or download the project repository.
2. Open
