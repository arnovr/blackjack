package blackjack;

import javax.imageio.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.JPanel;

public class GraphicalCardsCollectionPanel extends JPanel {

	public enum CardsCollectionPosition
	{
		LEFTTOP,
		RIGHTTOP,
		LEFTBOTTOM,
		RIGHTBOTTOM,
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1855804357090288659L;
	
	/**
	 * De startindex voor de shuffle animatie.
	 */
	private int shuffleStart = -1;
	/**
	 * Aantal kaarten die moeten worden verzameld bij de verzamel animatie.
	 */
	private int numberOfCardsToCollect = 2;
	/**
	 * De richting van waaruit de kaarten moeten worden getekend.
	 */
	private CardsCollectionPosition cardsPosition = CardsCollectionPosition.LEFTTOP;
	/**
	 * Indiceert dat deze colletie altijd vol moet zijn.
	 */
	private boolean alwaysFull = false;
	/**
	 * Simulatie lijst voor geroteerde kaarten als de collectie een chaos moet zijn.
	 */
	private int[] positions = { 96, 0, 123, 56, 67, 40, 86, 135 };
	/**
	 * De huidige teken-startindex van de lijst met kaarten.
	 */
	private int currentStartIndex = 0;
		
	public GraphicalCardsCollectionPanel()
	{
	}
	/**
	 * Stelt in dat deze collectie altijd vol moet zijn.
	 * @param alwaysFull
	 */
	public void setAlwaysFull(boolean alwaysFull)
	{
		this.alwaysFull = alwaysFull;
	}
	/**
	 * Stelt in hoeveel kaarten er verzameld moeten worden bij verzamel simulatie. 
	 * @param numberOfCardsToCollect
	 */
	public void setNumberOfCardsToCollect(int numberOfCardsToCollect)
	{
		this.numberOfCardsToCollect = numberOfCardsToCollect;
	}
	/**
	 * Stelt in vanuit welke richting de kaarten moeten worden getekend.
	 * @param cardsPosition
	 */
	public void setCardsPosition(CardsCollectionPosition cardsPosition)
	{
		this.cardsPosition = cardsPosition;
	}
	/**
	 * Volgende positie instellen
	 * @return boolean Wanneer de beginwaarde is bereikt van simulatie: true
	 */
	public boolean nextPosition()
	{
		currentStartIndex++;
		// opnieuw tekenen
		repaint();
		if (currentStartIndex > positions.length - 1 && alwaysFull != false)
		{
			// opnieuw starten. 
			currentStartIndex = 0;
			return true;
		}
		else if (alwaysFull == false && currentStartIndex % numberOfCardsToCollect == 0)
		{
			// cycle bereikt.
			return true;
		}
		return false;
	}
	/**
	 * Vorige positie instellen
	 * @return boolean Wanneer de beginwaarde is bereikt van simulatie: true
	 */
	public boolean previousPosition()
	{
		currentStartIndex--;
		// opnieuw tekenen
		repaint();
		if (currentStartIndex < 0 && alwaysFull == false)
		{
			// de collectie is weer 'leeg' dus stoppen
			currentStartIndex = 0;
			return true;
		}
		else if (currentStartIndex < 0) 
		{
			// cycle
			currentStartIndex = positions.length - 1;
		}
		return false;
	}
	/**
	 * Initieer/continue de shuffle animatie
	 * @return boolean Wanneer de animatie is afgerond: true
	 */
	public boolean shuffle()
	{
		if (shuffleStart < 0) 
		{
			// nieuwe shuffle actie starten
			shuffleStart = currentStartIndex >= positions.length ? positions.length - 1 : currentStartIndex;
		}
		else if (shuffleStart == currentStartIndex)
		{
			// animatie is afgerond
			shuffleStart = -1;
			return true;
		}
		// volgende positie van de animatee
		nextPosition();
		
		return false;
	}
	/**
	 * Teken dit component
	 * @param g De graphics interface waarnaartoe getekend kan worden.
	 */
	public void paintComponent(Graphics g) 
	{
		super.paintComponent (g);
		int numberOfCardsDraw = alwaysFull == false ? 
				currentStartIndex % positions.length - 1 + (
						currentStartIndex >= positions.length - 1 ? positions.length - 1 : 0
								): 
					positions.length - 1;

		// het plaatje wat getekend kan worden
		BufferedImage img = null;
		try 
		{
			String fileName;
			// achterkant van een kaart
			fileName = "src\\images\\blue_deck_portrait.png";

			img = ImageIO.read(new File(fileName));
		}
		catch (IOException e) 
		{
			System.out.println(e.toString());
		}

		for (int i = 0; i < numberOfCardsDraw; i++) 
		{
//			int pos = positions[(i + currentStartIndex) % (positions.length - 1)]; 
			int left = positions.length - ((i + currentStartIndex) % (positions.length - 1)) * 4; 
			if (img != null) 
			{
				// transformatie object waarmee we kunnen draaien ed.
				AffineTransformOp op;
				AffineTransform tx = new AffineTransform();
				// zorgen dat we in het midden tekenen
				tx.translate(img.getWidth() / 2, img.getHeight() / 2);
				
				switch (cardsPosition)
				{
				case LEFTTOP:
					// links boven dus we draaien de kaart 70 graden en schuiven een beetje
					// zodat de exact boven elkaar staan
					tx.translate(img.getWidth() / 2, img.getHeight() / 4);
					tx.translate(left, -left * 1.4);
					tx.rotate(Math.toRadians(70));
					tx.translate(-left, left * 1.4);
					break;
				case RIGHTTOP:
					// rechts boven dus we draaien 110 graden (na 90 ;-) )
					tx.translate(img.getWidth() / 2, img.getHeight() / 4);
					tx.translate(left, -left * 0.7);
					tx.rotate(Math.toRadians(110));
					tx.translate(-left, left * 0.7);
					break;
				}

				// tx.rotate(Math.toRadians(pos)); // test functie..
				// en weer terug translaten
				tx.translate(img.getWidth() / -2, img.getHeight() / -2);

				// Transform operatie object 
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				
				BufferedImage drawImg = new BufferedImage(img.getWidth() + img.getHeight(), img.getWidth() + img.getHeight(), BufferedImage.TYPE_INT_ARGB);
				
				// de transformatie uitvoeren van het origineel naar het doel
				op.filter(img, drawImg);
				// en doel tekenen
				g.drawImage (drawImg, 0, 0, this);	
			}
		}
	}
}
