package core;

public class TSP {
	Problem problem;
	Solution optimalSolution;
	Solution playerSolution;
	
	public TSP(int num, double xl, double xu, double yl, double yu) {
		problem = new Problem(num, xl, xu, yl, yu);
		playerSolution = new Solution(problem);
		
		//此处补充求最优解的方法
		
	}
	
	public boolean add(int pointIndex) {
		return playerSolution.add(pointIndex);
	}
	
	public boolean remove() {
		return playerSolution.remove();
	}
}
