package blackjack;
import java.util.*;
/**
 * Klasse die een kaartspel vertegenwoordigt.
 *
 */
public class Deck
{
	/**
	 * ArrayList van spelkaarten in het kaartspel.
	 */
	private ArrayList<Card>		cards = new ArrayList<Card>();									//an arraylist of cards
	/**
	 * Intern gebruikte String-array van kleuren, om kaarten mee te maken.
	 */
	private String[]			suits = {"Hearts", "Diamonds", "Clubs", "Spades"};				//for generating cards
	/**
	 * Intern gebruikte Stringarray van waarden, om kaarten mee te maken.
	 */
	private String[]			ranks = {"2", "3", "4", "5", "6", "7", "8", "9",
											"10", "Jack", "Queen",	"King", "Ace"};
	/**
	 * Intern gebruikte int- array van spelwaarden, om kaarten mee te maken.
	 */
	private int[]				values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};			//for generating cards
	/**
	 * Het totale aantal kaarten in het kaartspel.
	 */
	private	int					maxNumberOfCards;												//total number of cards in deck
	/**
	 * Maakt een nieuw object van het type Deck aan.
	 */
	public Deck()
	{
		generateCards();
	}
	/**
	 * Returnt een ArrayList met daarin alle kaartobjecten.
	 * @return ArrayList een ArrayList met daarin alle kaartobjecten.
	 */
	public ArrayList<Card> getCards()
	{
		return cards;
	}
	/**
	 * Haalt de kaart op index 0 uit het kaartspel en returnt deze.
	 * @return Card De kaart op index 0.
	 */
	public Card takeCard()
	{
		Card returnCard = cards.remove(0);
		return returnCard;
	}
	/**
	 * Stopt de meegegeven kaart terug in het kaartspel.
	 * @param card Een object van het type Card; de terug te plaatsen kaart.
	 */
	public void putCard(Card card)
	{
		cards.add(card);
	}
	/**
	 * Controleert aan de hand van het meegegeven percentage
	 * of het kaartspel moet worden geschud en geeft dit als een boolean terug.
	 * @param percentage Een percentage, zonder cijfers achter de komma.
	 * @return boolean true als er moet worden geschud, false wanneer dit niet het geval is.
	 */
	public boolean shouldBeShuffled(int percentage){

		double percentageOfCardsLeft = ((double)cards.size()/(double)maxNumberOfCards) *100;

		System.out.println((int)percentageOfCardsLeft + " percent of the cards left in shoe");
		if(percentageOfCardsLeft < (100-percentage))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	/**
	 * Schudt het kaartspel.
	 */
	public void shuffle()
	{
        Collections.shuffle(cards);
	}
	/**
	 * Enkel voor intern gebruik; genereert 52 kaarten op basis van 3 arrays
	 * met daarin de waarden voor iedere kaart.
	 */
	protected final void generateCards()
	{
		for (String suit : suits)
		{
			int i = 0;

			for (String rank : ranks)
			{
				cards.add(new Card(rank, suit, values[i]));
				i++;
			}
		}
		maxNumberOfCards = cards.size();
	}
	/**
	 * Returnt een String die dit spel kaarten vertegenwoordigt.
	 * @return String
	 */
	public String toString()
	{
		String returnString = "";

		for(int i = 0 ; i < 52 ; i++)
		{
			returnString += cards.get(i).toString() + "\r";
		}

		return returnString;
	}
}


