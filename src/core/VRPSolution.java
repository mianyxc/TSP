package core;

import java.util.ArrayList;

//Solution类定义了可能的解（即遍历顺序），包括游戏进行过程中不完整的解
public class VRPSolution {
	public VRPInfo problem; //对应的VRP
	public boolean[][] x; //遍历顺序
	public double totalCost; //总成本，在VRP中是总距离
	public ArrayList<Pair> addOrder;
	
	//构造空白解
	public VRPSolution(VRPInfo problem) {
		this.problem = problem;
		x = new boolean[problem.num+2][problem.num+2];
		for(int i = 0; i < problem.num + 2; i++) {
			for(int j = 0; j < problem.num + 2; j++) {
				x[i][j] = false;
			}
		}
		totalCost = 0;
		addOrder = new ArrayList<Pair>();
	}
	
	//构造现有的解
	public VRPSolution(VRPInfo problem, boolean[][] xtemp) {
		this.problem = problem;
		x = new boolean[problem.num+2][problem.num+2];
		for(int i = 0; i < problem.num + 2; i++) {
			for(int j = 0; j < problem.num + 2; j++) {
				x[i][j] = xtemp[i][j];
			}
		}
		refreshCost();
	}
	
	//计算总成本
	private double getCost() {
		double cost = 0;
		for(int i = 0; i < problem.num + 2; i++) {
			for(int j = 0; j < problem.num + 2; j++) {
				if(x[i][j]) {
					cost += problem.distance[i][j];
				}
			}
		}
		return cost;
	}
	
	//重新计算总成本
	private void refreshCost() {
		totalCost = getCost();
	}
	
	//向当前解中添加一个点
	public boolean add(int i, int j) {
		if(!x[i][j]) {
			x[i][j] = true;
			addOrder.add(new Pair(i,j));
			refreshSatisfied();
			refreshCost();
			System.out.println(problem.points[j].isSatisfied);
			return true;
		} else {
			return false;
		}
	}
	
	private void refreshSatisfied() {
		for(VRPPoint p: problem.points) {
			p.isSatisfied = false;
		}
		for(Pair p: addOrder) {
			problem.points[p.i].isSatisfied = true;
			problem.points[p.j].isSatisfied = true;
		}
	}

	//从当前解中撤销一个点
	public int remove() {
		if(addOrder.size() != 0) {
			int backIndex = addOrder.get(addOrder.size()-1).i;
			x[addOrder.get(addOrder.size()-1).i][addOrder.get(addOrder.size()-1).j] = false;
			addOrder.remove(addOrder.size()-1);
			refreshSatisfied();
			refreshCost();
			return backIndex;
		} else {
			return -1;
		}
	}
	
	//清除当前解
	public void removeAll() {
		while(addOrder.size() != 0) {
			remove();
		}
	}
}
