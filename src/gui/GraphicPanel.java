package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

import core.Point;
import core.TSP;

public class GraphicPanel extends JPanel implements MouseListener {
	TSP tsp;
	
	public GraphicPanel(TSP tsp) {
		this.tsp = tsp;
		setPreferredSize(new Dimension(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
		repaint();
	}
	
	public GraphicPanel() {
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D gg = (Graphics2D) g;
		
		for(Point p: tsp.problem.points) {
			gg.fill((Shape) new Ellipse2D.Double(p.x, p.y, Settings.POINTRADIUS, Settings.POINTRADIUS));
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
