package core;

import java.util.Arrays;

import solver.CplexSolver;

public class TSP {
	public Problem problem;
	public Solution optimalSolution;
	public Solution playerSolution;
	
	public TSP(int num, double xl, double xu, double yl, double yu) {
		problem = new Problem(num, xl, xu, yl, yu);
		playerSolution = new Solution(problem);
		CplexSolver solver = new CplexSolver(num, problem.distance);
		optimalSolution = new Solution(problem, solver.order);
	}
	
	public boolean add(int pointIndex) {
		return playerSolution.add(pointIndex);
	}
	
	public boolean remove() {
		return playerSolution.remove();
	}
}
