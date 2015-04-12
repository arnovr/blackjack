package blackjack;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.JPanel;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class GraphicalHandPanel extends JPanel {
	
	public enum HandInfoFormat {
		NONE,
		BUSTED_BLACKJACK,
		ALL,
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1855804357090288659L;
	/**
	 * De hand die dit control weergeeft.
	 */
	private Hand hand;
	
	/**
	 * De informatie die dit control van de hand moet weergeven.
	 */
	private HandInfoFormat handInfoFormat = HandInfoFormat.NONE;
	/**
	 * 2e kaart wel of niet weergeven.
	 */
	private boolean showSecondCard = false;
	/**
	 * Geeft aan of deze hand solid moet worden getekend.
	 */
	private boolean solid = true;

	public GraphicalHandPanel()
	{
	}
	/**
	 * Schoont deze hand op (er wordt niets meer getekend.
	 */
	public void clear()
	{
		this.hand = null;

		repaint();
	}
	/**
	 * Stel deze hand in.
	 * @param hand De hand die getoond moet worden.
	 * @param showSecondCard Indiceert of voor deze hand de 2e kaart moet worden getoond.
	 */
	public void setHand(Hand hand, boolean showSecondCard)
	{
		this.hand = hand;
		this.showSecondCard = showSecondCard;
						
		repaint();
	}
	/**
	 * Geeft de hand terug van dit control.
	 * @return Hand
	 */
	public Hand getHand()
	{
		return hand;
	}
	/**
	 * Stelt in of dit control solid moet worden getekend.
	 * @param solid Indien true dan solid tekenen.
	 */
	public void setSolid(boolean solid)
	{
		this.solid = solid;
		
		repaint();
	}
	/**
	 * Hand informatie formaat zetten voor deze hand
	 * @param handInfoFormat Gewenste informatie formaat
	 */
	public void setHandInfoFormat(HandInfoFormat handInfoFormat)
	{
		this.handInfoFormat = handInfoFormat;
	}
	/**
	 * Tekent dit control.
	 * @param g De graphics interface waarnaartoe getekend kan worden.
	 */
	public void paintComponent (Graphics g) 
	{
		super.paintComponent (g);
		
		if (hand == null) return;
		
		int index = 0;
		int y = 0;
		
		BufferedImage image = null;
		
		for (Card card : hand.getCards()) 
		{
			BufferedImage img = null;
			try 
			{
				String fileName;
				if (showSecondCard == false && hand.getCards().size() == 2 && index == 1) 
				{
					fileName = "src\\images\\blue_deck_portrait.png";
					y = 0;
				} 
				else 
				{
					fileName = "src\\images\\" + card.getImageLocation();
					y = 0;
				}
			    img = ImageIO.read(new File(fileName));
			}
			catch (IOException e) 
			{
				System.out.println(e.toString());
			}

			if (img != null) 
			{
				//Draw image centered in the middle of the panel
				if (image == null) 
				{
					if (handInfoFormat != HandInfoFormat.NONE && hand.isBust())
					{
						image = new BufferedImage(img.getWidth() + (hand.getCards().size() - 1) * 10, img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
					}
					else
					{
						image = (BufferedImage) createImage(img.getWidth() + (hand.getCards().size() - 1) * 10, img.getHeight());
					}
				}
				Graphics2D g2 = image.createGraphics();
				g2.drawImage (img, index * 10,  0, null);
			}
			index += 1;
		}
		if (solid == false)
		{
			((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.2f));
		}
		if (hand != null && hand.hasBet() != false && hand.getBet() > 0) 
		{
			String bet = "Inzet: $" + hand.getBet();
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.drawChars(bet.toCharArray(), 0, bet.length(), 0, 24);
		}
		g.drawImage (image, 5,  y + 26, this);
		Color color = null;
		String statusText = "";
		if (this.handInfoFormat != HandInfoFormat.NONE && hand.isBust() != false)
		{
			statusText = "Busted";
			color = new Color(200,0,0);
		}
		else if (this.handInfoFormat != HandInfoFormat.NONE && hand.isBlackjack() != false)
		{
			if ((showSecondCard == false && hand.getCards().size() == 2) == false)
			{
				statusText = "Blackjack";
				color = new Color(0,0,0);
			}
		}
		if (this.handInfoFormat == HandInfoFormat.ALL && hand.getWon() != false)
		{
			statusText = "Won";
			color = new Color(0,233,0);		
		}
		else if (this.handInfoFormat == HandInfoFormat.ALL && hand.getEven() != false)
		{
			statusText = "Even";
			color = new Color(150,150,150);		
		}
		else if (this.handInfoFormat == HandInfoFormat.ALL && hand.getLost() != false)
		{
			statusText = "Lost";
			color = new Color(240,0,0);		
		}
		if (statusText != "" && color != null) 
		{
//			g.setFont(new Font("Algerian", Font.BOLD, 24));
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.setColor(color);
			g.drawChars(statusText.toCharArray(), 0, statusText.length(), 18, getHeight() / 2 + 20);
		}
	}
}
