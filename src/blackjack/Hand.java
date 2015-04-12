package blackjack;
import java.util.*;
/**
 * De klasse die een hand in het spel vertegenwoordigt.
 *
 */
public class Hand
{
	/**
	 * Het op deze hand ingezette bedrag
	 */
	private	int		bet;
	/**
	 * Geeft weer of deze hand geboden heeft.
	 */
	private boolean hasPlacedBet;
	/**
	 * De spelwaarde van deze hand.
	 */
	private int		score;
	/**
	 * Geeft weer of deze hand 'standed' is.
	 */
	private boolean isStanded;
	/**
	 * Geeft weer of deze hand mag worden 'gesplit'.
	 */
	private boolean isSplittable;
	/**
	 * Geeft weer of de speler op deze hand mag 'doubledownen'.
	 */
	private boolean isDoubleDownable;
	/**
	 * Geeft weer of deze hand een blackjack is.
	 */
	private boolean isBlackjack;
	/**
	 * Geeft weer of deze hand misschien een blackjack is.
	 */
	private boolean maybeBlackjack;
	/**
	 * Geeft weer of deze hand 'busted' is.
	 */
	private boolean isBust;
	/**
	 * Geeft weer of de speler zich tegen deze hand kan verzekeren.
	 */
	private boolean isInsurable;
	/**
	 * Geeft weer of de speler met deze hand heeft gewonnen.
	 */
	private boolean hasWon;
	/**
	 * Geeft weer of de speler met deze hand heeft verloren.
	 */
	private boolean hasLost;
	/**
	 * Geeft weer of de speler met deze hand gelijk heeft gespeeld.
	 */
	private boolean hasEven;
	/**
	 * Geeft weer of de speler met deze hand doubledown heeft gedaan.
	 */
	private boolean hasDoubleDown;
	/**
	 * Geeft weer of deze hand al is uitbetaald of geind;
	 */
	private boolean isPayedOut;
	/**
	 * ArrayList van kaarten in deze hand.
	 */
	private ArrayList<Card> cards;
	/**
	 * Maakt een nieuwe, lege hand aan.
	 */
	public Hand()
	{
		bet					= 0;
		hasPlacedBet		= false;
		score				= 0;
		isStanded			= false;
		isSplittable		= false;
		isDoubleDownable	= false;
		isBlackjack			= false;
		isBust				= false;
		isInsurable			= false;
		hasWon				= false;
		hasLost				= false;
		hasEven				= false;
		hasDoubleDown		= false;

		cards = new ArrayList<Card>();
	}
	/**
	 * Returnt alle kaarten in deze hand.
	 * @return ArrayList Een Arraylist van kaarten in deze hand.
	 */
	public ArrayList<Card> getCards()
	{
		//not sure if we need this getter
		return cards;
	}
	/**
	 * Returnt de spelwaarde van deze hand.
	 * @return int intwaarde die de spelwaarde van deze hand vertegenwoordigt.
	 */
	public int getScore()
	{
		return score;
	}
	/**
	 * Returnt hoeveel er op deze hand is ingezet.
	 * @return int
	 */
	public int getBet()
	{
		return bet;
	}
	/**
	 * Plaatst het meegegeven bedrag als inzet op deze hand.
	 * @param amount Een bedrag in hele euro's
	 */
	public void setBet(int amount)
	{
		hasPlacedBet = true;
		bet = amount;
	}
	/**
	 * Geeft het originele bod terug
	 * @return int
	 */
	public int getOriginalBet()
	{
		if (getDoubleDown() != false) return bet / 2;

		return bet;
	}
	/**
	 * <p>Zet het originele bod.</p>
	 * <p>NOTE: Deze functie zet deze hand niet als hebbend geboden!</p>
	 * @param amount Een bedrag in hele euro's
	 */
	public void setOriginalBet(int amount)
	{
		bet = amount;
	}
	/**
	 * Geeft terug of deze hand geboden heeft
	 * @return boolean
	 */
	public boolean hasBet()
	{
		return hasPlacedBet;
	}
	/**
	 * 'Stand' deze hand.
	 */
	public void stand()
	{
		System.out.println("Player stands.");
		isStanded = true;
	}
	/**
	 * Split de hand door ŽŽn kaart uit de hand te halen en deze te returnen.
	 * @return een Card-object
	 */
	public Card split()
	{
		Card result = cards.get(1);
		cards.remove(result);
		update();
		return result;

	}
	/**
	 * Returnt of deze hand 'standed' is.
	 * @return boolean
	 */
	public boolean isStanded()
	{
		return isStanded;
	}
	/**
	 * Returnt of deze hand 'gesplit' kan worden.
	 * @return boolean
	 */
	public boolean isSplittable()
	{
		return isSplittable;
	}
	/**
	 * Returnt of op deze hand 'gedoubledownd' kan worden.
	 * @return boolean
	 */
	public boolean isDoubleDownable()
	{
		return isDoubleDownable;
	}
	/**
	 * Returnt of deze een blackjack is.
	 * @return boolean
	 */
	public boolean isBlackjack()
	{
		return isBlackjack;
	}
	/**
	 * Returnt of deze blackjack kan zijn.
	 */
	public boolean maybeBlackjack()
	{
		return maybeBlackjack;
	}
	/**
	 * Returnt of deze hand 'busted' is.
	 * @return boolean
	 */
	public boolean isBust()
	{
		return isBust;
	}
	/**
	 * Returnt of de spelers zich tegen deze hand kunnen verzekeren.
	 * TODO Insurance verwijderen of implementeren
	 * @return boolean
	 */
	public boolean isInsurable()
	{
		return isInsurable;
	}
	/**
	 * Zet de boolean hasWon op true.
	 * Moet worden aangeroepen wanneer de speler met deze hand gewonnen heeft.
	 */
	public void won()
	{
		hasWon = true;
	}
	/**
	 * Geeft terug of de speler met deze hand gewonnen heeft.
	 * @return boolean
	 */
	public boolean getWon()
	{
		return hasWon;
	}
	/**
	 * Zet de boolean hasLost op true.
	 * Moet worden aangeroepen wanneer de speler met deze hand verloren heeft.
	 */
	public void lost()
	{
		hasLost = true;
	}
	/**
	 * Geeft terug of de speler met deze hand verloren heeft.
	 * @return boolean
	 */
	public boolean getLost()
	{
		return hasLost;
	}
	/**
	 * Zet boolean hasEven op true
	 * Moet worden aangeroepen wanneer de speler met deze hand gelijk heeft gespeeld
	 */
	public void even()
	{
		hasEven = true;
	}
	/**
	 * Geeft terug of de speler met deze hand gelijk heeft gespeeld.
	 * @return boolean
	 */
	public boolean getEven()
	{
		return hasEven;
	}
	/**
	 * Zet boolean hasDoubleDown op true
	 */
	public void doubleDown()
	{
		hasDoubleDown = true;
	}
	/**
	 * Geeft terug of de speler met deze hand double down heeft gespeeld;
	 * @return boolean
	 */
	public boolean getDoubleDown()
	{
		return hasDoubleDown;
	}

