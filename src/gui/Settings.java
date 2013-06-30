package gui;

import java.awt.Color;
import java.awt.Font;

//Settings类定义了程序中用到的常量
public class Settings {
	//各组件的形状大小
	//窗口总大小
	public static final int FRAME_WIDTH = 840;
	public static final int FRAME_HEIGHT = 650;
	//标题
	public static final int TITLE_X = 300;
	public static final int TITLE_Y = 10;
	public static final int TITLE_WIDTH = 500;
	public static final int TITLE_HEIGHT = 50;
	public static final Font TITLEFONT = new Font("微软雅黑", Font.PLAIN, 25);
	public static final int TITLE2_X = 285;
	public static final int TITLE2_Y = 40;
	public static final int TITLE2_WIDTH = 500;
	public static final int TITLE2_HEIGHT = 50;
	public static final Font TITLEFONT2 = new Font("Helvetica", Font.PLAIN, 20);
	//左面板
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 100;
	public static final int LEFT_WIDTH = 300;
	public static final int LEFT_HEIGHT = 500;
	//右面板
	public static final int RIGHT_X = 300;
	public static final int RIGHT_Y = 100;
	public static final int RIGHT_WIDTH = 500;
	public static final int RIGHT_HEIGHT = 500;
	//游戏显示区
	public static final int GAME_WIDTH = 500;
	public static final int GAME_HEIGHT = 500;
	//数据显示区
	public static final int INFO_WIDTH = 300;
	public static final int INFO_HEIGHT = 200;
	//选择框
	public static final int COMBOBOX_WIDTH = 50;
	public static final int COMBOBOX_HEIGHT = 20;
	public static final int CONTROLPANEL_HEIGHT = 40;
	//按钮
	public static final int BUTTON_HEIGHT = 25;
	public static final int BUTTON_WIDTH = 100;
	
	//可选的问题规模
	public static final Integer[] alternativeNums = {10, 15, 20, 25, 30};
	
	//点的半径
	public static final int POINTRADIUS = 3;
	
	//点之间的最小距离
	public static final int MINDISTANCE = 30;
	
	//边缘留空，防止点显示到屏幕外或显示不全
	public static final int EDGE = 10;
	
	//点击时的判定半径
	public static final double IDENTIFYRADIUS = 50;
	
	//画笔粗细
	public static final int STROKEWIDTH = 3;
	public static final int OPTIMALSTROKEWIDTH = 7;
	
	//玩家路径颜色
	public static final Color ROUTECOLOR = new Color(0,0,0);
	
	//最优路径颜色
	public static final Color OPTIMALROUTECOLOR = new Color(102,204,0);
	
	//字体
	public static final Font font = new Font("微软雅黑", Font.PLAIN, 14);
	
	//滑块取值范围
	public static final int SLIDERMIN = POINTRADIUS;
	public static final int SLIDERMAX = 400;
	
	//圆圈参数
	public static final Color CIRCLECOLOR = Color.BLACK;
	public static final int RADIUSINCREASEMENT = 3;
}
