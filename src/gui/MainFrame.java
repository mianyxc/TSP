package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import solver.CplexSolver;

import core.Point;
import core.TSP;

//程序主界面
public class MainFrame {
	JFrame frame;
	JPanel topPanel, leftPanel, rightPanel, infoPanel, numControl;
	GraphicPanel gamePanel;
	JComboBox<Integer> chooseNum;
	JButton generate, showOptimal, rollback, clear;
	JLabel optimalCost, playerCost, difference;
	public JSlider slider;
	
	TSP tsp;
	
	public MainFrame() {
		frame = new JFrame("TSP");
		frame.setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
		frame.setTitle("VRP Game");
		frame.setLayout(null);
		frame.setSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		topPanel = (JPanel) frame.getContentPane();

		JLabel titleLabel = new JLabel("车 辆 路 径 问 题");
		titleLabel.setBounds(Settings.TITLE_X, Settings.TITLE_Y, Settings.TITLE_WIDTH, Settings.TITLE_HEIGHT);
		titleLabel.setFont(Settings.TITLEFONT);
		topPanel.add(titleLabel);
		JLabel titleLabel2 = new JLabel("Vehicle Routing Problem");
		titleLabel2.setBounds(Settings.TITLE2_X, Settings.TITLE2_Y, Settings.TITLE2_WIDTH, Settings.TITLE2_HEIGHT);
		titleLabel2.setFont(Settings.TITLEFONT2);
		topPanel.add(titleLabel2);
		
		leftPanel = new JPanel();
		leftPanel.setSize(new Dimension(Settings.LEFT_WIDTH, Settings.LEFT_HEIGHT));
		leftPanel.setBounds(Settings.LEFT_X, Settings.LEFT_Y, Settings.LEFT_WIDTH, Settings.LEFT_HEIGHT);
		//leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		//leftPanel.setBackground(Color.WHITE);
		leftPanel.setVisible(true);
		topPanel.add(leftPanel);
		
		rightPanel = new JPanel();
		rightPanel.setSize(new Dimension(Settings.RIGHT_WIDTH, Settings.RIGHT_HEIGHT));
		rightPanel.setBounds(Settings.RIGHT_X, Settings.RIGHT_Y, Settings.RIGHT_WIDTH, Settings.RIGHT_HEIGHT);
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setVisible(true);
		topPanel.add(rightPanel);
		
		//tsp = new TSP(25, 0, (double)Settings.GAME_WIDTH, 0, (double)Settings.GAME_HEIGHT);
		gamePanel = new GraphicPanel(this);
		rightPanel.add(gamePanel);
		
		chooseNum = new JComboBox<Integer>(Settings.alternativeNums);
		chooseNum.setPreferredSize(new Dimension(Settings.COMBOBOX_WIDTH, Settings.COMBOBOX_HEIGHT));
		generate = new JButton("生成问题");
		numControl = new JPanel();
		numControl.setPreferredSize(new Dimension(Settings.LEFT_WIDTH, Settings.CONTROLPANEL_HEIGHT));
		numControl.add(new JLabel("选择问题规模"));
		numControl.add(chooseNum);
		numControl.add(generate);
		leftPanel.add(numControl);
		for(Component c: numControl.getComponents()) {
			c.setFont(Settings.font);
		}
		
		
		generate.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				tsp = new TSP((int) chooseNum.getSelectedItem(), Settings.EDGE, Settings.GAME_WIDTH - Settings.EDGE, Settings.EDGE, Settings.GAME_HEIGHT - Settings.EDGE);
				gamePanel.setTSP(tsp);
				refreshInfo();
			}
		});
		
		showOptimal = new JButton("显示/隐藏最优路径");
		//showOptimal.setFont(Settings.font);
		showOptimal.setPreferredSize(new Dimension(Settings.BUTTON_WIDTH*2+5, Settings.BUTTON_HEIGHT));
		leftPanel.add(showOptimal);
		showOptimal.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				gamePanel.toggleShowOptimal();
			}
		});
		
		rollback = new JButton("撤销一步");
		rollback.setPreferredSize(new Dimension(Settings.BUTTON_WIDTH, Settings.BUTTON_HEIGHT));
		leftPanel.add(rollback);
		rollback.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				tsp.remove();
				refreshInfo();
				gamePanel.repaint();
			}
		});
		
		clear = new JButton("全部清除");
		clear.setPreferredSize(new Dimension(Settings.BUTTON_WIDTH, Settings.BUTTON_HEIGHT));
		leftPanel.add(clear);
		clear.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				tsp.removeAll();
				refreshInfo();
				gamePanel.repaint();
			}
		});
		
		slider = new JSlider(Settings.SLIDERMIN, Settings.SLIDERMAX, Settings.SLIDERMIN);
		leftPanel.add(slider);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				gamePanel.repaint();
			}
		});
		
		infoPanel = new JPanel();
		//infoPanel.setPreferredSize(new Dimension(Settings.INFO_WIDTH, Settings.INFO_HEIGHT));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		//infoPanel.setBackground(Color.CYAN);
		infoPanel.setVisible(true);
		leftPanel.add(infoPanel);
		optimalCost = new JLabel();
		playerCost = new JLabel();
		difference = new JLabel();
		infoPanel.add(optimalCost);
		infoPanel.add(playerCost);
		infoPanel.add(difference);
		for(Component c: infoPanel.getComponents()) {
			c.setFont(Settings.font);
		}
		
		gamePanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//System.out.println("Mouse clicked.");
				if(e.getButton() == MouseEvent.BUTTON1) {
					double x = e.getX();
					double y = e.getY();
					tsp.add(findPoint(x, y));
				}
				if(e.getButton() == MouseEvent.BUTTON3) {
					tsp.remove();
				}
				refreshInfo();
				gamePanel.repaint();
			}
		});
		gamePanel.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				slider.setValue(slider.getValue() - e.getUnitsToScroll() * Settings.RADIUSINCREASEMENT);
				gamePanel.repaint();
			}
		});
		
		
		tsp = new TSP((int) chooseNum.getSelectedItem(), Settings.EDGE, Settings.GAME_WIDTH - Settings.EDGE, Settings.EDGE, Settings.GAME_HEIGHT - Settings.EDGE);
		gamePanel.setTSP(tsp);
		refreshInfo();
		
		for(Component c: leftPanel.getComponents()) {
			c.setFont(Settings.font);
		}
		
		frame.setVisible(true);
	}
	
	private int findPoint(double x, double y) {
		double min = Settings.IDENTIFYRADIUS;
		int minIndex = -1;
		Point[] points = tsp.problem.points;
		for(int i = 0; i < tsp.problem.num; i++) {
			Point p = points[i];
			double distance = Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
			if(distance < min) {
				min = distance;
				minIndex = i;
			}
		}
		//System.out.println(minIndex);
		return minIndex;
	}
	
	public void refreshInfo() {
		optimalCost.setText("最优路径长度：" + format(tsp.optimalSolution.totalCost, 2));
		playerCost.setText("当前路径长度：" + format(tsp.playerSolution.totalCost, 2));
		difference.setText("百分比：" + format(tsp.playerSolution.totalCost / tsp.optimalSolution.totalCost * 100, 2) + "%");
	}
	
	public static void main(String[] args) {
		new MainFrame();
		//System.out.println("OK");
	}
	
	private double format(double x, int n) {
		return Math.round(x * Math.pow(10,n))/Math.pow(10, n);
	}

}
