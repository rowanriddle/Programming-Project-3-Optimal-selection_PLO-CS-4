# **Programming Project 3 – Scope Document**  
**Team:** Project Team 2  
 **Team Members:** Matthew Meek, Lia Day, Rowan Bailey-Nicholson  
**Course:** CS 2430-501-Fall 2026  
 **Project name:** Programming Project 3: Optimal selection_PLO-CS-4  
## **Required Features**  
This project consists of a Java program that solves the knapsack problem using various algorithmic approaches. The program will determine which combination of the 12 predetermined experiments produces the highest rating using different selection methods.  
- **Experiment:** Creates the Experiment class representing the experiments, the 12 predetermined experiments are stored within an experiment array.  
- **Greedy Algorithms:** Implement three greedy strategies for building a payload without exceeding 700kg.  
  - Highest Rating First: selects experiments based on its rating. In cases where the ratings tied, lowest weight will be the tie breaker.  
  - Lightest First: selects experiments based on the lightest weight first. Ties are broken based on rating.  
  - Best Rating to Weight Ratio First: computes a score = rating/weight. Experiments with the highest score ratio are selected. Ties are broken by lowest weight.  
- **Brute Force:** Generate all 4096 possible experiment combinations. The total weight and rating of each combination is calculated. Combinations exceeding the weight capacity are discarded, and the three highest rated valid combinations are displayed.  
- **Test driver:** Verifies the results of all algorithms using JUnit test.  
- **Experimental results:** Run all four algorithms and record the selected experiments, total weight, and rating.  
- **Performance analysis:** Compares the results of the greedy algorithms vs brute-force and explains why the greedy strategies did not produce the optimal result.  
## **Constraints**  
- Java is the chosen programming language.  
- The experiment data consist of the 12 predetermined experiments provided in canvas.  
- The space shuttle has a max capacity of 700kg.  
- Selected experiments in  each strategies or combination must having a total weight of 700kg or less.  
- The brute force strategy will be used to find the optimal solution.  
- Additional constraints may be added based on change request.  
## **Deliverables**  
- **Team report:** (submitted as a standalone file) A PDF containing the following:  
  - title block  
  - introduction  
  - methods/approach  
  - results  
  - discussion/ analysis  
  - conclusions  
  - summary of work done / lessons learned  
  - sources  
- **Team code package:** A zip exported from the repository containing:  
  - all source code files  
  - /docs folder including project plan, scope document, and UML diagram  
  - README with build/run instructions  
  - CONTRIBUTIONS.md mapping team members to responsibilities and evidence pointers (PR/commit/file/function)  
  - Any data output files (CSV/TSV/TXT) or a clearly labeled section in the team report.  
- **Team screencast:** A video of no more than 10 minutes including the following:  
  - Project structure (including /docs, README, and key source files).  
  - Implementation of the three greedy strategies and brute force approach.  
  - Running the main program to produce an output for the three greedy strategies and brute force approach.  
  - A brief summary of the report's conclusions.  
  - Each team member must participate and explain their contribution (what they owned and how to find it in the zip/repo)  
## **Out-of-Scope Items**  
The following features are not planned for this project because they are not required by the assignment:  
- Additional experiments added or modified to the data set.  
- Dynamic programming implementation.  
