package core;

//Point类定义了由横纵坐标组成的坐标点，实现了按要求生成和随机生成坐标点的构造函数
public class VRPPoint {
	//定义横纵坐标
	public int x;
	public int y;
	public int demand;
	public boolean isSatisfied;
	
	//按给定的横纵坐标生成坐标点
	public VRPPoint(int x, int y, int demand, boolean isSatisfied) {
		this.x = x;
		this.y = y;
		this.demand = demand;
		this.isSatisfied = isSatisfied;
	}
	
	public VRPPoint(int x, int y) {
		this(x, y, 0, false);
	}
	
	//按给定的范围随机生成坐标点，4个参数分别是横纵坐标的上限和下限
	public VRPPoint(int xl, int xu, int yl, int yu, int dl, int du) {
		this.x = (int)(Math.random() * (xu - xl) + xl);
		this.y = (int)(Math.random() * (yu - yl) + yl);
		this.demand = (int)(Math.random() * (du - dl) + dl);
		this.isSatisfied = false;
	}
}
