package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.Point;
import core.TSP;

public class GraphicPanel extends JPanel{
	TSP tsp;
	Graphics2D gg;
	boolean showOptimal;
	MainFrame mainFrame;
	
	public GraphicPanel(TSP tsp, MainFrame mainFrame) {
		showOptimal = false;
		this.tsp = tsp;
		this.mainFrame = mainFrame;
		setPreferredSize(new Dimension(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
		setBackground(Color.WHITE);
		repaint();
	}
	
	public GraphicPanel(MainFrame mainFrame) {
		this(null, mainFrame);
	}
	
	public void setTSP(TSP tsp) {
		this.tsp = tsp;
		repaint();
	}
	
	public void toggleShowOptimal() {
		showOptimal = !showOptimal;
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		gg = (Graphics2D) g;
		
		
		if(tsp != null){
			for(Point p: tsp.problem.points) {
				drawPoint(p, Color.BLACK);
			}
			if(showOptimal) {
				System.out.println("Show optimal solution.");
				ArrayList<Integer> optimalOrder = tsp.optimalSolution.order;
				for(int i = 0; i < optimalOrder.size() - 1; i++) {
					Point original = tsp.problem.points[optimalOrder.get(i)];
					Point destination = tsp.problem.points[optimalOrder.get(i + 1)];
					drawLine(original, destination, Settings.OPTIMALROUTECOLOR);
				}
			}
			ArrayList<Integer> playerOrder = tsp.playerSolution.order;
			for(int i = 0; i < playerOrder.size() - 1; i++) {
				Point original = tsp.problem.points[playerOrder.get(i)];
				Point destination = tsp.problem.points[playerOrder.get(i + 1)];
				drawLine(original, destination, Color.BLACK);
			}
			if(!playerOrder.isEmpty()) {
				drawCircle(tsp.problem.points[playerOrder.get(playerOrder.size() - 1)], mainFrame.slider.getValue(), Settings.CIRCLECOLOR);
			}
		}
	}
	
	private void drawCircle(Point p, int r, Color color) {
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		//gg.setStroke(new BasicStroke(Settings.STROKEWIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setColor(color);
		gg.drawOval(p.x - r, p.y - r, 2 * r, 2 * r);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	private void drawLine(Point original, Point destination, Color color) {
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		gg.setStroke(new BasicStroke(Settings.STROKEWIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape line = new Line2D.Double(original.x, original.y, destination.x, destination.y);
		gg.setColor(color);
		gg.draw(line);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	private void drawPoint(Point p, Color color) {
		//gg.fill((Shape) new Rectangle2D.Double(p.x - Settings.POINTRADIUS, p.y - Settings.POINTRADIUS, 2 * Settings.POINTRADIUS, 2 * Settings.POINTRADIUS));
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		gg.setStroke(new BasicStroke(Settings.STROKEWIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setColor(color);
		gg.fillOval(p.x - Settings.POINTRADIUS, p.y - Settings.POINTRADIUS, 2 * Settings.POINTRADIUS, 2 * Settings.POINTRADIUS);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	
}
