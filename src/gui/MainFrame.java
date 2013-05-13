package gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import solver.CplexSolver;

import core.Point;
import core.TSP;

//程序主界面
public class MainFrame {
	JFrame frame;
	JPanel topPanel, leftPanel, rightPanel, infoPanel, numControl;
	GraphicPanel gamePanel;
	JComboBox<Integer> chooseNum;
	JButton generate, showOptimal;
	JLabel optimalCost, playerCost, difference;
	
	TSP tsp;
	
	public MainFrame() {
		frame = new JFrame("TSP");
		frame.setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
		frame.setLayout(new BoxLayout(frame, BoxLayout.X_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		topPanel = (JPanel) frame.getContentPane();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
		//topPanel.setBackground(Color.BLUE);
		topPanel.setVisible(true);
		
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(Settings.LEFT_WIDTH, Settings.LEFT_HEIGHT));
		//leftPanel.setBackground(Color.WHITE);
		leftPanel.setVisible(true);
		topPanel.add(leftPanel);
		
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(Settings.RIGHT_WIDTH, Settings.RIGHT_HEIGHT));
		//rightPanel.setBackground(Color.WHITE);
		rightPanel.setVisible(true);
		topPanel.add(rightPanel);
		
		//tsp = new TSP(25, 0, (double)Settings.GAME_WIDTH, 0, (double)Settings.GAME_HEIGHT);
		gamePanel = new GraphicPanel();
		rightPanel.add(gamePanel);
		
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(Settings.INFO_WIDTH, Settings.INFO_HEIGHT));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		//infoPanel.setBackground(Color.CYAN);
		infoPanel.setVisible(true);
		rightPanel.add(infoPanel);
		optimalCost = new JLabel();
		playerCost = new JLabel();
		difference = new JLabel();
		infoPanel.add(optimalCost);
		infoPanel.add(playerCost);
		infoPanel.add(difference);
		
		chooseNum = new JComboBox<Integer>(Settings.alternativeNums);
		chooseNum.setPreferredSize(new Dimension(Settings.COMBOBOX_WIDTH, Settings.COMBOBOX_HEIGHT));
		numControl = new JPanel();
		numControl.setPreferredSize(new Dimension(Settings.LEFT_WIDTH, Settings.CONTROLPANEL_HEIGHT));
		numControl.add(new JLabel("选择问题规模"));
		numControl.add(chooseNum);
		leftPanel.add(numControl);
		
		generate = new JButton("生成");
		leftPanel.add(generate);
		generate.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				tsp = new TSP((int) chooseNum.getSelectedItem(), Settings.POINTRADIUS, Settings.GAME_WIDTH - Settings.POINTRADIUS, Settings.POINTRADIUS, Settings.GAME_HEIGHT - Settings.POINTRADIUS);
				gamePanel.setTSP(tsp);
				refreshInfo();
			}
		});
		
		showOptimal = new JButton("显示/隐藏最优路径");
		leftPanel.add(showOptimal);
		showOptimal.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				gamePanel.toggleShowOptimal();
			}
		});
		
		gamePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
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
		return minIndex;
	}
	
	public void refreshInfo() {
		optimalCost.setText("最优解：" + tsp.optimalSolution.totalCost);
		playerCost.setText("当前解：" + tsp.playerSolution.totalCost);
		difference.setText("百分比：" + tsp.playerSolution.totalCost / tsp.optimalSolution.totalCost);
	}
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		//System.out.println(mainFrame.tsp.optimalSolution.totalCost);
	}
}
