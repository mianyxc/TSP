package core;

import gui.Settings;

//Problem类定义了一个TSP所需要的信息，即点集和距离矩阵
public class Problem {
	public int num; //点的数目
	public Point[] points; //点集
	public double[][] distance; //距离矩阵
	
	//构造函数，根据点的数目和在坐标系中的范围，随机生成TSP
	public Problem(int num, double xl, double xu, double yl, double yu) {
		this.num = num;
		generatePoints(xl, xu, yl, yu);
		getDistanceMatrix();
	}
	
	//随机生成点集
	private void generatePoints(double xl, double xu, double yl, double yu) {
		points = new Point[num];
		for(int i = 0; i < num; i++) {
			points[i] = new Point(xl, xu, yl, yu);
		}
	}
	
	//计算点集中每两点之间的距离并存入距离矩阵
	private void getDistanceMatrix() {
		distance = new double[num][num];
		for(int i = 0; i < num; i++) {
			for(int j = 0; j < num; j++) {
				distance[i][j] = getDistance(points[i], points[j]);
			}
		}
	}
	
	//返回给定两点之间的距离
	public double getDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
}
