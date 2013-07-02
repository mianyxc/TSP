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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.VRPPoint;
import core.VRPGame;

public class GraphicPanel extends JPanel{
	VRPGame vrp;
	Graphics2D gg;
	boolean showOptimal, showDemand;
	MainFrame mainFrame;
	
	public GraphicPanel(VRPGame vrp, MainFrame mainFrame) {
		showOptimal = false;
		showDemand = true;
		this.vrp = vrp;
		this.mainFrame = mainFrame;
		setPreferredSize(new Dimension(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
		setBackground(Color.WHITE);
		repaint();
	}
	
	public GraphicPanel(MainFrame mainFrame) {
		this(null, mainFrame);
	}
	
	public void setVRP(VRPGame vrp) {
		this.vrp = vrp;
		repaint();
	}
	
	public void toggleShowOptimal() {
		showOptimal = !showOptimal;
		repaint();
	}
	
	public void toggleShowDemand() {
		showDemand = !showDemand;
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		gg = (Graphics2D) g;
		
		
		if(vrp != null){
			if(showOptimal) {
				for(int i = 0; i < vrp.info.num + 2; i++) {
					for(int j = 0; j < vrp.info.num + 2; j++) {
						if(vrp.optimalSolution.x[i][j]) {
							drawLine(vrp.info.points[i], vrp.info.points[j], Settings.OPTIMALROUTECOLOR, Settings.OPTIMALSTROKEWIDTH);
						}
					}
				}
			}
			for(int i = 0; i < vrp.info.num + 2; i++) {
				for(int j = 0; j < vrp.info.num + 2; j++) {
					if(vrp.playerSolution.x[i][j]) {
						drawLine(vrp.info.points[i], vrp.info.points[j], Settings.ROUTECOLOR, Settings.STROKEWIDTH);
					}
				}
			}
			if(!vrp.playerSolution.addOrder.isEmpty()) {
				drawCircle(vrp.info.points[vrp.playerSolution.addOrder.get(vrp.playerSolution.addOrder.size()-1).j], mainFrame.slider.getValue(), Settings.CIRCLECOLOR);
			}
			for(int i = 1; i < vrp.info.num + 1; i++) {
				drawPoint(vrp.info.points[i], Settings.POINTRADIUS, Color.BLACK);
				if(showDemand) {
					String demand = String.valueOf(vrp.info.points[i].demand);
					drawLabel(vrp.info.points[i], demand, Color.BLACK);
				}
			}
			drawPoint(vrp.info.points[0], Settings.POINTRADIUS, Color.RED);
		}
	}
	
	private void drawLabel(VRPPoint p, String demand, Color black) {
		gg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gg.drawString(demand, p.x + Settings.XBIASE, p.y + Settings.YBIASE);
	}

	private void drawSquare(VRPPoint p, int a, Color color) {
		Rectangle2D.Double rect = new Rectangle2D.Double(p.x - a/2, p.y - a/2, a, a);
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		gg.setStroke(new BasicStroke(Settings.STROKEWIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setColor(color);
		gg.fill(rect);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	private void drawCircle(VRPPoint p, int r, Color color) {
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		//gg.setStroke(new BasicStroke(Settings.STROKEWIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setColor(color);
		gg.drawOval(p.x - r, p.y - r, 2 * r, 2 * r);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	private void drawLine(VRPPoint original, VRPPoint destination, Color color, int width) {
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		gg.setStroke(new BasicStroke(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape line = new Line2D.Double(original.x, original.y, destination.x, destination.y);
		gg.setColor(color);
		gg.draw(line);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	private void drawPoint(VRPPoint p, int r, Color color) {
		Stroke originalStroke = gg.getStroke();
		RenderingHints orignalRenderingHints = gg.getRenderingHints();
		gg.setStroke(new BasicStroke(Settings.STROKEWIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setColor(color);
		gg.fillOval(p.x - r, p.y - r, 2 * r, 2 * r);
		gg.setStroke(originalStroke);
		gg.setRenderingHints(orignalRenderingHints);
	}
	
	
}
