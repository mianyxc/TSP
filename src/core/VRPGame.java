package core;

import solver.VRPSolver;

public class VRPGame {
	public VRPInfo info;
	public VRPSolution optimalSolution;
	public VRPSolution playerSolution;
	
	public VRPGame(int num, int vehicleNum, int xl, int xu, int yl, int yu, int dl, int du) {
		info = new VRPInfo(num, vehicleNum, xl, xu, yl, yu, dl, du);
		playerSolution = new VRPSolution(info);
		VRPSolver solver = new VRPSolver(num+2, info.distance, info.capacity, info.demand, vehicleNum);
		optimalSolution = new VRPSolution(info, solver.x);
	}
	
	public VRPGame() {
		this(0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public boolean add(int i, int j) {
		return playerSolution.add(i, j);
	}
	
	public int remove() {
		return playerSolution.remove();
	}
	
	public void removeAll() {
		playerSolution.removeAll();
	}
}
