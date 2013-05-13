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

import core.TSP;

//程序主界面
public class MainFrame {
	JFrame frame;
	JPanel topPanel, leftPanel, rightPanel, infoPanel, numControl;
	GraphicPanel gamePanel;
	JComboBox<Integer> chooseNum;
	JButton generate;
	
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
		//infoPanel.setBackground(Color.CYAN);
		infoPanel.setVisible(true);
		rightPanel.add(infoPanel);
		
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
			}
		});
		
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		//System.out.println(mainFrame.tsp.optimalSolution.totalCost);
	}
}
