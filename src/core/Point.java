package core;

//Point类定义了由横纵坐标组成的坐标点，实现了按要求生成和随机生成坐标点的构造函数
public class Point {
	//定义横纵坐标
	public double x;
	public double y;
	
	//按给定的横纵坐标生成坐标点
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//按给定的范围随机生成坐标点，4个参数分别是横纵坐标的上限和下限
	public Point(double xl, double xu, double yl, double yu) {
		this.x = Math.random() * (xu - xl) + xl;
		this.y = Math.random() * (yu - yl) + yl;
	}
}
