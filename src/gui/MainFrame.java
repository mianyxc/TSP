package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.VRPPoint;
import core.VRPGame;

//程序主界面
public class MainFrame {
	JFrame frame;
	JPanel topPanel, leftPanel, infoPanel, numControl, generatePanel;
	GraphicPanel gamePanel;
	JComboBox<Integer> chooseNum, chooseVehicle;
	JButton generate, showOptimal, showDemand, rollback, clear;
	JLabel optimalCost, playerCost, difference, capacity, vehicle, logo1;
	public JSlider slider;
	public int vehicleUsed;
	public int currentCapacity;
	public int currentIndex;
	
	VRPGame vrp;
	
	public MainFrame() {
		frame = new JFrame("VRP");
		frame.setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
		frame.setTitle("VRP Game");
		frame.setLayout(null);
		frame.setSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		topPanel = (JPanel) frame.getContentPane();
		
		topPanel.setBackground(Color.WHITE);

		JLabel titleLabel = new JLabel("车 辆 路 径 问 题");
		titleLabel.setBounds(Settings.TITLE_X, Settings.TITLE_Y, Settings.TITLE_WIDTH, Settings.TITLE_HEIGHT);
		titleLabel.setFont(Settings.TITLEFONT);
		topPanel.add(titleLabel);
		JLabel titleLabel2 = new JLabel("Vehicle Routing Problem");
		titleLabel2.setBounds(Settings.TITLE2_X, Settings.TITLE2_Y, Settings.TITLE2_WIDTH, Settings.TITLE2_HEIGHT);
		titleLabel2.setFont(Settings.TITLEFONT2);
		topPanel.add(titleLabel2);
		
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setSize(new Dimension(Settings.LEFT_WIDTH, Settings.LEFT_HEIGHT));
		leftPanel.setBounds(Settings.LEFT_X, Settings.LEFT_Y, Settings.LEFT_WIDTH, Settings.LEFT_HEIGHT);
		leftPanel.setVisible(true);
		leftPanel.setBorder(BorderFactory.createEtchedBorder());
		topPanel.add(leftPanel);
		gamePanel = new GraphicPanel(this);
		gamePanel.setBorder(BorderFactory.createEtchedBorder());
		gamePanel.setBounds(Settings.RIGHT_X, Settings.RIGHT_Y, Settings.RIGHT_WIDTH, Settings.RIGHT_HEIGHT);
		topPanel.add(gamePanel);
		
		chooseNum = new JComboBox<Integer>(Settings.alternativeNums);
		chooseNum.setPreferredSize(new Dimension(Settings.COMBOBOX_WIDTH, Settings.COMBOBOX_HEIGHT));
		chooseVehicle = new JComboBox<Integer>(Settings.alternativeVehicles);
		chooseVehicle.setPreferredSize(new Dimension(Settings.COMBOBOX_WIDTH, Settings.COMBOBOX_HEIGHT));
		generate = new JButton("<html>生成<br />问题</html>");
		generate.setFont(Settings.font);
		generate.setPreferredSize(new Dimension(62, 47));
		numControl = new JPanel();
		numControl.setPreferredSize(new Dimension(Settings.LEFT_WIDTH/2, Settings.CONTROLPANEL_HEIGHT));
		numControl.add(new JLabel("选择客户总数"));
		numControl.add(chooseNum);
		numControl.add(new JLabel("选择车辆总数"));
		numControl.add(chooseVehicle);
		generatePanel = new JPanel();
		generatePanel.add(numControl);
		generatePanel.add(generate);
		leftPanel.add(generatePanel);
		for(Component c: numControl.getComponents()) {
			c.setFont(Settings.font);
			c.setBackground(Color.WHITE);
			c.setFocusable(false);
		}
		for(Component c: generatePanel.getComponents()) {
			c.setBackground(Color.WHITE);
			c.setFocusable(false);
		}
		
		
		generate.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				vrp = new VRPGame((int) chooseNum.getSelectedItem(),(int) chooseVehicle.getSelectedItem(), Settings.EDGE, Settings.GAME_WIDTH - Settings.EDGE, Settings.EDGE, Settings.GAME_HEIGHT - Settings.EDGE, Settings.DEMAND_MIN, Settings.DEMAND_MAX);
				gamePanel.setVRP(vrp);
				currentIndex = 0;
				vehicleUsed = 0;
				currentCapacity = vrp.info.capacity;
				refreshInfo();
			}
		});
		
		showOptimal = new JButton("显示/隐藏最优路径");
		showOptimal.setPreferredSize(new Dimension(Settings.BUTTON_WIDTH*2+5, Settings.BUTTON_HEIGHT));
		leftPanel.add(showOptimal);
		showOptimal.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				gamePanel.toggleShowOptimal();
			}
		});
		
		showDemand = new JButton("显示/隐藏客户需求");
		showDemand.setPreferredSize(new Dimension(Settings.BUTTON_WIDTH*2+5, Settings.BUTTON_HEIGHT));
		leftPanel.add(showDemand);
		showDemand.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				gamePanel.toggleShowDemand();
			}
		});
		
		rollback = new JButton("撤销一步");
		rollback.setPreferredSize(new Dimension(Settings.BUTTON_WIDTH, Settings.BUTTON_HEIGHT));
		leftPanel.add(rollback);
		rollback.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int backIndex = vrp.remove();
				if(backIndex != -1) {
					currentCapacity += vrp.info.points[currentIndex].demand;
					if(currentIndex == 0) {
						currentCapacity = getCapacity();
					}
					currentIndex = backIndex;
					if(backIndex == 0) {
						currentCapacity = vrp.info.capacity;
						vehicleUsed--;
					}
				}
				refreshInfo();
				gamePanel.repaint();
			}
		});
		
		clear = new JButton("全部清除");
		clear.setPreferredSize(new Dimension(Settings.BUTTON_WIDTH, Settings.BUTTON_HEIGHT));
		leftPanel.add(clear);
		clear.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				vrp.removeAll();
				currentIndex = 0;
				currentCapacity = vrp.info.capacity;
				vehicleUsed = 0;
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
		
		JPanel tempPanel = new JPanel();
		tempPanel.setPreferredSize(new Dimension(Settings.LEFT_WIDTH-10, 50));
		leftPanel.add(tempPanel);
		/*
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/logo2.png"));
		icon.setImage(icon.getImage().getScaledInstance(Settings.LOGOWIDTH_1,Settings.LOGOWIDTH_1*icon.getIconHeight()/icon.getIconWidth(),Image.SCALE_DEFAULT));
		logo1 = new JLabel();
		logo1.setIcon(icon);
		leftPanel.add(logo1);
		*/
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setVisible(true);
		leftPanel.add(infoPanel);
		capacity = new JLabel();
		vehicle = new JLabel();
		optimalCost = new JLabel();
		playerCost = new JLabel();
		difference = new JLabel();
		infoPanel.add(capacity);
		infoPanel.add(vehicle);
		infoPanel.add(optimalCost);
		infoPanel.add(playerCost);
		infoPanel.add(difference);
		for(Component c: infoPanel.getComponents()) {
			c.setFont(Settings.font);
		}
		
		JPanel tempPanel2 = new JPanel();
		tempPanel2.setPreferredSize(new Dimension(Settings.LEFT_WIDTH-10, 100));
		leftPanel.add(tempPanel2);
		
		ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/logo1.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(Settings.LOGOWIDTH_2,Settings.LOGOWIDTH_2*icon2.getIconHeight()/icon2.getIconWidth(),Image.SCALE_DEFAULT));
		JLabel logo2 = new JLabel();
		logo2.setIcon(icon2);
		leftPanel.add(logo2);
		
		
		gamePanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					double x = e.getX();
					double y = e.getY();
					int clickedIndex = findIndex(x,y);
					if(clickedIndex != -1 && clickedIndex != currentIndex) {
						if(currentCapacity >= vrp.info.points[clickedIndex].demand || vrp.info.points[clickedIndex].isSatisfied) {
							boolean flag = vrp.info.points[clickedIndex].isSatisfied;
							if(vrp.add(currentIndex, clickedIndex)) {
								if(currentIndex == 0) {
									vehicleUsed++;
								}
								if(!flag) {
									currentCapacity -= vrp.info.points[clickedIndex].demand;
								}
								currentIndex = clickedIndex;
								if(currentIndex == 0) {
									currentCapacity = vrp.info.capacity;
								}
							}
						}
					}
				}
				if(e.getButton() == MouseEvent.BUTTON3) {
					int backIndex = vrp.remove();
					if(backIndex != -1) {
						currentCapacity += vrp.info.points[currentIndex].demand;
						if(currentIndex == 0) {
							currentCapacity = getCapacity();
						}
						currentIndex = backIndex;
						if(backIndex == 0) {
							currentCapacity = vrp.info.capacity;
							vehicleUsed--;
						}
					}
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
		
		for(Component c: leftPanel.getComponents()) {
			c.setBackground(Color.WHITE);
		}
		

		
		
		vrp = new VRPGame((int) chooseNum.getSelectedItem(),(int) chooseVehicle.getSelectedItem(), Settings.EDGE, Settings.GAME_WIDTH - Settings.EDGE, Settings.EDGE, Settings.GAME_HEIGHT - Settings.EDGE, Settings.DEMAND_MIN, Settings.DEMAND_MAX);
		gamePanel.setVRP(vrp);
		currentIndex = 0;
		vehicleUsed = 0;
		currentCapacity = vrp.info.capacity;
		refreshInfo();
		
		for(Component c: leftPanel.getComponents()) {
			c.setFont(Settings.font);
			c.setFocusable(false);
		}
		
		frame.setVisible(true);
	}
	
	private int getCapacity() {
		int c = vrp.info.capacity;
		int i = vrp.playerSolution.addOrder.size();
		do {
			i--;
			c -= vrp.info.points[vrp.playerSolution.addOrder.get(i).j].demand;
		} while(vrp.playerSolution.addOrder.get(i).i != 0);
		return c;
	}

	private int findIndex(double x, double y) {
		double min = Settings.IDENTIFYRADIUS;
		int minIndex = -1;
		VRPPoint[] points = vrp.info.points;
		for(int i = 0; i < vrp.info.num + 1; i++) {
			VRPPoint p = points[i];
			double distance = Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
			if(distance < min) {
				min = distance;
				minIndex = i;
			}
		}
		if(min == Settings.IDENTIFYRADIUS) {
			return -1;
		} else {
			return minIndex;
		}
	}
	
	public void refreshInfo() {
		capacity.setText("剩余容量：" + currentCapacity);
		vehicle.setText("已使用车辆数：" + vehicleUsed);
		optimalCost.setText("最优路径长度：" + format(vrp.optimalSolution.totalCost, 2));
		playerCost.setText("当前路径长度：" + format(vrp.playerSolution.totalCost, 2));
		difference.setText("百分比：" + format(vrp.playerSolution.totalCost / vrp.optimalSolution.totalCost * 100, 2) + "%");
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
	private double format(double x, int n) {
		return Math.round(x * Math.pow(10,n))/Math.pow(10, n);
	}

}