	/**
	 * Zet voor deze hand dat deze uitbetaald is.
	 */
	public void payedOut()
	{
		isPayedOut = true;
	}
	/**
	 * Geeft terug of deze hand uitbetaald is.
	 * @return boolean
	 */
	public boolean getPayedOut()
	{
		return isPayedOut;
	}
	/**
	 * Voegt een kaart toe aan deze hand en updatet alle variabelen.
	 * @param card De toe te voegen kaart
	 */
	public void addCard(Card card)
	{
		cards.add(card);
		System.out.println("Card added to hand:");
		System.out.println(card.toString());
		update();
		System.out.println("Score: " + score);
	}
	/**
	 * Updatet alle instance variabelen aan de hand
	 * van de kaarten in deze hand, zoals de totale waarde van deze hand,
	 * of deze hand een blackjack is, de spelwaarde, etc.
	 */
	private void update()
	{
		score = calculateScore();

		if(cards.size() == 1)
		{
			maybeBlackjack = score >= 10;
		}
		else if(cards.size() >= 2)
		{
			if(cards.size() == 2 && cards.get(0).getRank().matches(cards.get(1).getRank()))
			{
				System.out.println("This hand is splittable.");
				isSplittable = true;
			}
			else
			{
				isSplittable = false;
			}

			if(cards.size() == 2 && score >=9 && score <= 11)
			{
				System.out.println("This hand is double-downable.");
				isDoubleDownable = true;
			}
			else
			{
				isDoubleDownable = false;
			}

			if(cards.size() == 2 && score == 21)
			{
				System.out.println("This hand is a blackjack.");
				isBlackjack = true;
			}
			else
			{
				isBlackjack = false;
			}

			if(score > 21)
			{
				System.out.println("Hand busted.");
				isBust = true;
			}
			else
			{
				isBust = false;
			}
//TODO Implementeren of lozen
//			if(true)
//			{
//
//				isInsurable = true;
//			}
//			else
//			{
//				isInsurable = false;
//			}
		}
	}
	/**
	 * Zet de score op 0, en berekent de spelwaarde van deze hand opnieuw.
	 * @return int de berekende spelwaarde
	 */
	private int calculateScore()
	{
		score = 0;

		for (Card card : cards)
		{
			score += card.getValue();
		}

		/* Aas is 1 of 11 mechanisme */
		if (score > 21)
		{
			/* trek 10 van de score af voor iedere Aas in deze hand tot de score 21 of lager is */
			for (Card card : cards)
			{
				if (card.getValue() == 11 && score > 21)
				{
					score -=10;
				}
			}
		}
		return score;
	}
}