package core;

//Problem�ඨ����һ��TSP����Ҫ����Ϣ�����㼯�;������
public class Problem {
	public int num;
	public Point[] points;
	public double[][] distance;
	
	//���캯�������ݵ����Ŀ��������ϵ�еķ�Χ���������TSP
	public Problem(int num, double xl, double xu, double yl, double yu) {
		this.num = num;
		generatePoints(xl, xu, yl, yu);
		getDistanceMatrix();
	}
	
	//������ɵ㼯
	private void generatePoints(double xl, double xu, double yl, double yu) {
		points = new Point[num];
		for(int i = 0; i < num; i++) {
			points[i] = new Point(xl, xu, yl, yu);
		}
	}
	
	//����㼯��ÿ����֮��ľ��벢����������
	private void getDistanceMatrix() {
		distance = new double[num][num];
		for(int i = 0; i < num; i++) {
			for(int j = 0; j < num; j++) {
				distance[i][j] = getDistance(points[i], points[j]);
			}
		}
	}
	
	//���ظ�������֮��ľ���
	private double getDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
}
