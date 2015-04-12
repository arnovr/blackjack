package blackjack;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.*;

import javax.swing.*;

public class GraphingGameData extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4772994986563496392L;
	
	private Graphics2D g2;
	private FontRenderContext frc;
	private Font font;
    
	private String bottomLabel = "";
	
	private int ysteps;
	private int ylabellength = 30;
    private int[] y = {};
    
    // Padding in the panel
    final int padding = 20;
    private int rowLineWidth = 4;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
	        g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	
	        int w = getWidth();
	        int h = getHeight();
	        
	        // Verticale lijn
	        g2.draw(new Line2D.Double(padding, padding, padding, h-padding));
	        
	        // Horizontale lijn.
	        g2.draw(new Line2D.Double(padding, h-padding, w-padding, h-padding));
	        
	        // Draw labels.
	        font = g2.getFont();
	        frc = g2.getFontRenderContext();
	        
	        if ( y.length > 0 ) {
		        this.drawVerticalLabel();
		        this.drawCenteredHorizontalLabel(this.bottomLabel);
		        this.drawLines(y);
	        }

    }
    private void drawLines(int[] y) {
    	float minY = getHeight() - padding;
    	float maxY = padding;
    	float yStep = (minY - maxY) / (ylabellength);
    	float xStep = (getWidth() - padding) / y.length;

    	
        g2.setPaint(Color.green.darker());

        for(int i = 0; i < y.length - 1; i++) {
    		float y1 =  minY - (yStep * y[i]);
    		float y2 =  minY - (yStep * y[i + 1]);

            double x1 = padding + i*xStep;          
            double x2 = padding + (i+1)*xStep;
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    /**
     * Draws vertical labels
     */
    private void drawVerticalLabel() {
    	// Calculate half of font height
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() / 2;

    	float minY = getHeight() - padding;
    	float maxY = padding;
    	float diff = (minY - maxY) / (ylabellength);

    	for(int i = 0; i <= ylabellength; i = i + ysteps ) {
    		float yrow =  minY - (diff * i);
            g2.setPaint(Color.black);
    		g2.drawString("" + i, 2, yrow + sh - 1);
  
    		// Draw small line
    		g2.drawLine(padding - (rowLineWidth / 2), Math.round(yrow), padding + (rowLineWidth / 2), Math.round(yrow));

    		// Skip 0 point
    		if ( i > 0 ) {
    			g2.setPaint(Color.lightGray);
    			g2.drawLine(padding + (rowLineWidth / 2), Math.round(yrow), getWidth() - padding, Math.round(yrow));
    		}
    	}
    }
    
    public void setY(int[] y, int numlabels, int steps) {
    	this.y = y;
    	this.ysteps = steps;
    	this.ylabellength = numlabels;
    	this.repaint();
    }
    
    /**
     * Create a centered label
     * @param label String
     */
    private void drawCenteredHorizontalLabel(String label) {
        g2.setPaint(Color.black);
        float stringY = ( getHeight() - padding ) + (float)font.getStringBounds(label, frc).getHeight();
        float stringWidth = (float)font.getStringBounds(label, frc).getWidth();
        float sx = (getWidth() - stringWidth)/2;
        g2.drawString(label, sx, stringY);
    }
}