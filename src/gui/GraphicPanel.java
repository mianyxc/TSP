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

import javax.swing.JPanel;

import core.Point;
import core.TSP;

public class GraphicPanel extends JPanel{
	TSP tsp;
	Graphics2D gg;
	boolean showOptimal;
	
	public GraphicPanel(TSP tsp) {
		showOptimal = false;
		this.tsp = tsp;
		setPreferredSize(new Dimension(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
		setBackground(Color.WHITE);
		repaint();
	}
	
	public GraphicPanel() {
		this(null);
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
				drawPoint(p);
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
		}
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
	
	private void drawPoint(Point p) {
		//gg.fill((Shape) new Rectangle2D.Double(p.x - Settings.POINTRADIUS, p.y - Settings.POINTRADIUS, 2 * Settings.POINTRADIUS, 2 * Settings.POINTRADIUS));
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		gg.setStroke(new BasicStroke(Settings.STROKEWIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.fillOval(p.x - Settings.POINTRADIUS, p.y - Settings.POINTRADIUS, 2 * Settings.POINTRADIUS, 2 * Settings.POINTRADIUS);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	
}
