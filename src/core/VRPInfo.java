package core;

import gui.Settings;

//Problem类定义了一个TSP所需要的信息，即点集和距离矩阵
public class VRPInfo {
	public int num; //点的数目
	public VRPPoint[] points; //点集
	public double[][] distance; //距离矩阵
	public double[] demand;
	public int capacity;
	public int vehicleNum;
	
	//构造函数，根据点的数目和在坐标系中的范围，随机生成TSP
	public VRPInfo(int num, int vehicleNum, int xl, int xu, int yl, int yu, int dl, int du) {
		this.num = num;
		this.vehicleNum = vehicleNum;
		generatePoints(xl, xu, yl, yu, dl, du);
		getDistanceMatrix();
		getDemand();
		capacity = getCapacity();
	}
	
	private int getCapacity() {
		int totalDemand = 0;
		for(int i = 0; i < num+2; i++) {
			totalDemand += demand[i];
		}
		int capacity = ((totalDemand/vehicleNum+1)/5+1)*5;
		return capacity;
	}

	private void getDemand() {
		demand = new double[num+2];
		for(int i = 0; i < num+2; i++) {
			demand[i] = points[i].demand;
		}
	}

	//随机生成点集
	private void generatePoints(int xl, int xu, int yl, int yu, int dl, int du) {
		points = new VRPPoint[num+2];
		//如果要求depot在中心，就用注释掉的那一句
		//VRPPoint depot = new VRPPoint(Settings.GAME_WIDTH/2, Settings.GAME_HEIGHT/2);
		VRPPoint depot = new VRPPoint(xl, xu, yl, yu, 0, 0);
		points[0] = depot;
		for(int i = 1; i < num+1; i++) {
			VRPPoint tempPoint;
			do {
				tempPoint = new VRPPoint(xl, xu, yl, yu, dl ,du);
			} while (!checkDistance(i, tempPoint));
			points[i] = tempPoint;
		}
		points[num+1] = depot;
	}
	
	private boolean checkDistance(int i, VRPPoint tempPoint) {
		for(int j = 0; j < i; j++) {
			if(getDistance(points[j], tempPoint) < Settings.MINDISTANCE) {
				return false;
			}
		}
		return true;
	}

	//计算点集中每两点之间的距离并存入距离矩阵
	private void getDistanceMatrix() {
		distance = new double[num+2][num+2];
		for(int i = 0; i < num+2; i++) {
			for(int j = 0; j < num+2; j++) {
				distance[i][j] = getDistance(points[i], points[j]);
			}
		}
	}
	
	//返回给定两点之间的距离
	public double getDistance(VRPPoint p1, VRPPoint p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
}
