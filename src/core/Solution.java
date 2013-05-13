package core;

import java.util.ArrayList;
import java.util.Arrays;

//Solution类定义了可能的解（即遍历顺序），包括游戏进行过程中不完整的解
public class Solution {
	public Problem problem; //对应的TSP
	public ArrayList<Integer> order; //遍历顺序
	public double totalCost; //总成本，在TSP中是总距离
	
	//构造空白解
	public Solution(Problem problem) {
		this.problem = problem;
		order = new ArrayList<Integer>(this.problem.num + 1); //起点在序列首尾各出现一次，因此+1
		totalCost = 0;
	}
	
	//构造现有的解
	public Solution(Problem problem, int[] order) {
		this.problem = problem;
		this.order = new ArrayList<Integer>(this.problem.num + 1);
		for(int i = 0; i < this.problem.num; i++) {
			this.order.add(order[i]);
		}
		refreshCost();
	}
	
	//计算总成本
	private double getCost() {
		double cost = 0;
		for(int i = 0; i < order.size() - 1; i++) {
			cost += problem.distance[order.get(i)][order.get(i+1)];
		}
		return cost;
	}
	
	private void refreshCost() {
		totalCost = getCost();
	}
	
	//向当前解中添加一个点
	public boolean add(int pointIndex) {
		//检查数据合法性
		if(pointIndex >= problem.num || pointIndex < 0) {
			return false;
		}
		//判断是不是最后回到起点的一步，不是的话则检查是否重复经过某个点
		if(!(order.size() == problem.num && pointIndex == order.get(0))) {
			for(Integer x: order) {
				if(x == pointIndex) {
					return false;
				}
			}
		}
		order.add(pointIndex);
		refreshCost();
		return true;
	}
	
	//从当前解中撤销一个点
	public boolean remove() {
		if(order.size() == 0) return false;
		else {
			order.remove(order.size() - 1);
			refreshCost();
			return true;
		}
	}
}
